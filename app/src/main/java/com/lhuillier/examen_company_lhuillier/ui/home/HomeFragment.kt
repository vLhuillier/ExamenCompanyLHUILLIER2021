package com.lhuillier.examen_company_lhuillier.ui.home

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.RadioGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lhuillier.examen_company_lhuillier.CompanyAdapter
import com.lhuillier.examen_company_lhuillier.CompanyDatabase
import com.lhuillier.examen_company_lhuillier.R
import com.lhuillier.examen_company_lhuillier.data.model.Company
import com.lhuillier.examen_company_lhuillier.data.model.SearchHistory
import com.lhuillier.examen_company_lhuillier.data.tier.SearchHistoryDAO
import com.lhuillier.examen_company_lhuillier.data.tier.SearchHistoryWithCompaniesDAO
import com.lhuillier.examen_company_lhuillier.service.CompanyService
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private var filterValue: String = "null"
    private var filterType: String = "postal_code"

    inner class QueryCompaniesTask(
            private val svc: CompanyService,
            private val progress: ProgressBar,
            private val filterType: String? = null,
            private val filterValue: String? = null,
            private val recyclerView: RecyclerView?,

            ): AsyncTask<String, Void, List<Company>?>() {

        override fun onPreExecute() {
            progress.visibility = View.VISIBLE
            if (recyclerView != null) {
                recyclerView.visibility = View.INVISIBLE
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        override fun doInBackground(vararg params: String?): List<Company>? {
            val query = params[0] ?: return emptyList()
            return svc.getCompanies(query, filterType, filterValue)
        }

        override fun onPostExecute(result: List<Company>?) {
            if (recyclerView != null) {
                if (result != null && result.size != 0) {
                        recyclerView.adapter = activity?.let { CompanyAdapter(it, result) }
                        recyclerView.visibility = View.VISIBLE
                }else{
                    val builder = AlertDialog.Builder(context)
                    builder.setMessage(R.string.empty_result_company)
                            .setPositiveButton(R.string.ok,
                                    DialogInterface.OnClickListener { dialog, _ ->
                                        dialog.dismiss()
                                    })
                    builder.create().show()
                }

            }
            progress.visibility = View.GONE
        }

    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        /* Init */
        homeViewModel =
                ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val db = activity?.let { CompanyDatabase.getDatabase(it) }
        val searchHistoryDAO = db?.searchHistoryDao()
        val companyDAO = db?.companyDao()
        val searchHistoryWithCompaniesDAO = db?.searchHistoryWithCompaniesDAO()
        val svc = CompanyService(searchHistoryDAO!!, companyDAO!!, searchHistoryWithCompaniesDAO!!)

        val lstCompanies = root.findViewById<RecyclerView>(R.id.lstCompanies)
        lstCompanies.layoutManager = LinearLayoutManager(activity)

        /* Reset Search History after one day */
        val searchHistory = searchHistoryDAO.getAll()
        val currentDateMinusOneDay: LocalDate = LocalDate.now().minusDays(1)
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE)
        for (i in searchHistory!!){
            if( sdf.parse(i.date).before(sdf.parse(currentDateMinusOneDay.toString())) ){
                    searchHistoryDAO.delete(i)
                    val searchHistoryToDelete = searchHistoryWithCompaniesDAO.getBySearchHistory(i.id!!)
                for (y in searchHistoryToDelete){
                    searchHistoryWithCompaniesDAO.delete(y)
                }

            }
        }

        /* Search Company From HistoryFragment */
        val history = activity?.intent?.getSerializableExtra("history") as? SearchHistory
        if (history != null) {
            val searchbar = root.findViewById<EditText>(R.id.search_input)
            searchbar.setText(history.word)
            getDatas(searchHistoryDAO, searchHistoryWithCompaniesDAO, svc, lstCompanies, history.word)
        }

        /* Show/Hide Advanced Search */
        val sw =  root.findViewById<Switch>(R.id.switchAdvancedSearch)
        sw.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            hideKeyboard()
            val radGroupFilter = root.findViewById<RadioGroup>(R.id.radGroupFilter)
            val filterParams = root.findViewById<LinearLayout>(R.id.filterParams)
            val textViewFilter = root.findViewById<TextView>(R.id.filterKey)

            if (isChecked) {
                radGroupFilter.getLayoutParams().height = 200;
                radGroupFilter.requestLayout();
                radGroupFilter.visibility = RadioGroup.VISIBLE
                filterParams.visibility = RadioGroup.VISIBLE

                radGroupFilter.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
                    when (checkedId) {
                        R.id.radPostalCode -> {
                            textViewFilter.setText(String.format(getString(R.string.defaultFilterKey)))
                            filterType = "postal_code"
                        }
                        R.id.rad_departement -> {
                            textViewFilter.setText(String.format(getString(R.string.filterKeyDepartement)))
                            filterType = "departement"
                        }
                        R.id.rad_naf -> {
                            textViewFilter.setText(String.format(getString(R.string.filterKeyNaf)))
                            filterType = "naf"
                            Toast.makeText(
                                    activity,
                                    "Indisponible",
                                    Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                })
            } else {
                radGroupFilter.getLayoutParams().height = 40;
                radGroupFilter.requestLayout();
                radGroupFilter.visibility = RadioGroup.INVISIBLE
                filterParams.visibility = RadioGroup.INVISIBLE
            }
        })

        /* Search Company Action Button */
        root.findViewById<ImageButton>(R.id.search_button).setOnClickListener {
            hideKeyboard()
            val query = search_input.text.toString()
            /* Check Empty Form Search Company */
            val name = (root.findViewById<View>(R.id.search_input) as EditText).text.toString()
            if (name.trim().isEmpty()) {
                val errorMessage = String.format(getString(R.string.error_message), name)
                Toast.makeText(
                        activity,
                        errorMessage,
                        Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            if (filterParams.visibility != View.INVISIBLE) {
                /* Check Form Search Filter */
                val filterValueText: EditText = root.findViewById<EditText>(R.id.filterValue)
                filterValue = filterValueText.getText().toString()
                hideKeyboard()
                if (filterValue.trim().isEmpty()) {
                    val errorMessage = String.format(getString(R.string.error_messageFilter))
                    Toast.makeText(
                            activity,
                            errorMessage,
                            Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
                if (filterType == "postal_code" && filterValue.count() != 5 /* && filterValue.matches("[0-9]+".toRegex()) */) {
                    val errorMessage = String.format(getString(R.string.error_messageFilter_postal_code))
                    Toast.makeText(
                            activity,
                            errorMessage,
                            Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
                if (filterType == "departement" && filterValue.count() != 2 /* && filterValue.matches("[0-9]+".toRegex()) */) {
                    val errorMessage = String.format(getString(R.string.error_messageFilter_departement))
                    Toast.makeText(
                            activity,
                            errorMessage,
                            Toast.LENGTH_LONG
                    ).show()
                    return@setOnClickListener
                }
            } else {
                filterType = "null"
                filterValue = "null"
            }

            getDatas(searchHistoryDAO, searchHistoryWithCompaniesDAO, svc, lstCompanies, query, filterType, filterValue)
        }

        return root
    }

    fun getDatas(searchHistoryDAO: SearchHistoryDAO,
                 searchHistoryWithCompaniesDAO: SearchHistoryWithCompaniesDAO,
                 svc: CompanyService,
                 lstCompanies: RecyclerView,
                 query: String,
                 filterType: String? = null,
                 filterValue: String? = null){

        /* Check db to avoid duplication By Word*/
        val searchHistory = searchHistoryDAO.getBySearchWord(query)
        if (searchHistory == null || radGroupFilter.getVisibility() == View.VISIBLE) {
            QueryCompaniesTask(svc, prgCompanies, filterType, filterValue, lstCompanies as RecyclerView).execute(query)
        } else {
            val results = searchHistory.id?.let { it1 -> searchHistoryWithCompaniesDAO.getCompaniesBySearchHistory(it1) }
            if(results != null){
                if (lstCompanies != null) {
                    lstCompanies.adapter = activity?.let { CompanyAdapter(it, results) }
                    lstCompanies.visibility = View.VISIBLE
                }
            }
        }
    }

    fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    fun Activity.hideKeyboard() {
        hideKeyboard(currentFocus ?: View(this))
    }

    fun Context.hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
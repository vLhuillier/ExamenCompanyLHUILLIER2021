package com.lhuillier.examen_company_lhuillier.ui.home

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
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

    inner class QueryCompaniesTask(
            private val svc: CompanyService,
            private val progress: ProgressBar,
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
            return svc.getCompanies(query)
        }

        override fun onPostExecute(result: List<Company>?) {
            if (recyclerView != null) {
                recyclerView.adapter = activity?.let { CompanyAdapter(it, result) }
                recyclerView.visibility = View.VISIBLE
            }
            progress.visibility = View.GONE
        }

    }

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
            getDatas(searchHistoryDAO, searchHistoryWithCompaniesDAO, svc, lstCompanies,  history.word)
        }

        /* Search Company Action Button */
        root.findViewById<ImageButton>(R.id.search_button).setOnClickListener {
            hideKeyboard()
            val query = search_input.text.toString()
            getDatas(searchHistoryDAO, searchHistoryWithCompaniesDAO, svc, lstCompanies, query)
        }
        return root
    }

    fun getDatas(searchHistoryDAO:SearchHistoryDAO,
                 searchHistoryWithCompaniesDAO: SearchHistoryWithCompaniesDAO,
                 svc: CompanyService,
                 lstCompanies: RecyclerView,
                 query: String){
        /* Check db to avoid duplication By Word*/
        val searchHistory = searchHistoryDAO.getBySearchWord(query)
        println(searchHistory)
        if (searchHistory == null) {
            println("get from api")
            QueryCompaniesTask(svc, prgCompanies, lstCompanies as RecyclerView).execute(query)
        } else {
            println("get from db")
            val results = searchHistory.id?.let { it1 -> searchHistoryWithCompaniesDAO.getCompaniesBySearchHistory(it1) }
            println(results)
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
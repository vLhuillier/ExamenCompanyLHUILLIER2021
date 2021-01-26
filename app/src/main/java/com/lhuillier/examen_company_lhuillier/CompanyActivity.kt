package com.lhuillier.examen_company_lhuillier

import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.lhuillier.examen_company_lhuillier.service.CompanyService
import com.lhuillier.examen_lhuillier.data.model.Company
import kotlinx.android.synthetic.main.content_company.*
import kotlinx.android.synthetic.main.fragment_home.*

class CompanyActivity : AppCompatActivity() {

    inner class QueryWeatherTask(private val svc: CompanyService,
                                 private val progress: ProgressBar
    ): AsyncTask<String, Void, Company?>() {

        override fun onPreExecute() {
            progress.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg params: String?): Company? {
            val query = params[0].toString()
            return svc.getCompany(query)
        }

        override fun onPostExecute(result: Company?) {

            println(result)

            /* Display Siren */
            val sirenMessage = getString(R.string.siren_company)
            val textViewSiren: TextView = findViewById<TextView>(R.id.siren_company) as TextView
            textViewSiren.text = String.format(sirenMessage, result?.siren)

            /* Display Siret */
            val siretMessage = getString(R.string.siret_company)
            val textViewSiret: TextView = findViewById<TextView>(R.id.siret_company) as TextView
            textViewSiret.text = String.format(siretMessage, result?.siret)

            /* Display Nic */
            val nicMessage = getString(R.string.nic_company)
            val textViewNic: TextView = findViewById<TextView>(R.id.nic_company) as TextView
            textViewNic.text = String.format(nicMessage, result?.nic)

            /* Display L1 Normalisized */
            val l1NormalisizedMessage = getString(R.string.l1_normalisized_company)
            val textViewL1Normalisized: TextView = findViewById<TextView>(R.id.l1_normalisized_company) as TextView
            textViewL1Normalisized.text = String.format(l1NormalisizedMessage, result?.l1_normalisee)

            /* Display L2 Normalisized */
            val l2NormalisizedMessage = getString(R.string.l2_normalisized_company)
            val textViewL2Normalisized: TextView = findViewById<TextView>(R.id.l2_normalisized_company) as TextView
            textViewL2Normalisized.text = String.format(l2NormalisizedMessage, result?.l2_normalisee)

            /* Display L3 Normalisized */
            val l3NormalisizedMessage = getString(R.string.l3_normalisized_company)
            val textViewL3Normalisized: TextView = findViewById<TextView>(R.id.l3_normalisized_company) as TextView
            textViewL3Normalisized.text = String.format(l3NormalisizedMessage, result?.l3_normalisee)

            /* Display L4 Normalisized */
            val l4NormalisizedMessage = getString(R.string.l4_normalisized_company)
            val textViewL4Normalisized: TextView = findViewById<TextView>(R.id.l4_normalisized_company) as TextView
            textViewL4Normalisized.text = String.format(l4NormalisizedMessage, result?.l4_normalisee)

            /* Display L5 Normalisized */
            val l5NormalisizedMessage = getString(R.string.l5_normalisized_company)
            val textViewL5Normalisized: TextView = findViewById<TextView>(R.id.l5_normalisized_company) as TextView
            textViewL5Normalisized.text = String.format(l5NormalisizedMessage, result?.l5_normalisee)

            /* Display L6 Normalisized */
            val l6NormalisizedMessage = getString(R.string.l6_normalisized_company)
            val textViewL6Normalisized: TextView = findViewById<TextView>(R.id.l6_normalisized_company) as TextView
            textViewL6Normalisized.text = String.format(l6NormalisizedMessage, result?.l6_normalisee)

            /* Display L7 Normalisized */
            val l7NormalisizedMessage = getString(R.string.l7_normalisized_company)
            val textViewL7Normalisized: TextView = findViewById<TextView>(R.id.l7_normalisized_company) as TextView
            textViewL7Normalisized.text = String.format(l7NormalisizedMessage, result?.l7_normalisee)

            /* Display label region */
            val labelRegionMessage = getString(R.string.label_region_company)
            val textViewLabelRegion: TextView = findViewById<TextView>(R.id.label_region_company) as TextView
            textViewLabelRegion.text = String.format(labelRegionMessage, result?.libelle_region)

            /* Display departement */
            val departementMessage = getString(R.string.departement_company)
            val textViewDepartement: TextView = findViewById<TextView>(R.id.departement_company) as TextView
            textViewDepartement.text = String.format(departementMessage, result?.departement)

            progress.visibility = View.GONE
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company)
        setSupportActionBar(findViewById(R.id.toolbar))

        val company = intent.getSerializableExtra("company") as? Company
        println("company in company ACTIVITY")
        println(company)
        setSupportActionBar(findViewById(R.id.toolbar))
        val textNameLocation = getString(R.string.toolbar_title_company)
        val nameLocation = company?.l1_normalisee
        getSupportActionBar()?.setTitle(String.format(textNameLocation, nameLocation))
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        val svc = CompanyService()

        if (company != null) {
            QueryWeatherTask(svc, prgCompany).execute(company.siret)
        }


        findViewById<FloatingActionButton>(R.id.fabFavorite).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }
}
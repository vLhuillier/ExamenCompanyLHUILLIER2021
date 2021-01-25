package com.lhuillier.examen_company_lhuillier

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
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

            /* Display L1 Normalisée */
            val l1NormaliseeMessage = getString(R.string.l1_normalisee_company)
            val textViewL1Normalisee: TextView = findViewById<TextView>(R.id.l1_normalisee_company) as TextView
            textViewL1Normalisee.text = String.format(l1NormaliseeMessage, result?.l1_normalisee)

            /* Display L2 Normalisée */
            val l2NormaliseeMessage = getString(R.string.l2_normalisee_company)
            val textViewL2Normalisee: TextView = findViewById<TextView>(R.id.l2_normalisee_company) as TextView
            textViewL2Normalisee.text = String.format(l2NormaliseeMessage, result?.l2_normalisee)

            /* Display L3 Normalisée */
            val l3NormaliseeMessage = getString(R.string.l3_normalisee_company)
            val textViewL3Normalisee: TextView = findViewById<TextView>(R.id.l3_normalisee_company) as TextView
            textViewL3Normalisee.text = String.format(l3NormaliseeMessage, result?.l3_normalisee)

            /* Display L4 Normalisée */
            val l4NormaliseeMessage = getString(R.string.l4_normalisee_company)
            val textViewL4Normalisee: TextView = findViewById<TextView>(R.id.l4_normalisee_company) as TextView
            textViewL4Normalisee.text = String.format(l4NormaliseeMessage, result?.l4_normalisee)

            /* Display L5 Normalisée */
            val l5NormaliseeMessage = getString(R.string.l5_normalisee_company)
            val textViewL5Normalisee: TextView = findViewById<TextView>(R.id.l5_normalisee_company) as TextView
            textViewL5Normalisee.text = String.format(l5NormaliseeMessage, result?.l5_normalisee)

            /* Display L6 Normalisée */
            val l6NormaliseeMessage = getString(R.string.l6_normalisee_company)
            val textViewL6Normalisee: TextView = findViewById<TextView>(R.id.l6_normalisee_company) as TextView
            textViewL6Normalisee.text = String.format(l6NormaliseeMessage, result?.l6_normalisee)

            /* Display L7 Normalisée */
            val l7NormaliseeMessage = getString(R.string.l7_normalisee_company)
            val textViewL7Normalisee: TextView = findViewById<TextView>(R.id.l7_normalisee_company) as TextView
            textViewL7Normalisee.text = String.format(l7NormaliseeMessage, result?.l7_normalisee)

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
        getSupportActionBar()?.setTitle(String.format(textNameLocation, nameLocation));

        val svc = CompanyService()

        if (company != null) {
            QueryWeatherTask(svc, prgCompany).execute(company.siret)
        }


        findViewById<FloatingActionButton>(R.id.fabFavorite).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }
}
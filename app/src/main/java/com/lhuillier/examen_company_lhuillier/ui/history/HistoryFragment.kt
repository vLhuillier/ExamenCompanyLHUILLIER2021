package com.lhuillier.examen_company_lhuillier.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lhuillier.examen_company_lhuillier.CompanyAdapter
import com.lhuillier.examen_company_lhuillier.CompanyDatabase
import com.lhuillier.examen_company_lhuillier.HistoryAdapter
import com.lhuillier.examen_company_lhuillier.R
import com.lhuillier.examen_company_lhuillier.service.CompanyService

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel:HistoryViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_history, container, false)

        val db = activity?.let { CompanyDatabase.getDatabase(it) }
        val searchHistoryDAO = db?.searchHistoryDao()

        val lstHistory = root.findViewById<RecyclerView>(R.id.lstHistory)
        lstHistory.layoutManager = LinearLayoutManager(activity)

        var results = searchHistoryDAO?.getAll()

        if(results != null){
            if (lstHistory != null) {
                lstHistory.adapter = activity?.let { HistoryAdapter(it, results) }
                lstHistory.visibility = View.VISIBLE
            }
        }

        return root
    }
}
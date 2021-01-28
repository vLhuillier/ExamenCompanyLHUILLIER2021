package com.lhuillier.examen_company_lhuillier

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lhuillier.examen_company_lhuillier.data.model.SearchHistory
import com.lhuillier.examen_company_lhuillier.ui.history.HistoryFragment
import com.lhuillier.examen_company_lhuillier.ui.home.HomeFragment


class HistoryAdapter(val context: Context,
                     private var historyList: List<SearchHistory>?): RecyclerView.Adapter<HistoryViewHolder?>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.lst_history, parent, false))
    }

    override fun getItemCount(): Int {
        return historyList?.size ?: 0
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {

        holder.txtHistory.text = historyList?.get(position).toString()

        holder.itemView.setOnClickListener {
            val history: SearchHistory = historyList?.get(position) ?: return@setOnClickListener
/*
            val bundle = Bundle()
            bundle.putSerializable("history", history)
            val historyFragment = HistoryFragment()
            historyFragment.arguments = bundle
            */

            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra("history", history)
            context.startActivity(intent)

            true
        }
    }
}
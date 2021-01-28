package com.lhuillier.examen_company_lhuillier

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistoryViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {
    val txtHistory = itemView.findViewById<TextView>(R.id.txtHistory)
}
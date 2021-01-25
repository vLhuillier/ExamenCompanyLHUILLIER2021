package com.lhuillier.examen_company_lhuillier

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CompanyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val txtCompany = itemView.findViewById<TextView>(R.id.txtCompany)
}
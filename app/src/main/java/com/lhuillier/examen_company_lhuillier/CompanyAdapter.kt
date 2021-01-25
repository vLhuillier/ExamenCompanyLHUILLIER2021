package com.lhuillier.examen_company_lhuillier

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lhuillier.examen_company_lhuillier.ui.home.HomeFragment
import com.lhuillier.examen_lhuillier.data.model.Company

class CompanyAdapter(val context: Context,
                     private var companiesList:List<Company>?): RecyclerView.Adapter<CompanyViewHolder?>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        return CompanyViewHolder(LayoutInflater
                .from(context)
                .inflate(R.layout.lst_companies, parent, false))
    }

    override fun getItemCount(): Int {
        return companiesList?.size ?: 0
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {

        holder.txtCompany.text = companiesList?.get(position).toString()

        /* Show Companies by QuerySearch */
        holder.itemView.setOnClickListener {
            val company: Company = companiesList?.get(position) ?: return@setOnClickListener
            println("COMPANIES in COMPANY ADAPTER")
            val intent = Intent(context, CompanyActivity::class.java)
            intent.putExtra("company", company)
            context.startActivity(intent)
            true
        }
    }
}
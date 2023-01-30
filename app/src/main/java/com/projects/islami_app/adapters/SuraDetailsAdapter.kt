package com.projects.islami_app.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projects.islami_app.R

class SuraDetailsAdapter(val suraLines:List<String>) :RecyclerView.Adapter<SuraDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.sura_details,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val suraLine=suraLines[position]
        holder.suraLine.text=suraLine
    }

    override fun getItemCount(): Int {
        return suraLines.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
            val suraLine=itemView.findViewById<TextView>(R.id.sura_line_text)
    }
}
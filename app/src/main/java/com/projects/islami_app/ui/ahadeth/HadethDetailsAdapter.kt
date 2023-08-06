package com.projects.islami_app.ui.ahadeth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projects.islami_app.R

class HadethDetailsAdapter(val hadethLines:List<String>) :RecyclerView.Adapter<HadethDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.hadeth_details,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hadethLine=hadethLines[position]
        holder.hadethLine.text=hadethLine
    }

    override fun getItemCount(): Int {
        return hadethLines.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val hadethLine=itemView.findViewById<TextView>(R.id.hadeth_line_text)
    }
}
package com.projects.islami_app.ahadeth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projects.islami_app.R

class AhadethAdapter(val ahadethList:MutableList<String>) : RecyclerView.Adapter<AhadethAdapter.ViewHolder>() {
    lateinit var onHadethClick: OnItemClick
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.hadeth_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val hadeth=ahadethList[position]
        holder.hadethNumber.text=hadeth
        holder.itemView.setOnClickListener{
            onHadethClick.onClick(position)
        }
    }

    interface OnItemClick
    {
        fun onClick(position:Int)
    }

    override fun getItemCount(): Int {
        return ahadethList.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val hadethNumber:TextView=itemView.findViewById(R.id.hadethNumber)
    }
}
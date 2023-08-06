package com.projects.islami_app.ui.quran

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.projects.islami_app.R

class QuranAdapter(val surasList:MutableList<QuranDataClass>) : RecyclerView.Adapter<QuranAdapter.ViewHolder>() {
    lateinit var onSuraClick: onItemClick
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.sura_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val sura=surasList[position]
        holder.suraName.setText(sura.suraName)
        holder.ayatNumber.setText(sura.ayatNumber)
        holder.itemView.setOnClickListener{
            onSuraClick.onClick(position)
        }
    }

    interface onItemClick
    {
        fun onClick(position:Int)
    }

    override fun getItemCount(): Int {
        return surasList.size
    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val suraName:TextView=itemView.findViewById(R.id.sura_name)
        val ayatNumber:TextView=itemView.findViewById(R.id.no_ayat)
    }
}
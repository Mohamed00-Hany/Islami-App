package com.projects.islami_app.ui.ahadeth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projects.islami_app.R

class AhadethFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var adapter: AhadethAdapter
    lateinit var ahadethList:MutableList<String>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ahadeth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=view.findViewById(R.id.quran_recyclerview)
        layoutManager= LinearLayoutManager(view.context)
        createAhadethList()
        adapter= AhadethAdapter(ahadethList)
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter=adapter
        adapter.onHadethClick=object : AhadethAdapter.OnItemClick {
            override fun onClick(position: Int){
                val intent=Intent(requireActivity(), HadethDetailsActivity::class.java)
                intent.putExtra("hadethPosition",position)
                startActivity(intent)
            }
        }
    }

    fun createAhadethList()
    {
        ahadethList= mutableListOf()

        for(i in 1..50)
        {
            val s:String="الحديث رقم "
            ahadethList.add(s+i)
        }
    }

}
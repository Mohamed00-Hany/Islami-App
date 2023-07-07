package com.projects.islami_app.quran

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.projects.islami_app.*

class QuranFragment : Fragment() {
    lateinit var recyclerView:RecyclerView
    lateinit var layoutManager: LayoutManager
    lateinit var adapter: QuranAdapter
    lateinit var quranList:MutableList<QuranDataClass>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_quran, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView=view.findViewById(R.id.quran_recyclerview)
        layoutManager=LinearLayoutManager(view.context)
        createQuranList()
        adapter= QuranAdapter(quranList)
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter=adapter
        adapter.onSuraClick=object : QuranAdapter.onItemClick {
            override fun onClick(position: Int) {
                val intent=Intent(requireActivity(), SuraDetailsActivity::class.java)
                intent.putExtra("suraPosition",position)
                startActivity(intent)
            }
        }
    }

    fun createQuranList()
            {
                quranList= mutableListOf()

                for(i in 0..suras.size-1)
                {
            quranList.add(QuranDataClass(ayatNumber[i], suras[i]))
        }

    }
}
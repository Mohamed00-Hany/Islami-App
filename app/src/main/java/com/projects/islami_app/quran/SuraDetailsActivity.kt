package com.projects.islami_app.quran

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projects.islami_app.R

class SuraDetailsActivity : AppCompatActivity() {
    var suraPosition: Int = 0
    lateinit var suraName: TextView
    lateinit var suraLines:List<String>
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: SuraDetailsAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var backButton:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sura_details)
        suraPosition=intent.getIntExtra("suraPosition",0)
        suraName=findViewById(R.id.suraTitle)
        suraName.text= "سورة " + suras[suraPosition]
        recyclerView=findViewById(R.id.sura_details_recyclerview)
        createSuraDetails(suraPosition)
        adapter= SuraDetailsAdapter(suraLines)
        layoutManager=LinearLayoutManager(this@SuraDetailsActivity)
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter=adapter
        backButton=findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener{
            finish()
        }

    }

    fun createSuraDetails(position: Int)
    {
        val fileContent=readFileText("${position+1}.txt")
        suraLines=fileContent.split("\n")
    }

    fun readFileText(fileName:String):String
    {
        return assets.open(fileName).bufferedReader().use {
            it.readText() }
    }

}
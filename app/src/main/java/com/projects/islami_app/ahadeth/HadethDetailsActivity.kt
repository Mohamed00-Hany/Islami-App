package com.projects.islami_app.ahadeth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projects.islami_app.R

class HadethDetailsActivity : AppCompatActivity() {
    var hadethPosition: Int = 0
    var title: String=""
    lateinit var hadethTitle: TextView
    lateinit var hadethLines:MutableList<String>
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: HadethDetailsAdapter
    lateinit var layoutManager: LinearLayoutManager
    lateinit var backButton:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hadeth_details)

        hadethPosition=intent.getIntExtra("hadethPosition",0)

        hadethTitle=findViewById(R.id.hadethTitle)

        createHadethDetails(hadethPosition)

        hadethTitle.text=title

        recyclerView=findViewById(R.id.hadeth_details_recyclerview)

        adapter= HadethDetailsAdapter(hadethLines)
        layoutManager=LinearLayoutManager(this@HadethDetailsActivity)
        recyclerView.layoutManager=layoutManager
        recyclerView.adapter=adapter

        backButton=findViewById<ImageView>(R.id.backButton2)
        backButton.setOnClickListener{
            finish()
        }

    }

    fun createHadethDetails(position: Int)
    {
        val fileContent=readFileText("h${position+1}.txt")
        hadethLines = fileContent.split("\n").toMutableList()
        title=hadethLines[0]
        hadethLines.removeAt(0)
    }

    fun readFileText(fileName:String):String
    {
        return assets.open(fileName).bufferedReader().use {
            it.readText() }
    }

}
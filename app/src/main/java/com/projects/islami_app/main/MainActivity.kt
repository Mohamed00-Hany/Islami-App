package com.projects.islami_app.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.projects.islami_app.*
import com.projects.islami_app.databinding.ActivityMainBinding
import com.projects.islami_app.ahadeth.AhadethFragment
import com.projects.islami_app.quran.QuranFragment
import com.projects.islami_app.radio.RadioFragment
import com.projects.islami_app.radio.RadioInfo
import com.projects.islami_app.sebha.SebhaFragment

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navigationBar.setOnItemSelectedListener{
            if(it.itemId== R.id.quran)
            {
                showFragment(QuranFragment())
            }
            else if(it.itemId== R.id.ahadeth)
            {
                showFragment(AhadethFragment())
            }
            else if(it.itemId== R.id.sebha)
            {
                showFragment(SebhaFragment())
            }
            else if(it.itemId== R.id.radio)
            {
                showFragment(RadioFragment());
            }
            return@setOnItemSelectedListener true
        }
        binding.navigationBar.selectedItemId= R.id.quran
        val appModeButton:Button=findViewById(R.id.dark_mode_button)
        appModeButton.setOnClickListener{
            changeAppMode()
            binding.navigationBar.selectedItemId= R.id.quran
        }
    }

    fun changeAppMode()
    {
        val defaultNightMode = AppCompatDelegate.getDefaultNightMode()
        val modeNightNo = AppCompatDelegate.MODE_NIGHT_NO
        if(defaultNightMode == modeNightNo)
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else
        {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
    fun showFragment(fragment: Fragment)
    {
        supportFragmentManager.beginTransaction().replace(R.id.fragments_container, fragment).commit()
    }
}
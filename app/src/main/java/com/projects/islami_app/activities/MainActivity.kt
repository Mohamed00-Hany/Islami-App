package com.projects.islami_app.activities

import android.content.ClipData.Item
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import com.projects.islami_app.*
import com.projects.islami_app.databinding.ActivityMainBinding
import com.projects.islami_app.fragments.AhadethFragment
import com.projects.islami_app.fragments.QuranFragment
import com.projects.islami_app.fragments.RadioFragment
import com.projects.islami_app.fragments.SebhaFragment

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
                showFragment(RadioFragment())
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
package com.projects.islami_app.ui.main

import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.projects.islami_app.R
import com.projects.islami_app.databinding.ActivityMainBinding
import com.projects.islami_app.ui.ahadeth.AhadethFragment
import com.projects.islami_app.ui.quran.QuranFragment
import com.projects.islami_app.ui.radio.RadioFragment
import com.projects.islami_app.ui.sebha.SebhaFragment


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navigationBar.setOnItemSelectedListener {
            if (it.itemId == R.id.quran) {
                showFragment(QuranFragment())
            } else if (it.itemId == R.id.ahadeth) {
                showFragment(AhadethFragment())
            } else if (it.itemId == R.id.sebha) {
                showFragment(SebhaFragment())
            } else if (it.itemId == R.id.radio) {
                showFragment(RadioFragment());
            }
            return@setOnItemSelectedListener true
        }
        if (savedInstanceState==null)
        {
            binding.navigationBar.selectedItemId = R.id.quran
        }
        val appModeButton: Button = findViewById(R.id.dark_mode_button)
        appModeButton.setOnClickListener {
            changeAppMode()
        }
    }

    fun changeAppMode() {
        val currentNightMode = resources.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)
        when (currentNightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragments_container, fragment)
            .commit()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val NightMode = AppCompatDelegate.getDefaultNightMode()
        val sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt("NightModeInt", NightMode)
        editor.apply()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "now you can control media in background by notifications", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "please allow notifications permission to control media in background", Toast.LENGTH_LONG).show()
            }
        }
    }

}
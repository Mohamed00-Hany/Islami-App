package com.projects.islami_app.ui

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import com.projects.islami_app.R

class MyApplication:Application() {

    companion object
    {
        const val NOTIFICATION_CHANNEL_ID="Islami_channel"
    }

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        val sharedPreferences = getSharedPreferences("SharedPrefs", MODE_PRIVATE);
        val nightMode = sharedPreferences.getInt("NightModeInt", 1);
        AppCompatDelegate.setDefaultNightMode(nightMode);
    }

    private fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            val name = getString(R.string.app_name)
            val importance = NotificationManager.IMPORTANCE_LOW
            val desc= "اذاعة القران الكريم"
            val channel = NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance).apply {
                description = desc
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
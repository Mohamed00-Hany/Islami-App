package com.projects.islami_app.ui.radio

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import android.widget.RemoteViews
import androidx.core.app.JobIntentService
import androidx.core.app.NotificationCompat
import com.projects.islami_app.ui.MyApplication.Companion.NOTIFICATION_CHANNEL_ID
import com.projects.islami_app.R
import com.projects.islami_app.ui.radio.RadioInfo.ACTION_CLOSE
import com.projects.islami_app.ui.radio.RadioInfo.ACTION_PLAY
import com.projects.islami_app.ui.radio.RadioInfo.ACTION_PLAY_FIRST_TIME
import com.projects.islami_app.ui.radio.RadioInfo.ACTION_STOP
import com.projects.islami_app.ui.radio.RadioInfo.play
import com.projects.islami_app.ui.radio.RadioInfo.playedBefore

class PlayerService():JobIntentService() {
    var mediaPlayer:MediaPlayer?= MediaPlayer()
    val binder=MyBinnder()
    private val RADIO_PLAYER_NOTIFICATION_ID=200
    var name:String?=null
    var notification=false

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val action=intent?.action
        val urlToPlay=intent?.getStringExtra("url")
        val name=intent?.getStringExtra("name")

        when(action){
            ACTION_PLAY_FIRST_TIME -> {
                startForeground(RADIO_PLAYER_NOTIFICATION_ID,createNotificationForMediaPlayer(name))
                notification=true
                startMediaPlayer(urlToPlay,name)
            }
            ACTION_PLAY -> {
                if(!mediaPlayer?.isPlaying!!)
                {
                    playMediaPlayer()
                    onButtonNotificationClickListener?.onButtonClick(ACTION_PLAY)
                    play=1
                }
            }
            ACTION_STOP -> {
                if(mediaPlayer?.isPlaying!!)
                {
                    pauseMediaPlayer()
                    onButtonNotificationClickListener?.onButtonClick(ACTION_STOP)
                    play=0
                }
            }
            ACTION_CLOSE -> {
                stopMediaPlayer()
                onButtonNotificationClickListener?.onButtonClick(ACTION_CLOSE)
                stopService()
                notification=false
                playedBefore=false
                play=-1 }
            }

        return START_NOT_STICKY
    }

    override fun onHandleWork(intent: Intent) {
        TODO("Not yet implemented")
    }

    var onButtonNotificationClickListener: ButtonNotificationClick?=null

    interface ButtonNotificationClick
    {
        fun onButtonClick(action:String)
    }

    fun startMediaPlayer(urlToPlay:String?,channelName: String?) {
        mediaPlayer= MediaPlayer()
        name=channelName
        mediaPlayer?.apply {
            setDataSource(this@PlayerService, Uri.parse(urlToPlay))
            prepareAsync()
            setOnPreparedListener {
                it.start()
            }
        }
        if(notification)
        {
            updateNotification(name,R.drawable.stop_button)
        }
        else
        {
            startForeground(RADIO_PLAYER_NOTIFICATION_ID,createNotificationForMediaPlayer(name))
            notification=true
        }
    }

    fun playMediaPlayer() {
        mediaPlayer?.start()
        if(notification)
        {
            updateNotification(name,R.drawable.stop_button)
        }
        else
        {
            startForeground(RADIO_PLAYER_NOTIFICATION_ID,createNotificationForMediaPlayer(name))
            notification=true
        }
    }

    fun pauseMediaPlayer() {
        mediaPlayer?.pause()
        if(notification)
        {
            updateNotification(name,R.drawable.play_button)
        }
        else
        {
            startForeground(RADIO_PLAYER_NOTIFICATION_ID,createNotificationForMediaPlayer(name))
            notification=true
        }
    }

    fun stopMediaPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.reset()
    }

    var unbindServiceListener: UnbindService?=null

    interface UnbindService
    {
        fun unBind()
    }

    private fun stopService() {
        stopForeground(true)
        unbindServiceListener?.unBind()
        stopSelf()
    }

    private fun createNotificationForMediaPlayer(name: String?): Notification {
        val defaultView = RemoteViews(packageName, R.layout.notification_item)
        defaultView.setTextViewText(R.id.notification_title,name)
        defaultView.setImageViewResource(R.id.play,R.drawable.stop_button)
        defaultView.setOnClickPendingIntent(R.id.play,getStopPindingButton())
        defaultView.setOnClickPendingIntent(R.id.close,getClosePindingButton())

        val builder=NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_ic)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(defaultView)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setDefaults(0)
            .setSound(null)

        return builder.build()
    }


    private fun updateNotification(name: String?,imageView: Int) {
        val defaultView = RemoteViews(packageName, R.layout.notification_item)
        defaultView.setTextViewText(R.id.notification_title,name)
        defaultView.setImageViewResource(R.id.play,imageView)
        if(imageView==R.drawable.play_button)
        {
            defaultView.setOnClickPendingIntent(R.id.play,getPlayPindingButton())
        }
        else
        {
            defaultView.setOnClickPendingIntent(R.id.play,getStopPindingButton())
        }
        defaultView.setOnClickPendingIntent(R.id.close,getClosePindingButton())

        val builder=NotificationCompat.Builder(this,NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.notification_ic)
            .setStyle(NotificationCompat.DecoratedCustomViewStyle())
            .setCustomContentView(defaultView)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setDefaults(0)
            .setSound(null)

        val notificationManager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(RADIO_PLAYER_NOTIFICATION_ID,builder.build())

    }

    private fun getPlayPindingButton(): PendingIntent {
        val intent=Intent(this, PlayerService::class.java)
        intent.action= ACTION_PLAY
        val pendingIntent=PendingIntent.getService(this,12,intent, PendingIntent.FLAG_IMMUTABLE)
        return pendingIntent
    }
    private fun getStopPindingButton(): PendingIntent {
        val intent=Intent(this, PlayerService::class.java)
        intent.action= ACTION_STOP
        val pendingIntent=PendingIntent.getService(this,0,intent, PendingIntent.FLAG_IMMUTABLE)
        return pendingIntent
    }
    private fun getClosePindingButton(): PendingIntent {
        val intent=Intent(this, PlayerService::class.java)
        intent.action= ACTION_CLOSE
        val pendingIntent=PendingIntent.getService(this,0,intent, PendingIntent.FLAG_IMMUTABLE)
        return pendingIntent
    }


    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    inner class MyBinnder:Binder()
    {
        fun getService(): PlayerService
        {
            return this@PlayerService
        }
    }
}
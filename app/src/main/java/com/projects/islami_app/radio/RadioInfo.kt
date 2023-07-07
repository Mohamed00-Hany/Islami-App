package com.projects.islami_app.radio

import android.content.ServiceConnection
import com.projects.islami_app.apis.models.RadioChannel

object RadioInfo {
    var radioResponse:List<RadioChannel?>?=null
    var channelsNumber=0
    var counter=0
    var play=-1
    var playedBefore=false
    const val ACTION_PLAY="play"
    const val ACTION_PLAY_FIRST_TIME="play_first_time"
    const val ACTION_STOP="stop"
    const val ACTION_CLOSE="close"
    var radioPlayerService:PlayerService?=null
    lateinit var serviceConnection: ServiceConnection
}
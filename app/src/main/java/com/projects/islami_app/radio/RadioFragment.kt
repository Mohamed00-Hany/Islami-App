package com.projects.islami_app.radio

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.projects.islami_app.R
import com.projects.islami_app.apis.ApiManager
import com.projects.islami_app.apis.models.RadioResponse
import com.projects.islami_app.databinding.FragmentRadioBinding
import com.projects.islami_app.radio.RadioInfo.ACTION_CLOSE
import com.projects.islami_app.radio.RadioInfo.ACTION_PLAY
import com.projects.islami_app.radio.RadioInfo.ACTION_PLAY_FIRST_TIME
import com.projects.islami_app.radio.RadioInfo.ACTION_STOP
import com.projects.islami_app.radio.RadioInfo.channelsNumber
import com.projects.islami_app.radio.RadioInfo.counter
import com.projects.islami_app.radio.RadioInfo.play
import com.projects.islami_app.radio.RadioInfo.playedBefore
import com.projects.islami_app.radio.RadioInfo.radioPlayerService
import com.projects.islami_app.radio.RadioInfo.radioResponse
import com.projects.islami_app.radio.RadioInfo.serviceConnection
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RadioFragment : Fragment() {
    lateinit var viewBinding:FragmentRadioBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewBinding=FragmentRadioBinding.inflate(inflater,container,false)
        return viewBinding.root
    }

    private fun callRadioApi() {
        if(radioResponse==null)
        {
            ApiManager.getApis().getRadioChannels().enqueue(object :Callback<RadioResponse>{
                override fun onResponse(call: Call<RadioResponse>, response: Response<RadioResponse>) {
                    if (response.isSuccessful)
                    {
                        radioResponse= response.body()?.radios
                        channelsNumber= radioResponse?.size!!
                        viewBinding.channelName.text=radioResponse?.get(counter)?.name?.trim()
                    }
                }

                override fun onFailure(call: Call<RadioResponse>, t: Throwable) {

                }

            })
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        callRadioApi()
    }

    override fun onStart() {
        super.onStart()

        radio()

        if(radioPlayerService!=null)
        {
            if(radioPlayerService?.mediaPlayer?.isPlaying!!)
            {
                viewBinding.playButton.setBackgroundResource(R.drawable.stop_button)
            }
            else
            {
                viewBinding.playButton.setBackgroundResource(R.drawable.play_button)
            }
        }

        if(radioResponse!=null)
        {
            viewBinding.channelName.text=radioResponse?.get(counter)?.name?.trim()
        }
    }

    override fun onResume() {
        super.onResume()
        if(radioPlayerService!=null&&radioPlayerService?.onButtonNotificationClickListener==null)
        {
            radioPlayerService?.onButtonNotificationClickListener=object :PlayerService.ButtonNotificationClick
            {
                override fun onButtonClick(action: String) {
                    if(action==ACTION_PLAY)
                    {
                        viewBinding.playButton.setBackgroundResource(R.drawable.stop_button)
                    }
                    else if (action==ACTION_STOP)
                    {
                        viewBinding.playButton.setBackgroundResource(R.drawable.play_button)
                    }
                    else if (action==ACTION_CLOSE)
                    {
                        viewBinding.playButton.setBackgroundResource(R.drawable.play_button)
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        radioPlayerService?.onButtonNotificationClickListener=null
    }

    private fun initializeConnection():ServiceConnection {
        return object :ServiceConnection{
            override fun onServiceConnected(name: ComponentName?, mBinder: IBinder?) {
                val binder=mBinder as PlayerService.MyBinnder
                radioPlayerService=binder.getService()
                if(radioPlayerService?.onButtonNotificationClickListener==null)
                {
                    radioPlayerService?.onButtonNotificationClickListener=object :PlayerService.ButtonNotificationClick
                    {
                        override fun onButtonClick(action: String) {
                            if(action==ACTION_PLAY)
                            {
                                viewBinding.playButton.setBackgroundResource(R.drawable.stop_button)
                            }
                            else if (action==ACTION_STOP)
                            {
                                viewBinding.playButton.setBackgroundResource(R.drawable.play_button)
                            }
                            else if (action==ACTION_CLOSE)
                            {
                                viewBinding.playButton.setBackgroundResource(R.drawable.play_button)
                            }
                        }
                    }
                }
                radioPlayerService?.unbindServiceListener=object :PlayerService.UnbindService
                {
                    override fun unBind() {
                        unbindService()
                    }

                }
            }
            override fun onServiceDisconnected(name: ComponentName?) {
            }
        }
    }

    fun unbindService() {
        activity?.unbindService(serviceConnection)
    }

    private fun radio() {
        viewBinding.playButton.setOnClickListener{

            if(channelsNumber!=0) {

                val currentChannelUrl=radioResponse?.get(counter)?.url
                val currentChannelName=radioResponse?.get(counter)?.name

                if(play==-1)
                {
                    if(!playedBefore)
                    {
                        val intent=Intent(requireActivity(),PlayerService::class.java)
                        intent.putExtra("url",currentChannelUrl)
                        intent.putExtra("name",currentChannelName)
                        intent.action = ACTION_PLAY_FIRST_TIME
                        activity?.startService(intent)
                        serviceConnection=initializeConnection()
                        activity?.bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE)
                        playedBefore=true
                    }
                    else
                    {
                        radioPlayerService?.startMediaPlayer(currentChannelUrl,currentChannelName)
                    }
                    play = 1
                    viewBinding.playButton.setBackgroundResource(R.drawable.stop_button)
                }
                else if (play == 1||play==0) {
                    if(!radioPlayerService?.mediaPlayer?.isPlaying!!)
                    {
                        radioPlayerService?.playMediaPlayer()
                        play=1
                        viewBinding.playButton.setBackgroundResource(R.drawable.stop_button)
                    }
                    else
                    {
                        radioPlayerService?.pauseMediaPlayer()
                        play=0
                        viewBinding.playButton.setBackgroundResource(R.drawable.play_button)
                    }
                }
            }
        }

        viewBinding.nextButton.setOnClickListener{

            if(channelsNumber!=0)
            {
                if(counter != channelsNumber-1) {
                    counter++
                }
                else
                {
                    counter =0
                }

                val currentChannelUrl=radioResponse?.get(counter)?.url
                val currentChannelName=radioResponse?.get(counter)?.name

                if(play==1)
                {
                    radioPlayerService?.stopMediaPlayer()
                    radioPlayerService?.startMediaPlayer(currentChannelUrl,currentChannelName)
                }
                else if(play==0)
                {
                    radioPlayerService?.stopMediaPlayer()
                    play=-1
                }
                viewBinding.channelName.text= radioResponse?.get(counter)?.name?.trim()
            }

        }

        viewBinding.previousButton.setOnClickListener {
            if(channelsNumber!=0)
            {
                if(counter != 0) {
                    counter--
                }
                else
                {
                    counter =channelsNumber-1
                }

                val currentChannelUrl=radioResponse?.get(counter)?.url
                val currentChannelName=radioResponse?.get(counter)?.name

                if(play==1)
                {
                    radioPlayerService?.stopMediaPlayer()
                    radioPlayerService?.startMediaPlayer(currentChannelUrl,currentChannelName)
                }
                else if(play==0)
                {
                    radioPlayerService?.stopMediaPlayer()
                    play=-1
                }
                viewBinding.channelName.text= radioResponse?.get(counter)?.name?.trim()
            }
        }
    }
}
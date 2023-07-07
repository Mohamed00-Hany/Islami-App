package com.projects.islami_app.apis

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiManager {
    companion object{
        private var retrofit:Retrofit?=null

        @Synchronized
        private fun getInstance():Retrofit
        {
            if(retrofit==null)
            {
                val loggingInterceptor=HttpLoggingInterceptor {
                    Log.e("Api",it)
                }

                loggingInterceptor.level=HttpLoggingInterceptor.Level.BODY

                val okHttpClient=OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

                retrofit=Retrofit.Builder()
                    .baseUrl("https://mp3quran.net/api/v3/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }

        fun getApis():WebServices
        {
            return getInstance().create(WebServices::class.java)
        }
    }
}
package com.projects.islami_app.apis

import com.projects.islami_app.apis.models.RadioResponse
import retrofit2.Call
import retrofit2.http.GET

interface WebServices {
    @GET("radios")
    fun getRadioChannels():Call<RadioResponse>
}
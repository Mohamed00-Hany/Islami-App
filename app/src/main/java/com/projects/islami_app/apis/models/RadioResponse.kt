package com.projects.islami_app.apis.models

import com.google.gson.annotations.SerializedName

data class RadioResponse(

	@field:SerializedName("radios")
	val radios: List<RadioChannel?>? = null
)
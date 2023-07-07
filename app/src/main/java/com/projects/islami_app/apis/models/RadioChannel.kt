package com.projects.islami_app.apis.models

import com.google.gson.annotations.SerializedName

data class RadioChannel(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("url")
	val url: String? = null
)
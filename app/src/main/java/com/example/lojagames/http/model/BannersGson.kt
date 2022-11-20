package com.example.lojagames.http.model

import com.google.gson.annotations.SerializedName

data class BannersGson(
    val banners: List<Banner> = emptyList()
)

data class Banner(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("url")
    val url: String = ""
)

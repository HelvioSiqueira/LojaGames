package com.example.lojagames.http

import com.example.lojagames.http.model.Banner
import com.example.lojagames.http.model.BannersGson
import com.example.lojagames.http.model.Game
import retrofit2.Response
import retrofit2.http.*

interface GamesHttpApi {

    @GET("banners")
    suspend fun getBanners(): Response<List<Banner>>

    @GET("spotlight")
    suspend fun getGames(): Response<List<Game>>

    @GET("games/{id}")
    fun getDetails(@Path("id") id: Int): Response<Game>

    @GET("games/search?term=")
    suspend fun search(@Query("term") term: String?): Response<List<Game>>

    @POST("checkout")
    fun checkout()
}
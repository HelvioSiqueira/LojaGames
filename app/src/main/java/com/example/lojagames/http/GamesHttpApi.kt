package com.example.lojagames.http

import com.example.lojagames.BannersGson
import com.example.lojagames.http.model.Game
import com.example.lojagames.http.model.GamesGson
import retrofit2.Response
import retrofit2.http.*

interface GamesHttpApi {

    @GET("banners")
    fun getBanners(): Response<BannersGson>

    @GET("spotlight")
    suspend fun getGames(): Response<List<Game>>

    @GET("games/{id}")
    fun getDetails(@Path("id") id: Int): Response<Game>

    @GET("games/search?term=")
    suspend fun search(@Query("term") term: String?): Response<List<Game>>

    @POST("checkout")
    fun checkout()
}
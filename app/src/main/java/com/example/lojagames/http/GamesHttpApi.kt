package com.example.lojagames.http

import com.example.lojagames.BannersGson
import com.example.lojagames.Game
import com.example.lojagames.GamesGson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GamesHttpApi {

    @GET("banners")
    fun getBanners(): Response<BannersGson>

    @GET("spotlight")
    fun getGames(): Response<GamesGson>

    @GET("games/{id}")
    fun getDetails(@Path("id") id: Int): Response<Game>

    @GET("games/search?term={term}")
    fun search(@Path("term") term: String): Response<List<Game>>

    @POST("checkout")
    fun checkout()
}
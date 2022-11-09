package com.example.lojagames.http.model

import com.example.lojagames.GamesGson
import com.example.lojagames.http.GamesHttpApi
import retrofit2.Response

class HttpsUtils(private val api: GamesHttpApi) {

    suspend fun getGames(): Response<GamesGson> {

        return api.getGames()
    }
}
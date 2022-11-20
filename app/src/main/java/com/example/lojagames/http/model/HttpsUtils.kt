package com.example.lojagames.http.model

import com.example.lojagames.http.model.GamesGson
import com.example.lojagames.http.GamesHttpApi
import retrofit2.Response

class HttpsUtils(private val api: GamesHttpApi) {

    suspend fun getGames(): Response<List<Game>> {

        return api.getGames()
    }

    suspend fun getSearch(term: String?): Response<List<Game>> {

        return api.search(term)
    }

    suspend fun getBanners(): Response<List<Banner>> {

        return api.getBanners()
    }
}
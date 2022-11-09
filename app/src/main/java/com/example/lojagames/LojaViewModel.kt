package com.example.lojagames

import androidx.lifecycle.ViewModel
import com.example.lojagames.http.GamesHttpApi
import com.example.lojagames.http.model.Game
import com.example.lojagames.http.model.HttpsUtils

class LojaViewModel(val repo: HttpsUtils): ViewModel() {

    suspend fun getGames(): List<Game>? {

        var listGames: List<Game>? = listOf()

        val response = repo.getGames()

        if(response.isSuccessful){
            listGames = response.body()
        }

        return listGames
    }
}
package com.example.lojagames

import androidx.lifecycle.ViewModel
import com.example.lojagames.http.GamesHttpApi
import com.example.lojagames.http.model.HttpsUtils

class LojaViewModel(val repo: HttpsUtils): ViewModel() {

    suspend fun getGames() = repo.getGames()
}
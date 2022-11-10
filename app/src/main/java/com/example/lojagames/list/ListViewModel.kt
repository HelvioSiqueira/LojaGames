package com.example.lojagames.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lojagames.http.model.Game
import com.example.lojagames.http.model.HttpsUtils

class ListViewModel(val repo: HttpsUtils): ViewModel() {

    val isFinish = MutableLiveData<Boolean>()

    suspend fun getGames(): List<Game>? {
        var listGames: List<Game>? = listOf()

        val response = repo.getGames()

        if(response.isSuccessful){
            listGames = response.body()
        }

        return listGames
    }
}
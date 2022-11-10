package com.example.lojagames.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lojagames.http.model.Game
import com.example.lojagames.http.model.HttpsUtils

class ListViewModel(val repo: HttpsUtils): ViewModel() {

    val isFinish = MutableLiveData<Boolean>()

    suspend fun getGames(): List<Game>? {

        val response = repo.getGames()

        return if(response.isSuccessful){
            response.body()
        } else {
            listOf()
        }

    }

    suspend fun getSearch(term: String?): List<Game>? {
        val response = repo.getSearch(term)

        return if(response.isSuccessful){
            response.body()
        } else{
            listOf()
        }
    }
}
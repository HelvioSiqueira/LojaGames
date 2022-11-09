package com.example.lojagames.di

import com.example.lojagames.API
import com.example.lojagames.LojaViewModel
import com.example.lojagames.http.GamesHttpApi
import com.example.lojagames.http.model.HttpsUtils
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

val androidModule = module {
    single { this }

    single { HttpsUtils(api = get()) }

    viewModel {
        LojaViewModel(repo = get())
    }

    single {
        val looging = HttpLoggingInterceptor()

        looging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(looging)

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl(API)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()

        retrofit.create(GamesHttpApi::class.java)
    }

}
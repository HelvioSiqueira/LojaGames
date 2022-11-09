package com.example.lojagames

data class GamesGson(
    val games: List<Game> = emptyList()
)

data class Game(
    val id: Int = 0,
    val title: String = "",
    val publisher: String = "",
    val image: String = "",
    val discount: Int = 0,
    val price: Int = 0,
    val description: String = "",
    val rating: Double = 0.0,
    val stars: Int = 0,
    val reviews: Int = 0
)
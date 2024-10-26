package com.dharamveer.starwarsblastertournament.model


import com.dharamveer.starwarsblastertournament.model.Player
import com.google.gson.annotations.SerializedName

data class StarWarsMatches(
    @SerializedName("match")
    val match: Int,
    @SerializedName("player1")
    val player1: Player,
    @SerializedName("player2")
    val player2: Player
)
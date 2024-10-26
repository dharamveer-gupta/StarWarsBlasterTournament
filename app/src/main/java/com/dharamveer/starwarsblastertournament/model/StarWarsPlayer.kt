package com.dharamveer.starwarsblastertournament.model


import com.google.gson.annotations.SerializedName

data class StarWarsPlayer(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)
package com.dharamveer.starwarsblastertournament.util

import android.content.Context
import com.dharamveer.starwarsblastertournament.model.StarWarsMatches
import com.dharamveer.starwarsblastertournament.model.StarWarsPlayer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


/**
 * @Author: Dharamveer Gupta
 * @Date: 21 October, 2024 20:50.
 * @Email: dharamveer.gupt@gmail.com
 * @Package: com.dharamveer.starwarsblastertournament.util
 */


fun Context.getPlayerData(): List<StarWarsPlayer> {
    val data = assets.open("StarWarsPlayer.json").bufferedReader().use { it.readText() }
    val gson = Gson()

    val type = object : TypeToken<List<StarWarsPlayer>>() {}.type

    val playerData = gson.fromJson<List<StarWarsPlayer>>(data, type)

//    Log.e("TAG", "DMG loadJsonfromAssets: -> " + playerData)
    return playerData
}


fun Context.getMatchData(): List<StarWarsMatches> {
    val data = assets.open("StarWarsMatches.json").bufferedReader().use { it.readText() }
    val gson = Gson()

    val type = object : TypeToken<List<StarWarsMatches>>() {}.type

    val playerData = gson.fromJson<List<StarWarsMatches>>(data, type)

//    Log.e("TAG", "DMG loadJsonfromAssets: -> " + playerData)
    return playerData
}
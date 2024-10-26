package com.dharamveer.starwarsblastertournament.model


/**
 * @Author: Dharamveer Gupta
 * @Date: 21 October, 2024 23:09.
 * @Email: dharamveer.gupt@gmail.com
 * @Package: com.dharamveer.starwarsblastertournament.data.model
 */
data class PlayerAppearance(
    val matchNumber: Int,
//    val position: String, // "player1" or "player2"
    val player1Name: String,
    val player2Name: String,
    val player1Score: Int,
    val player2Score: Int,
    val result: String // "WIN", "LOSS", "DRAW"
)

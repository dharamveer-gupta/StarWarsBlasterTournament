package com.dharamveer.starwarsblastertournament.ui.details

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dharamveer.starwarsblastertournament.R
import com.dharamveer.starwarsblastertournament.model.PlayerAppearance
import com.dharamveer.starwarsblastertournament.model.StarWarsMatches
import com.dharamveer.starwarsblastertournament.model.StarWarsPlayer
import com.dharamveer.starwarsblastertournament.databinding.ActivityMatchDetailsBinding
import com.dharamveer.starwarsblastertournament.ui.details.adapter.MatchRecordAdapter
import com.dharamveer.starwarsblastertournament.util.getMatchData
import com.dharamveer.starwarsblastertournament.util.getPlayerData

class MatchDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMatchDetailsBinding
    private lateinit var adapter: MatchRecordAdapter

    private lateinit var matchRecord: List<PlayerAppearance>

    private lateinit var playerName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.statusBarColor = ContextCompat.getColor(this@MatchDetailsActivity, R.color.theme_color)

        val playerList = getPlayerData()
        val matchesData = getMatchData()

        intent.extras?.let { bundle ->
            val playerId = bundle.getInt("player_id", -1)
            playerName = bundle.getString("player_name", "")

            matchRecord = getPlayerAppearances(playerId, matchesData, playerList)
        }

        binding = ActivityMatchDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            txtBack.setOnClickListener {
                finish()
            }

            txtTitle.text = playerName

            adapter = MatchRecordAdapter()

            rvMatchRecord.adapter = adapter

            adapter.loadData(matchRecord)
        }

    }

    private fun getPlayerAppearances(playerId: Int, matchesData: List<StarWarsMatches>, playerList: List<StarWarsPlayer>): List<PlayerAppearance> {
        val playersMap = playerList.associateBy { it.id }

        return matchesData.filter { match ->
            match.player1.id == playerId || match.player2.id == playerId
        }.map { match ->
            PlayerAppearance(
                matchNumber = match.match,
//                position = if (match.player1.id == playerId) "player1" else "player2",
                player1Name = playersMap[match.player1.id]?.name ?: "",
                player2Name = playersMap[match.player2.id]?.name ?: "",
                player1Score = match.player1.score,
                player2Score = match.player2.score,
                result = when {
                    match.player1.id == playerId -> {
                        when {
                            match.player1.score > match.player2.score -> "WIN"
                            match.player1.score < match.player2.score -> "LOSS"
                            else -> "DRAW"
                        }
                    }
                    else -> {
                        when {
                            match.player2.score > match.player1.score -> "WIN"
                            match.player2.score < match.player1.score -> "LOSS"
                            else -> "DRAW"
                        }
                    }
                }
            )
        }.sortedBy { it.matchNumber }
    }
}
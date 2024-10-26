package com.dharamveer.starwarsblastertournament.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dharamveer.starwarsblastertournament.R
import com.dharamveer.starwarsblastertournament.model.PlayerScore
import com.dharamveer.starwarsblastertournament.databinding.ActivityMainBinding
import com.dharamveer.starwarsblastertournament.ui.details.MatchDetailsActivity
import com.dharamveer.starwarsblastertournament.ui.home.adapter.StarWarsPlayersAdapter
import com.dharamveer.starwarsblastertournament.util.getMatchData
import com.dharamveer.starwarsblastertournament.util.getPlayerData
import kotlin.collections.groupBy
import kotlin.collections.map
import kotlin.collections.mapValues

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var starWarsPlayersAdapter: StarWarsPlayersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        window.statusBarColor = ContextCompat.getColor(this@MainActivity, R.color.theme_color)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {


            ViewCompat.setOnApplyWindowInsetsListener(main) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            starWarsPlayersAdapter = StarWarsPlayersAdapter { playerId, playerName ->
                val intent = Intent(this@MainActivity, MatchDetailsActivity::class.java).apply {
                    putExtra("player_id", playerId)
                    putExtra("player_name", playerName)
                }
                startActivity(intent)
            }

            rvPlayerList.adapter = starWarsPlayersAdapter

            val layoutManager = LinearLayoutManager(applicationContext)

            val decoration = DividerItemDecoration(rvPlayerList.context, layoutManager.orientation)
            rvPlayerList.addItemDecoration(decoration)

            val playerList = getPlayerData()
            val matchesData = getMatchData()

            // First, let's transform the matches data to determine winners, w.r.t. win = 3, loss = 0, draw = 1
            val winnersList = matchesData.flatMap { match ->
                when {
                    match.player1.score > match.player2.score -> listOf(match.player1.id to 3, match.player2.id to 0)
                    match.player1.score < match.player2.score -> listOf(match.player1.id to 0, match.player2.id to 3)
                    else -> listOf(match.player1.id to 1, match.player2.id to 1)
                }
            }

            // Group by player ID and sum their wins
            val winsMap = winnersList
                .groupBy({ it.first }, { it.second })
                .mapValues { it.value.sum() }

            // Merge the data
            val mergedData = playerList.map { character ->
                PlayerScore(
                    id = character.id,
                    name = character.name,
                    icon = character.icon,
                    score = winsMap[character.id] ?: 0
                )
            }.sortedBy { it.score }.reversed()
//            Log.e("TAG", "DMG match combined : " + mergedData)
            starWarsPlayersAdapter.loadData(mergedData)
        }
    }
}
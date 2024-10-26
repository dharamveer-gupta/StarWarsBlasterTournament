package com.dharamveer.starwarsblastertournament.ui.details.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dharamveer.starwarsblastertournament.model.PlayerAppearance
import com.dharamveer.starwarsblastertournament.databinding.PlayersMatchesItemBinding


/**
 * @Author: Dharamveer Gupta
 * @Date: 21 October, 2024 23:13.
 * @Email: dharamveer.gupt@gmail.com
 * @Package: com.dharamveer.starwarsblastertournament.ui.adapter
 */

class MatchRecordAdapter: RecyclerView.Adapter<MatchRecordAdapter.MatchRecordVH>() {

    private val diffUtil = object : DiffUtil.ItemCallback<PlayerAppearance>() {
        override fun areItemsTheSame(oldItem: PlayerAppearance, newItem: PlayerAppearance): Boolean {
            return oldItem.matchNumber == newItem.matchNumber
        }

        override fun areContentsTheSame(oldItem: PlayerAppearance, newItem: PlayerAppearance): Boolean {
            return oldItem == newItem
        }

    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    class MatchRecordVH(private val binding: PlayersMatchesItemBinding): RecyclerView.ViewHolder(binding.root)  {
        fun bind(data: PlayerAppearance) {
            binding.apply {
                txtPlayer1Name.text = data.player1Name
                txtPlayer2Name.text = data.player2Name

                ("${data.player1Score} - ${data.player2Score}").also { txtPlayersScore.text = it }

                when(data.result) {
                    "WIN" -> itemView.setBackgroundColor(Color.parseColor("#36AE7C"))
                    "LOSS"-> itemView.setBackgroundColor(Color.parseColor("#D83F31"))
                    "DRAW" -> itemView.setBackgroundColor(Color.parseColor("#FFFFFF"))
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchRecordVH {
        val binding = PlayersMatchesItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MatchRecordVH(binding)
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: MatchRecordVH, position: Int) {
        val data = asyncListDiffer.currentList[position]
        holder.bind(data)
    }

    fun loadData(newData: List<PlayerAppearance>) {
        asyncListDiffer.submitList(newData)
    }

}
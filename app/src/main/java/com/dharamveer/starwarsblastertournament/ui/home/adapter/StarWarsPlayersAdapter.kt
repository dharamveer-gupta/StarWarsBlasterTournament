package com.dharamveer.starwarsblastertournament.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dharamveer.starwarsblastertournament.model.PlayerScore
import com.dharamveer.starwarsblastertournament.databinding.PlayersItemCardBinding


/**
 * @Author: Dharamveer Gupta
 * @Date: 21 October, 2024 21:09.
 * @Email: dharamveer.gupt@gmail.com
 * @Package: com.dharamveer.starwarsblastertournament.ui.adapter
 */
class StarWarsPlayersAdapter(
    private val onPlayerClick: (id: Int, name: String) -> Unit
): RecyclerView.Adapter<StarWarsPlayersAdapter.StarWarsPlayerVH>() {

    private val diffUtil = object : DiffUtil.ItemCallback<PlayerScore>() {
        override fun areItemsTheSame(oldItem: PlayerScore, newItem: PlayerScore): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PlayerScore, newItem: PlayerScore): Boolean {
            return oldItem == newItem
        }

    }

    private val asyncListDiffer = AsyncListDiffer(this, diffUtil)

    class StarWarsPlayerVH(private val binding: PlayersItemCardBinding): RecyclerView.ViewHolder(binding.root)  {
        fun bind(data: PlayerScore, onPlayerClick: (id: Int, name: String) -> Unit) {
            binding.apply {
                imgPlayerAvatar.load(data.icon) {
                    crossfade(true)
                }

                txtPlayerName.text = data.name

                data.score.toString().also { txtPlayerScore.text = it }

                itemView.setOnClickListener {
                    onPlayerClick(data.id, data.name)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarWarsPlayerVH {
        val binding = PlayersItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StarWarsPlayerVH(binding)
    }

    override fun getItemCount(): Int = asyncListDiffer.currentList.size

    override fun onBindViewHolder(holder: StarWarsPlayerVH, position: Int) {
        val data = asyncListDiffer.currentList[position]
        holder.bind(data, onPlayerClick)
    }

    fun loadData(newData: List<PlayerScore>) {
        asyncListDiffer.submitList(newData)
    }

 }
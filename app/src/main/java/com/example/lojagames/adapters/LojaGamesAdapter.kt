package com.example.lojagames.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lojagames.databinding.ItemGameBinding
import com.example.lojagames.http.model.Game

class LojaGamesAdapter(private val games: List<Game>, private val onClick: (Game) -> Unit): RecyclerView.Adapter<LojaGamesAdapter.ViewHolder>() {

    private lateinit var binding: ItemGameBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentGame = games[position]

        if(currentGame.title == "Chrono Trigger"){
            currentGame.image = "https://cdn.akamai.steamstatic.com/steam/apps/613830/header.jpg?t=1646911047"
        }

        binding.txtPriceBefore.paintFlags = binding.txtPriceBefore.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        holder.binding.run {
            game = currentGame

            root.setOnClickListener {
                onClick(currentGame)
            }

            executePendingBindings()
        }
    }

    override fun getItemCount() = games.size

    inner class ViewHolder(_binding: ItemGameBinding): RecyclerView.ViewHolder(binding.root){
        val binding = _binding
    }
}
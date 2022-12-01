package com.example.lojagames.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lojagames.databinding.ItemListCarBinding
import com.example.lojagames.http.model.Game

class CarGamesAdapter(private val games: List<Game>): RecyclerView.Adapter<CarGamesAdapter.ViewHolder>() {

    private lateinit var binding: ItemListCarBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemListCarBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentGame = games[position]

        var quantGames = 0

        if(currentGame.title == "Chrono Trigger"){
            currentGame.image = "https://cdn.akamai.steamstatic.com/steam/apps/613830/header.jpg?t=1646911047"
        }

        binding.txtPriceBefore.paintFlags = binding.txtPriceBefore.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

        holder.binding.run {
            game = currentGame

            btnRemo.setOnClickListener {
                if(quantGames > 0){
                    quantGames--
                }
                txtCount.text = quantGames.toString()
            }

            btnAdd.setOnClickListener {
                quantGames++
                txtCount.text = quantGames.toString()
            }
        }
    }

    override fun getItemCount() = games.size

    inner class ViewHolder(_binding: ItemListCarBinding): RecyclerView.ViewHolder(binding.root){
        val binding = _binding
    }
}
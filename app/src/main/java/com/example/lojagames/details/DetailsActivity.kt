package com.example.lojagames.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lojagames.R
import com.example.lojagames.http.model.Game
import org.parceler.Parcels

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        val arguments = Parcels.unwrap<Game>(intent.getParcelableExtra(EXTRA_GAME))

        showDetailsFragment(arguments)
    }

    private fun showDetailsFragment(currentGame: Game){
        val fragment = DetailsFragment.newInstance(currentGame)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentDetails, fragment, DetailsFragment.TAG_DETAILS)
            .commit()
    }

    companion object{

        const val EXTRA_GAME = "game"

        fun open(context: Context, currentGame: Game){
            context.startActivity(Intent(context, DetailsActivity::class.java)
                .putExtra(EXTRA_GAME, Parcels.wrap(currentGame)))
        }
    }
}
package com.example.lojagames.details

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.core.graphics.drawable.toDrawable
import com.example.lojagames.R
import com.example.lojagames.http.model.Game
import org.parceler.Parcels

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = ""

        window.navigationBarColor = Color.BLACK

        setContentView(R.layout.activity_details)

        //Obtem o Game pelo Parcels
        val arguments = Parcels.unwrap<Game>(intent.getParcelableExtra(EXTRA_GAME))

        showDetailsFragment(arguments)
    }

    private fun showDetailsFragment(currentGame: Game){

        //Manda o Game para o fragment
        val fragment = DetailsFragment.newInstance(currentGame)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentDetails, fragment, DetailsFragment.TAG_DETAILS)
            .commit()
    }

    companion object{

        const val EXTRA_GAME = "game"

        //Manda o Game por meio de um Parcels
        fun open(context: Context, currentGame: Game){
            context.startActivity(Intent(context, DetailsActivity::class.java)
                .putExtra(EXTRA_GAME, Parcels.wrap(currentGame)))
        }
    }
}
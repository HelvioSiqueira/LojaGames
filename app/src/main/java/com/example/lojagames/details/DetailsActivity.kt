package com.example.lojagames.details

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lojagames.R

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        val arguments = intent.getIntExtra(EXTRA_ID, 0)

        showDetailsFragment(arguments)
    }

    private fun showDetailsFragment(id: Int){
        val fragment = DetailsFragment.newInstance(id)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragmentDetails, fragment, DetailsFragment.TAG_DETAIS)
            .commit()
    }

    companion object{

        const val EXTRA_ID = "game_id"

        fun open(context: Context, id: Int){
            context.startActivity(Intent(context, DetailsActivity::class.java)
                .putExtra(EXTRA_ID, id))
        }
    }
}
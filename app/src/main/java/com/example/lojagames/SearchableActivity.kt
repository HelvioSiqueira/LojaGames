package com.example.lojagames

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SearchableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onNewIntent(intent: Intent?) {
        setIntent(intent)

    }

    private fun handleIntent(intent: Intent?){
        if(Intent.ACTION_SEARCH == intent?.action){
            intent.getStringExtra(SearchManager.QUERY)?.also {

            }
        }
    }
}
package com.example.lojagames.list

import android.app.blob.BlobStoreManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lojagames.R
import com.example.lojagames.databinding.ActivityMainBinding
import java.sql.Blob

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val fragment = ListGamesFragment.newInstance()

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.mainLayout, fragment, ListGamesFragment.TAG_LIST)
            .commit()
    }
}
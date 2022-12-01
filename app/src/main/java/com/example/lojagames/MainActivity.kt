package com.example.lojagames

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lojagames.car.CarGamesFragment
import com.example.lojagames.databinding.ActivityMainBinding
import com.example.lojagames.list.ListGamesFragment

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

        binding.fabMarket.setOnClickListener {

            val fragmentCar = CarGamesFragment.newInstance()

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.mainLayout, fragmentCar, CarGamesFragment.TAG_CAR)
                .commit()
        }
    }
}
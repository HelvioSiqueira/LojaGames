package com.example.lojagames

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.lojagames.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private val viewModel: LojaViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        launch {
            fill()
        }
    }

    private suspend fun fill(){
        binding.txtTest.text = viewModel.getGames()?.joinToString(" ")
    }
}
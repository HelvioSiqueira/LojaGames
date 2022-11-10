package com.example.lojagames.http

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.parceler.Parcels
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lojagames.adapters.LojaGamesAdapter
import com.example.lojagames.list.ListViewModel
import com.example.lojagames.databinding.ListGamesLayoutBinding
import com.example.lojagames.details.DetailsActivity
import com.example.lojagames.http.model.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class ListGamesFragment : Fragment(), CoroutineScope {
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var binding: ListGamesLayoutBinding

    private var gameList = listOf<Game>()

    private val viewModel: ListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListGamesLayoutBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        job = Job()

        launch {
            getList()

            viewModel.isFinish.value = true
        }

        viewModel.isFinish.observe(viewLifecycleOwner, Observer {
            if (it) {
                startRecyclerView()
            }
        })
    }

    private suspend fun getList() {
        gameList = viewModel.getGames()!!
    }

    private fun startRecyclerView() {
        binding.rvListGames.run {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = LojaGamesAdapter(gameList, ::onClick)
        }
    }

    private fun onClick(currentGame: Game){
        DetailsActivity.open(requireContext(), currentGame)
    }

    companion object {
        const val TAG_LIST = "tagList"

        fun newInstance() = ListGamesFragment()
    }
}
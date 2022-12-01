package com.example.lojagames.car

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lojagames.adapters.CarGamesAdapter
import com.example.lojagames.databinding.ListCarGamesLayoutBinding
import com.example.lojagames.list.ListGamesFragment

class CarGamesFragment: Fragment() {

    private lateinit var binding: ListCarGamesLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ListCarGamesLayoutBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        startRecyclerView()
    }

    private fun startRecyclerView(){
        binding.rvListCarGames.run {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = CarGamesAdapter(ListGamesFragment.listTest)
        }
    }

    companion object{
        const val TAG_CAR = "tagListCar"

        fun newInstance() = CarGamesFragment()
    }
}
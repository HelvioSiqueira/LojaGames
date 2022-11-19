package com.example.lojagames.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lojagames.databinding.FragmentDetailsBinding
import com.example.lojagames.http.model.Game
import org.parceler.Parcels

class DetailsFragment: Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailsBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.game = Parcels.unwrap<Game>(arguments?.getParcelable(EXTRA_GAME))
    }

    companion object{
        const val TAG_DETAILS = "tagDetails"
        const val EXTRA_GAME = "game"

        //Manda o Game pela DetailsActivity pelo Parcels
        fun newInstance(currentGame: Game) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_GAME, Parcels.wrap(currentGame))
            }
        }
    }
}
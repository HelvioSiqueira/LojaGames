package com.example.lojagames.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.lojagames.databinding.FragmentDetailsBinding

class DetailsFragment: Fragment() {

    private lateinit var bindind: FragmentDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindind = FragmentDetailsBinding.inflate(layoutInflater)

        return bindind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindind.txtTitle.text = arguments?.getInt(EXTRA_ID, 0).toString()
    }

    companion object{
        const val TAG_DETAIS = "tagDetails"
        const val EXTRA_ID = "game_id"

        fun newInstance(id: Int) = DetailsFragment().apply {
            arguments = Bundle().apply {
                putInt(EXTRA_ID, id)
            }
        }
    }
}
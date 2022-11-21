package com.example.lojagames.list

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewbinding.ViewBinding
import com.example.lojagames.R
import com.example.lojagames.adapters.LojaGamesAdapter
import com.example.lojagames.databinding.LayoutCardviewBinding
import com.example.lojagames.databinding.ListGamesLayoutBinding
import com.example.lojagames.details.DetailsActivity
import com.example.lojagames.http.model.Banner
import com.example.lojagames.http.model.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.listener.CarouselListener
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import org.imaginativeworld.whynotimagecarousel.utils.setImage
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.coroutines.CoroutineContext

class ListGamesFragment : Fragment(), CoroutineScope, MenuProvider, SearchView.OnQueryTextListener,
    MenuItem.OnActionExpandListener {
    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private var searchView: SearchView? = null

    private val viewModel: ListViewModel by viewModel()

    private lateinit var binding: ListGamesLayoutBinding

    private var gameList = listOf<Game>()
    private var lastSearchTerm: String = ""

    private var banners = listOf<Banner>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding = ListGamesLayoutBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        job = Job()

        //Obtem a corouselView
        val carousel: ImageCarousel = binding.carouselView
        carousel.registerLifecycle(lifecycle)

        //Armazena os itens(Imagens) que irão aparecer no carrosel
        val list = mutableListOf<CarouselItem>()

        launch {
            getList()
            viewModel.isFinish.value = true
        }

        viewModel.isFinish.observe(viewLifecycleOwner) {
            if (it) {
                startRecyclerView()
            }
        }

        launch {

            banners = viewModel.getBanners()!!

            //Adiciona as imagens do Banners a lista
            banners.forEach {
                list.add(CarouselItem(it.image))
            }

            //Adiciona a lista de Banners ao carrosel
            carousel.setData(list)
        }

        //Objeto Listener para ações programaticas
        carousel.carouselListener = object : CarouselListener {
            override fun onCreateViewHolder(
                layoutInflater: LayoutInflater,
                parent: ViewGroup
            ): ViewBinding? {

                //Define um layout para o carrosel
                return LayoutCardviewBinding.inflate(layoutInflater, parent, false)
            }

            override fun onBindViewHolder(binding: ViewBinding, item: CarouselItem, position: Int) {
                val currentBinding = binding as LayoutCardviewBinding

                currentBinding.imageView.apply {
                    scaleType = ImageView.ScaleType.CENTER_CROP

                    setImage(
                        item,
                        org.imaginativeworld.whynotimagecarousel.R.drawable.carousel_default_placeholder
                    )
                }
            }

            override fun onClick(position: Int, carouselItem: CarouselItem) {
                super.onClick(position, carouselItem)

                Toast.makeText(
                    requireContext(),
                    banners.map(Banner::url)[position],
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        //Associa um custom CircleIndicator a um carrosel (O Cicle indicator é definido no layout)
        binding.carouselView.setIndicator(binding.customIndicator)
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

    //Passa o game para a DetaisActivity por meio de um callback
    private fun onClick(currentGame: Game) {
        DetailsActivity.open(requireContext(), currentGame)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.loja_games, menu)

        val searchItem = menu.findItem(R.id.action_search)
        searchItem.setOnActionExpandListener(this)
        searchView = searchItem?.actionView as SearchView
        searchView?.queryHint = "Pesquisa"
        searchView?.setOnQueryTextListener(this)

        if (lastSearchTerm.isNotEmpty()) {
            Handler().post {
                val query = lastSearchTerm
                searchItem.expandActionView()
                searchView?.setQuery(query, true)
                searchView?.clearFocus()
            }
        }

    }

    override fun onMenuItemSelected(menuItem: MenuItem) = true

    override fun onQueryTextSubmit(p0: String?) = true

    override fun onQueryTextChange(term: String?): Boolean {
        lastSearchTerm = term ?: ""

        Log.d("HSV", "Digitando...")

        if (lastSearchTerm.isEmpty()) {
            launch {
                getList()
                Log.d("HSV", gameList.toString())
                startRecyclerView()
            }
        } else {
            launch {
                gameList = viewModel.getSearch(lastSearchTerm)!!
                startRecyclerView()
                Log.d("HSV", gameList.toString())
            }
        }

        return true
    }

    override fun onMenuItemActionExpand(p0: MenuItem?) = true

    override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
        lastSearchTerm = ""

        launch {
            getList()
        }

        startRecyclerView()

        return true
    }

    companion object {
        const val TAG_LIST = "tagList"

        fun newInstance() = ListGamesFragment()
    }
}
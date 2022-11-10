package com.example.lojagames.list

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lojagames.R
import com.example.lojagames.adapters.LojaGamesAdapter
import com.example.lojagames.databinding.ListGamesLayoutBinding
import com.example.lojagames.details.DetailsActivity
import com.example.lojagames.http.model.Game
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
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

        launch {
            getList()
            viewModel.isFinish.value = true
        }

        viewModel.isFinish.observe(viewLifecycleOwner) {
            if (it) {
                startRecyclerView()
            }
        }
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

    companion object {
        const val TAG_LIST = "tagList"

        fun newInstance() = ListGamesFragment()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.loja_games, menu)

        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu.findItem(R.id.action_search).actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))
            queryHint = "Pesquisa"
            searchView?.onActionViewExpanded()
            setOnQueryTextListener(this@ListGamesFragment)
            isIconifiedByDefault = false
            isSubmitButtonEnabled = false
            isIconified = false
        }
    }



    override fun onMenuItemSelected(menuItem: MenuItem) = true

    override fun onQueryTextSubmit(p0: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(p0: String?): Boolean = true

    override fun onMenuItemActionExpand(p0: MenuItem?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMenuItemActionCollapse(p0: MenuItem?): Boolean {
        TODO("Not yet implemented")
    }
}
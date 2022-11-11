package com.example.lojagames.list

import android.app.SearchManager
import android.content.ContentProvider
import android.content.ContentProviderOperation
import android.content.ContentResolver
import android.content.Context
import android.database.MatrixCursor
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.BaseColumns
import android.util.Log
import android.view.*
import android.widget.AutoCompleteTextView
import android.widget.SearchView
import android.widget.SimpleCursorAdapter
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.cursoradapter.widget.CursorAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lojagames.MyContentProvider
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
    private var lastSearchTerm: String = ""

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
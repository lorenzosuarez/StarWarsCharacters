package com.example.starwarscharacters.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarscharacters.R
import com.example.starwarscharacters.application.AppConstants.PAGE_SIZE
import com.example.starwarscharacters.data.DataSource
import com.example.starwarscharacters.data.model.*
import com.example.starwarscharacters.databinding.FragmentMainBinding
import com.example.starwarscharacters.domain.RepositoryImpl
import com.example.starwarscharacters.ui.viewModel.MainViewModel
import com.example.starwarscharacters.ui.viewModel.ViewModelFactory
import com.example.starwarscharacters.utils.hide
import com.example.starwarscharacters.utils.show
import com.example.starwarscharacters.utils.showToast
import com.example.starwarscharacters.vo.Resource
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*


/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by viewModels<MainViewModel> { ViewModelFactory(RepositoryImpl(DataSource())) }
    private lateinit var binding: FragmentMainBinding
    private var page: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        var loading = false

        viewModel.items.observe(viewLifecycleOwner, itemsObserver)
        viewModel.character.observe(viewLifecycleOwner, charactersObserver)
        viewModel.races.observe(viewLifecycleOwner, racesObserver)
        viewModel.starships.observe(viewLifecycleOwner, starshipsObserver)
        viewModel.planets.observe(viewLifecycleOwner, planetsObserver)
        viewModel.loading.observe(viewLifecycleOwner, {
            loading = it
            if(!loading) binding.rvList.adapter!!.notifyDataSetChanged()
        })
        viewModel.itemType.observe(viewLifecycleOwner, {
            this.page = 1
        })
        viewModel.page.observe(viewLifecycleOwner, { page ->
            if (page != this.page) {
                viewModel.nextPage()
                this.page = page
            }
        })
        viewModel.listSize.observe(viewLifecycleOwner, { size ->
            if (size.plus(1) >= (page * PAGE_SIZE) && !loading) {
                viewModel.incrementNumberPage()
            }
        })

        binding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1))
                    viewModel.onChangeListSize(binding.rvList.adapter!!.itemCount)
            }
        })

        binding.rvList.adapter = MainItemsAdapter(
            requireContext(),
            MainItemsAdapter.OnClickListener { title, item ->
                findNavController().navigate(
                    MainFragmentDirections.actionMainFragmentToDetailsFragment(title, item)
                )
            }
        )

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.runFilter()
        }
    }

    private val itemsObserver = Observer<List<Item>> {
        binding.swipeRefresh.isRefreshing = false
    }
    private val charactersObserver = Observer<Resource<CharacterList>> { result ->
        //binding.swipeRefresh.showIf { result is Resource.Loading }
        when (result) {
            is Resource.Loading -> {
                binding.swipeRefresh.isRefreshing = true
            }
            is Resource.Success -> {
                binding.swipeRefresh.isRefreshing = false
                if (result.data.characterList.isEmpty()) {
                    binding.rvList.hide()
                    return@Observer
                }
                binding.rvList.show()
                viewModel.updateItems(result.data)
            }
            is Resource.Failure -> {
                binding.swipeRefresh.isRefreshing = false
                showToast(message = "${getString(R.string.get_data_error)} ${result.exception}")
            }
        }
    }
    private val racesObserver = Observer<Resource<RaceList>> { result ->
        //binding.swipeRefresh.showIf { result is Resource.Loading }
        when (result) {
            is Resource.Loading -> {
                binding.swipeRefresh.isRefreshing = true
            }
            is Resource.Success -> {
                binding.swipeRefresh.isRefreshing = false
                if (result.data.raceList.isEmpty()) {
                    binding.rvList.hide()
                    return@Observer
                }
                binding.rvList.show()
                viewModel.updateItems(result.data)
            }
            is Resource.Failure -> {
                binding.swipeRefresh.isRefreshing = false
                showToast(message = "${getString(R.string.get_data_error)} ${result.exception}")
            }
        }
    }
    private val starshipsObserver = Observer<Resource<StarshipList>> { result ->
        //binding.swipeRefresh.showIf { result is Resource.Loading }
        when (result) {
            is Resource.Loading -> {
                binding.swipeRefresh.isRefreshing = true
            }
            is Resource.Success -> {
                binding.swipeRefresh.isRefreshing = false
                if (result.data.starshipList.isEmpty()) {
                    binding.rvList.hide()
                    return@Observer
                }
                binding.rvList.show()
                viewModel.updateItems(result.data)
            }
            is Resource.Failure -> {
                binding.swipeRefresh.isRefreshing = false
                showToast(message = "${getString(R.string.get_data_error)} ${result.exception}")
            }
        }
    }
    private val planetsObserver = Observer<Resource<PlanetList>> { result ->
        //binding.swipeRefresh.showIf { result is Resource.Loading }
        when (result) {
            is Resource.Loading -> {
                binding.swipeRefresh.isRefreshing = true
            }
            is Resource.Success -> {
                binding.swipeRefresh.isRefreshing = false
                if (result.data.planetList.isEmpty()) {
                    binding.rvList.hide()
                    return@Observer
                }
                binding.rvList.show()
                viewModel.updateItems(result.data)
            }
            is Resource.Failure -> {
                binding.swipeRefresh.isRefreshing = false
                showToast(message = "${getString(R.string.get_data_error)} ${result.exception}")
            }
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.statusInLeague).isVisible = false
        menu.findItem(R.id.myGalacticLeague).isVisible = true
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.myGalacticLeague -> {
                Toast.makeText(requireContext(), "statussInLeague", Toast.LENGTH_LONG).show()
                false
            }

            R.id.statusInLeague ->{
                Toast.makeText(requireContext(), "statusInLeague", Toast.LENGTH_LONG).show()
                false
            }
            else -> false
        }
    }

    override fun onResume() {
        if (viewModel.items.value == null) viewModel.runFilter()
        super.onResume()
    }
}

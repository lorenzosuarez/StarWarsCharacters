package com.example.starwarscharacters.ui

import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.*
import android.view.animation.Animation
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.starwarscharacters.R
import com.example.starwarscharacters.application.AppConstants.PAGE_SIZE
import com.example.starwarscharacters.application.AppConstants.PREF_NAME
import com.example.starwarscharacters.application.AppConstants.PRIVATE_MODE
import com.example.starwarscharacters.application.AppDatabase
import com.example.starwarscharacters.data.DataSource
import com.example.starwarscharacters.data.model.*
import com.example.starwarscharacters.databinding.FragmentMainBinding
import com.example.starwarscharacters.databinding.LeagueBottomSheetBinding
import com.example.starwarscharacters.databinding.OptionsBottomSheetBinding
import com.example.starwarscharacters.domain.RepositoryImpl
import com.example.starwarscharacters.ui.viewModel.MainViewModel
import com.example.starwarscharacters.ui.viewModel.ViewModelFactory
import com.example.starwarscharacters.utils.hide
import com.example.starwarscharacters.utils.onSNACK
import com.example.starwarscharacters.utils.show
import com.example.starwarscharacters.utils.showToast
import com.example.starwarscharacters.vo.Resource
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.item_row.*
import kotlinx.android.synthetic.main.league_bottom_sheet.*
import kotlinx.android.synthetic.main.options_bottom_sheet.*
import java.util.*


/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel by activityViewModels<MainViewModel> {
        ViewModelFactory(
            RepositoryImpl(
                DataSource(
                    AppDatabase.getDatabase(requireActivity().applicationContext)
                )
            )
        )
    }
    private lateinit var binding: FragmentMainBinding
    private lateinit var bindingSheetLeague: LeagueBottomSheetBinding
    private lateinit var bottomSheetLeague: BottomSheetDialog
    private lateinit var item: Item
    private lateinit var title: String
    private var page: Int = 1
    private lateinit var sharedPref: SharedPreferences
    private var leagueIsOpen: Boolean
        get() = sharedPref.getBoolean(PREF_NAME, true)
        set(value) = sharedPref.edit().putBoolean(PREF_NAME, value).apply()
    private var loading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        viewModel.items.observe(this, itemsObserver)
        viewModel.character.observe(this, charactersObserver)
        viewModel.races.observe(this, racesObserver)
        viewModel.starships.observe(this, starshipsObserver)
        viewModel.planets.observe(this, planetsObserver)
        viewModel.loading.observe(this, {
            loading = it
            if (!loading) {
                binding.rvList.adapter!!.notifyDataSetChanged()
                val itemsCount: Int = binding.rvList.adapter?.itemCount ?: 0
                if (itemsCount > PAGE_SIZE)
                    binding.rvList.scrollToPosition(itemsCount)
            }
        })
        viewModel.itemType.observe(this, {
            this.page = 1
        })
        viewModel.page.observe(this, { page ->
            if (page != this.page) {
                viewModel.nextPage()
                this.page = page
            }
        })
        viewModel.listSize.observe(this, { size ->
            if (size.plus(1) >= (page * PAGE_SIZE) && !loading) {
                viewModel.incrementNumberPage()
            }
        })

        sharedPref = requireContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE)
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

        //League data observers
        viewModel.getCharactersFromLeague().observe(viewLifecycleOwner, charactersLeagueObserver)
        viewModel.getRacesFromLeague().observe(viewLifecycleOwner, racesLeagueObserver)
        viewModel.getStarshipsFromLeague().observe(viewLifecycleOwner, starshipsLeagueObserver)
        viewModel.getPlanetsFromLeague().observe(viewLifecycleOwner, planetsLeagueObserver)

        val bottomSheetOptions = BottomSheetDialog(requireContext())
        val bindingSheetOptions = DataBindingUtil.inflate<OptionsBottomSheetBinding>(
            layoutInflater,
            R.layout.options_bottom_sheet,
            null,
            false
        )
        bottomSheetOptions.setContentView(bindingSheetOptions.root)
        bindingSheetOptions.viewDetail.setOnClickListener {
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailsFragment(title, item)
            )
            bottomSheetOptions.cancel()
        }
        bindingSheetOptions.addToLeague.setOnClickListener {
            if (leagueIsOpen) onSNACK(binding.root, viewModel.addToLeage(item, requireContext()))
            else onSNACK(binding.root, requireContext().getString(R.string.league_blocked))
            bottomSheetOptions.cancel()
        }

        bottomSheetLeague = BottomSheetDialog(requireContext())
        bindingSheetLeague = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.league_bottom_sheet,
            null,
            false
        )
        bottomSheetLeague.setContentView(bindingSheetLeague.root)
        bindingSheetLeague.createLeague.setOnClickListener {
            if (leagueIsOpen) {
                viewModel.createLeague(requireContext()) { b: Boolean, s: String ->
                    if (b) leagueIsOpen = false
                    onSNACK(binding.root, s)
                    bottomSheetLeague.cancel()
                }
            } else {
                viewModel.cleanTables()
                onSNACK(
                    binding.root,
                    requireContext()
                        .getString(R.string.league_eliminated)
                )
                leagueIsOpen = true
                bottomSheetLeague.cancel()
            }

            viewModel.switchLeagueStatus(leagueIsOpen)
        }

        binding.rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1))
                    viewModel.onChangeListSize(binding.rvList.adapter!!.itemCount)
            }
        })
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.runFilter(viewModel.itemType.value)
        }
        binding.rvList.adapter = MainItemsAdapter(
            requireContext(),
            MainItemsAdapter.OnClickListener { title, item ->
                this.title = title
                this.item = item
                bottomSheetOptions.show()
            }
        )
        
        viewModel.switchLeagueStatus(leagueIsOpen)
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

    private val charactersLeagueObserver = Observer<List<Character>> { result ->
        bindingSheetLeague.chipGroupCharacters.removeAllViews()
        result.map {
            bindingSheetLeague.chipGroupCharacters.addChip(
                requireContext(),
                it.name,
                it.url,
                it.itemType
            )
        }
    }
    private val racesLeagueObserver = Observer<List<Race>> { result ->
        bindingSheetLeague.chipGroupRaces.removeAllViews()
        result.map {
            bindingSheetLeague.chipGroupRaces.addChip(
                requireContext(),
                it.name,
                it.url,
                it.itemType
            )
        }
    }
    private val starshipsLeagueObserver = Observer<List<Starship>> { result ->
        bindingSheetLeague.chipGroupStarships.removeAllViews()
        result.map {
            bindingSheetLeague.chipGroupStarships.addChip(
                requireContext(),
                it.name,
                it.url,
                it.itemType
            )
        }
    }
    private val planetsLeagueObserver = Observer<List<Planet>> { result ->
        bindingSheetLeague.chipGroupPlanets.removeAllViews()
        result.map {
            bindingSheetLeague.chipGroupPlanets.addChip(
                requireContext(),
                it.name,
                it.url,
                it.itemType
            )
        }
    }

    private fun ChipGroup.addChip(
        context: Context,
        label: String,
        url: String,
        itemType: ItemType
    ) {
        Chip(context).apply {
            id = View.generateViewId()
            text = label
            isClickable = true
            isCheckable = true
            isCheckedIconVisible = false
            isFocusable = true
            tag = itemType
            height = 55
            isCloseIconVisible = true
            setOnCheckedChangeListener { _, _ ->
                if (leagueIsOpen) viewModel.removeFromLeague(url, itemType)
                else onSNACK(binding.root, requireContext().getString(R.string.league_blocked))
            }
            addView(this)
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
                bindingSheetLeague.createLeague.backgroundTintList = when (leagueIsOpen) {
                    true -> ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.green
                        )
                    )
                    else -> ColorStateList.valueOf(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red
                        )
                    )
                }
                bindingSheetLeague.createLeague.text = when (leagueIsOpen) {
                    true -> getString(R.string.create_league)
                    else -> getString(R.string.delete_league)
                }
                bottomSheetLeague.show()
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
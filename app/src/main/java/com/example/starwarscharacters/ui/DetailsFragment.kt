package com.example.starwarscharacters.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.starwarscharacters.R
import com.example.starwarscharacters.application.AppDatabase
import com.example.starwarscharacters.data.DataSource
import com.example.starwarscharacters.data.model.*
import com.example.starwarscharacters.databinding.FragmentDetailsBinding
import com.example.starwarscharacters.domain.RepositoryImpl
import com.example.starwarscharacters.ui.viewModel.MainViewModel
import com.example.starwarscharacters.ui.viewModel.ViewModelFactory
import com.example.starwarscharacters.utils.showToast
import kotlinx.android.synthetic.main.item_detail.view.*
import kotlinx.coroutines.launch


/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private lateinit var itemReceived: Item
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewInflater: View
    private lateinit var itemType: ItemType
    private var isInTheLeague: Boolean? = null

    private val viewModel by activityViewModels<MainViewModel> {
        ViewModelFactory(
            RepositoryImpl(
                DataSource(
                    AppDatabase.getDatabase(requireActivity().applicationContext)
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        requireArguments().let {
            DetailsFragmentArgs.fromBundle(it).also { args ->
                itemReceived = args.item
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)

        itemType = itemReceived.itemType

        when {
            itemType.isCharacter() -> inflateWithCharacter((itemReceived as Character))
            itemType.isRace() -> inflateWithRace((itemReceived as Race))
            itemType.isStarship() -> inflateWithStarship((itemReceived as Starship))
            itemType.isPlanet() -> inflateWithPlanet((itemReceived as Planet))
        }

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.statusInLeague).isVisible = true
        menu.findItem(R.id.myGalacticLeague).isVisible = false

        viewLifecycleOwner.lifecycleScope.launch {
            isInTheLeague = when (itemType) {
                ItemType.CHARACTER -> viewModel.characterInLeague((itemReceived as Character))
                ItemType.RACE -> viewModel.raceInLeague((itemReceived as Race))
                ItemType.STARSHIP -> viewModel.starshipInLeague((itemReceived as Starship))
                ItemType.PLANET -> viewModel.planetInLeague((itemReceived as Planet))
            }
            updateIcon(menu.findItem(R.id.statusInLeague))
        }

        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.statusInLeague -> {
                when {
                    itemType.isCharacter() -> {
                        val character = (itemReceived as Character)
                        when (isInTheLeague) {
                            true -> viewModel.deleteFromLeague(character.asCharacter())
                            else -> viewModel.insertToLeague(character.asCharacter())
                        }
                    }
                    itemType.isRace() -> {
                    }
                    itemType.isStarship() -> {
                    }
                    itemType.isPlanet() -> {
                    }
                }

                isInTheLeague = isInTheLeague?.not()
                showToast(
                    requireContext().getString(
                        when (isInTheLeague) {
                            true -> R.string.added_gl
                            else -> R.string.removed_gl
                        }
                    )
                )
                updateIcon(item)
                false
            }
            else -> false
        }
    }

    private fun updateIcon(item: MenuItem) {
        item.icon = when (isInTheLeague) {
            true -> ContextCompat.getDrawable(requireContext(), R.drawable.ic_star_on)
            else -> ContextCompat.getDrawable(requireContext(), R.drawable.ic_star_off)
        }
    }

    private fun inflateWithPlanet(planet: Planet) {
        setValue(requireContext().getString(R.string.rotation_period), planet.rotationPeriod)
        setValue(requireContext().getString(R.string.orbital_period), planet.orbitalPeriod)
        setValue(requireContext().getString(R.string.diameter), planet.diameter)
        setValue(requireContext().getString(R.string.climate), planet.climate)
        setValue(requireContext().getString(R.string.gravity), planet.gravity)
        setValue(requireContext().getString(R.string.terrain), planet.terrain)
        setValue(requireContext().getString(R.string.surface_water), planet.surfaceWater)
        setValue(requireContext().getString(R.string.residents), planet.residents.size.toString())
        setValue(requireContext().getString(R.string.films), planet.films.size.toString())
        setValue(requireContext().getString(R.string.created), planet.created)
        setValue(requireContext().getString(R.string.edited), planet.edited)
        setValue(requireContext().getString(R.string.url), planet.url)
    }

    private fun inflateWithStarship(starship: Starship) {
        setValue(requireContext().getString(R.string.model), starship.model)
        setValue(requireContext().getString(R.string.manufacturer), starship.manufacturer)
        setValue(requireContext().getString(R.string.cost_in_credits), starship.costInCredits)
        setValue(requireContext().getString(R.string.length), starship.length)
        setValue(
            requireContext().getString(R.string.max_atmosphering_speed),
            starship.maxAtmospheringSpeed
        )
        setValue(requireContext().getString(R.string.crew), starship.crew)
        setValue(requireContext().getString(R.string.passengers), starship.passengers)
        setValue(requireContext().getString(R.string.cargo_capacity), starship.cargoCapacity)
        setValue(requireContext().getString(R.string.consumables), starship.consumables)
        setValue(requireContext().getString(R.string.hyperdrive_rating), starship.hyperdriveRating)
        setValue(requireContext().getString(R.string.mglt), starship.mglt)
        setValue(requireContext().getString(R.string.starship_class), starship.starshipClass)
        setValue(requireContext().getString(R.string.pilots), starship.pilots.size.toString())
        setValue(requireContext().getString(R.string.films), starship.films.size.toString())
        setValue(requireContext().getString(R.string.created), starship.created)
        setValue(requireContext().getString(R.string.edited), starship.edited)
        setValue(requireContext().getString(R.string.url), starship.url)

    }

    private fun inflateWithRace(race: Race) {
        setValue(requireContext().getString(R.string.classification), race.classification)
        setValue(requireContext().getString(R.string.designation), race.designation)
        setValue(requireContext().getString(R.string.average_height), race.averageHeight)
        setValue(requireContext().getString(R.string.skin_colors), race.skinColor)
        setValue(requireContext().getString(R.string.hair_colors), race.hairColor)
        setValue(requireContext().getString(R.string.eye_colors), race.eyeColor)
        setValue(requireContext().getString(R.string.average_lifespan), race.averageLifespan)
        setValue(requireContext().getString(R.string.homeworld), race.homeWorld)
        setValue(requireContext().getString(R.string.language), race.language)
        setValue(requireContext().getString(R.string.people), race.people.size.toString())
        setValue(requireContext().getString(R.string.films), race.films.size.toString())
        setValue(requireContext().getString(R.string.created), race.created)
        setValue(requireContext().getString(R.string.edited), race.edited)
        setValue(requireContext().getString(R.string.url), race.url)
    }

    private fun inflateWithCharacter(character: Character) {
        setValue(requireContext().getString(R.string.mass), character.mass)
        setValue(requireContext().getString(R.string.hair_color), character.hairColor)
        setValue(requireContext().getString(R.string.skin_color), character.skinColor)
        setValue(requireContext().getString(R.string.birth_year), character.birthYear)
        setValue(requireContext().getString(R.string.gender), character.gender)
        setValue(requireContext().getString(R.string.homeworld), character.homeWorld)
        setValue(requireContext().getString(R.string.films), character.films.size.toString())
        setValue(requireContext().getString(R.string.species), character.species.size.toString())
        setValue(requireContext().getString(R.string.vehicles), character.vehicles.size.toString())
        setValue(
            requireContext().getString(R.string.starships),
            character.starships.size.toString()
        )
        setValue(requireContext().getString(R.string.created), character.created)
        setValue(requireContext().getString(R.string.edited), character.edited)
        setValue(requireContext().getString(R.string.url), character.url)
    }

    @SuppressLint("SetTextI18n")
    private fun setValue(title: String, value: String) {
        viewInflater = layoutInflater.inflate(
            R.layout.item_detail,
            null
        )
        viewInflater.title.text = "$title: "
        viewInflater.value.text = value
        binding.linear.addView(viewInflater)
    }
}

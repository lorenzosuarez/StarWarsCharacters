package com.example.starwarscharacters.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.starwarscharacters.R
import com.example.starwarscharacters.data.model.*
import com.example.starwarscharacters.databinding.FragmentDetailsBinding
import kotlinx.android.synthetic.main.item_detail.view.*
import kotlinx.android.synthetic.main.item_detail.view.title
import kotlinx.android.synthetic.main.item_row.view.*


/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private lateinit var item: Item
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewInflater: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        requireArguments().let {
            DetailsFragmentArgs.fromBundle(it).also { args ->
                item = args.item
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

        val itemType: ItemType = item.itemType

        when {
            itemType.isCharacter() -> inflateWithCharacter((item as Character))
            itemType.isRace() -> inflateWithRace((item as Race))
            itemType.isStarship() -> inflateWithStarship((item as Starship))
            itemType.isPlanet() -> inflateWithPlanet((item as Planet))
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.statusInLeague).isVisible = true
        menu.findItem(R.id.myGalacticLeague).isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.statusInLeague -> {
                item.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_star_on)
                false
            }
            else -> false
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

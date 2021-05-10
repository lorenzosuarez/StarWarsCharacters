package com.example.starwarscharacters.ui.viewModel


import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.starwarscharacters.R
import com.example.starwarscharacters.application.AppConstants.MAX_CHARACTERS
import com.example.starwarscharacters.application.AppConstants.MAX_PLANETS
import com.example.starwarscharacters.application.AppConstants.MAX_RACES
import com.example.starwarscharacters.application.AppConstants.MAX_STARSHIPS
import com.example.starwarscharacters.application.AppConstants.TAG
import com.example.starwarscharacters.data.model.*
import com.example.starwarscharacters.domain.Repository
import com.example.starwarscharacters.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _existLeague = MutableLiveData(true)
    val existLeague: LiveData<Boolean> = _existLeague

    private val _page = MutableLiveData<Int>()
    val page: LiveData<Int>
        get() = _page

    private val _listSize = MutableLiveData<Int>()
    val listSize: LiveData<Int>
        get() = _listSize

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _itemType = MutableLiveData<ItemType?>()
    val itemType: MutableLiveData<ItemType?>
        get() = _itemType

    private val _items = MutableLiveData<List<Item>>()
    val items: LiveData<List<Item>>
        get() = _items

    private val _characters = MutableLiveData<Resource<CharacterList>>()
    val character: LiveData<Resource<CharacterList>>
        get() = _characters

    private val _races = MutableLiveData<Resource<RaceList>>()
    val races: LiveData<Resource<RaceList>>
        get() = _races

    private val _starships = MutableLiveData<Resource<StarshipList>>()
    val starships: LiveData<Resource<StarshipList>>
        get() = _starships

    private val _planets = MutableLiveData<Resource<PlanetList>>()
    val planets: LiveData<Resource<PlanetList>>
        get() = _planets

    private val hasNext = mutableStateOf(true)

    //Data LEAGUE
    private val characterListLiveData: MutableLiveData<List<Character>> = MutableLiveData()
    private var _characterList: MutableList<Character> = ArrayList()
    fun getCharactersFromLeague(): LiveData<List<Character>> {
        return characterListLiveData
    }

    private var raceListLiveData: MutableLiveData<List<Race>> = MutableLiveData()
    private var _raceList: MutableList<Race> = ArrayList()
    fun getRacesFromLeague(): LiveData<List<Race>> {
        return raceListLiveData
    }

    var starshipListLiveData: MutableLiveData<List<Starship>> = MutableLiveData()
    private var _starshipList: MutableList<Starship> = ArrayList()
    fun getStarshipsFromLeague(): LiveData<List<Starship>> {
        return starshipListLiveData
    }

    var planetListLiveData: MutableLiveData<List<Planet>> = MutableLiveData()
    private var _planetList: MutableList<Planet> = ArrayList()
    fun getPlanetsFromLeague(): LiveData<List<Planet>> {
        return planetListLiveData
    }

    init {
        _listSize.value = 0
        _page.value = 1
        _loading.value = false
        _itemType.value = ItemType.CHARACTER
    }

    fun removeFromLeague(url: String, itemType: ItemType) {
        when {
            itemType.isCharacter() -> {
                _characterList.removeAll { it.url == url }
                characterListLiveData.value = _characterList
            }
            itemType.isRace() -> {
                _raceList.removeAll { it.url == url }
                raceListLiveData.value = _raceList
            }
            itemType.isStarship() -> {
                _starshipList.removeAll { it.url == url }
                starshipListLiveData.value = _starshipList
            }
            itemType.isPlanet() -> {
                _planetList.removeAll { it.url == url }
                planetListLiveData.value = _planetList
            }
        }
    }

    fun addToLeage(obj: Any, context: Context): String {
        return when (obj) {
            is Character -> checkIfUploadCharacter(obj, context)
            is Race -> checkIfUploadRace(obj, context)
            is Starship -> checkIfUploadStarship(obj, context)
            is Planet -> checkIfUploadPlanet(obj, context)
            else -> context.getString(R.string.unknown_error)
        }
    }

    private fun checkIfUploadPlanet(planet: Planet, context: Context): String {
        return when {
            _planetList.size >= MAX_PLANETS -> context.getString(R.string.maximum_planets_added)
            _planetList.contains(planet) -> context.getString(R.string.already_exists)
            else -> {
                _planetList.add(planet)
                planetListLiveData.value = _planetList
                context.getString(R.string.added_gl)
            }
        }
    }

    private fun checkIfUploadStarship(starship: Starship, context: Context): String {
        return when {
            _starshipList.size >= MAX_STARSHIPS -> context.getString(R.string.maximum_starships_added)
            _starshipList.contains(starship) -> context.getString(R.string.already_exists)
            else -> {
                _starshipList.add(starship)
                starshipListLiveData.value = _starshipList
                context.getString(R.string.added_gl)
            }
        }
    }

    private fun checkIfUploadRace(race: Race, context: Context): String {
        return when {
            _raceList.size >= MAX_RACES -> context.getString(R.string.maximum_races_added)
            _raceList.contains(race) -> context.getString(R.string.already_exists)
            else -> {
                _raceList.add(race)
                raceListLiveData.value = _raceList
                context.getString(R.string.added_gl)
            }
        }
    }

    private fun checkIfUploadCharacter(character: Character, context: Context): String {
        return when {
            _characterList.size >= MAX_CHARACTERS -> context.getString(R.string.maximum_characters_added)
            _characterList.contains(character) -> context.getString(R.string.already_exists)
            else -> {
                _characterList.add(character)
                characterListLiveData.value = _characterList
                context.getString(R.string.added_gl)
            }
        }
    }

    private fun fetchCharacters() {
        viewModelScope.launch {
            _characters.value = Resource.Loading
            try {
                _characters.value = repository.getCharacters()
            } catch (e: Exception) {
                _characters.value = Resource.Failure(e)
            }
        }
    }

    private fun fetchRaces() {
        viewModelScope.launch {
            _races.value = Resource.Loading
            try {
                _races.value = repository.getRaces()
            } catch (e: Exception) {
                _races.value = Resource.Failure(e)
            }
        }
    }

    private fun fetchStarships() {
        viewModelScope.launch {
            _starships.value = Resource.Loading
            try {
                _starships.value = repository.getStarships()
            } catch (e: Exception) {
                _starships.value = Resource.Failure(e)
            }
        }
    }

    private fun fetchPlanets() {
        viewModelScope.launch {
            _planets.value = Resource.Loading
            try {
                _planets.value = repository.getPlanets()
            } catch (e: Exception) {
                _planets.value = Resource.Failure(e)
            }
        }
    }

    private fun resetValues() {
        _page.value = 1
        _listSize.value = 0
    }

    private fun appendItems(obj: Any) {
        val current = ArrayList(this._items.value)
        current.addAll(
            when (obj) {
                is CharacterList -> obj.characterList
                is RaceList -> obj.raceList
                is StarshipList -> obj.starshipList
                is PlanetList -> obj.planetList
                else -> arrayListOf()
            }
        )
        this._items.value = current
    }

    fun runFilter(itemType: ItemType? = ItemType.CHARACTER) {
        if (itemType != null) {
            if (_itemType.value != itemType) _items.value = emptyList()
            _itemType.value = itemType

            when {
                itemType.isCharacter() -> {
                    if (_characters.value == null) fetchCharacters()
                    else if (_characters.value is Resource.Success)
                        updateItems((_characters.value as Resource.Success).data)
                }
                itemType.isRace() -> {
                    if (_races.value == null) fetchRaces()
                    else if (_races.value is Resource.Success)
                        updateItems((_races.value as Resource.Success).data)
                }
                itemType.isStarship() -> {
                    if (_starships.value == null) fetchStarships()
                    else if (_starships.value is Resource.Success)
                        updateItems((_starships.value as Resource.Success).data)
                }
                itemType.isPlanet() -> {
                    if (_planets.value == null) fetchPlanets()
                    else if (_planets.value is Resource.Success)
                        updateItems((_planets.value as Resource.Success).data)
                }
            }
        }

        _itemType.value = itemType!!
        resetValues()
    }

    fun updateItems(obj: Any) {
        _items.value = when (obj) {
            is CharacterList -> obj.characterList
            is RaceList -> obj.raceList
            is StarshipList -> obj.starshipList
            is PlanetList -> obj.planetList
            else -> listOf()
        }
    }

    fun nextPage() {
        viewModelScope.launch {
            _loading.value = true

            Log.d(TAG, "NextPage -> ${_page.value}")

            if (_page.value!! >= 1 && hasNext.value) {

                val result: Any = when ((itemType.value as ItemType)) {
                    ItemType.CHARACTER -> repository.charactersFromPage(page = _page.value!!)
                    ItemType.RACE -> repository.racesFromPage(page = _page.value!!)
                    ItemType.STARSHIP -> repository.starshipsFromPage(page = _page.value!!)
                    ItemType.PLANET -> repository.planetsFromPage(page = _page.value!!)
                }

                Log.d(TAG, "appendItems $result")

                hasNext.value = !(result as ResponseHeader).next.isNullOrBlank()

                appendItems(result)
            }
            _loading.value = false
        }
    }

    fun onChangeListSize(size: Int) {
        _listSize.value = size
    }

    fun incrementNumberPage() {
        _page.value = _page.value?.inc()
    }

    /*** DATA FROM DB ***/
    fun getLocalData() {

        viewModelScope.launch {
            _loading.value = true
            //Characters
            if (_characterList.isEmpty()) {
                _characterList.addAll(withContext(Dispatchers.IO) {
                    repository.getLocalCharacters().asCharacters()
                })
                characterListLiveData.value = _characterList
            }
            //Races
            if (_raceList.isEmpty()) {
                _raceList.addAll(withContext(Dispatchers.IO) {
                    repository.getLocalRaces().asRaces()
                })
                raceListLiveData.value = _raceList
            }
            //Starships
            if (_starshipList.isEmpty()) {
                _starshipList.addAll(withContext(Dispatchers.IO) {
                    repository.getLocalStarships().asStarships()
                })
            }
            starshipListLiveData.value = _starshipList
            //Planets
            if (_planetList.isEmpty()) {
                _planetList.addAll(withContext(Dispatchers.IO) {
                    repository.getLocalPlanets().asPlanet()
                })
                planetListLiveData.value = _planetList
            }
            _loading.value = false
        }
    }

    fun characterInLeague(character: Character): Boolean =
        _characterList.contains(character)

    fun raceInLeague(race: Race): Boolean =
        _raceList.contains(race)

    fun starshipInLeague(starship: Starship): Boolean =
        _starshipList.contains(starship)

    fun planetInLeague(planet: Planet): Boolean =
        _planetList.contains(planet)

    fun createLeague(context: Context, onSuccess: (Boolean, String) -> Unit) {
        if (_characterList.isEmpty()) {
            onSuccess(false, context.getString(R.string.incomplete_data))
            return
        }

        var containsCharacter = false
        _characterList.forEach { character ->
            _raceList.forEach {
                if (!containsCharacter) containsCharacter =
                    when (it.people.contains(character.url)) {
                        true -> true
                        else -> false
                    }
            }
        }
        if (!containsCharacter) {
            onSuccess(false, context.getString(R.string.no_race_for_character))
            return
        }
        //Insert Characters
        val charactersEntity: MutableList<CharacterEntity> = arrayListOf()
        _characterList.map {
            charactersEntity.add(it.asCharacterEntity())
        }
        insertEntitiesToLeague(charactersEntity = charactersEntity, type = ItemType.CHARACTER)
        //Insert Race
        val racesEntity: MutableList<RaceEntity> = arrayListOf()
        _raceList.map {
            racesEntity.add(it.asRaceEntity())
        }
        insertEntitiesToLeague(racesEntity = racesEntity, type = ItemType.RACE)
        //Insert Starships
        val starshipsEntity: MutableList<StarshipEntity> = arrayListOf()
        _starshipList.map {
            starshipsEntity.add(it.asStarshipEntity())
        }
        insertEntitiesToLeague(starshipsEntity = starshipsEntity, type = ItemType.STARSHIP)
        //Insert Planets
        val planetsEntity: MutableList<PlanetEntity> = arrayListOf()
        _planetList.map {
            planetsEntity.add(it.asPlanetEntity())
        }
        insertEntitiesToLeague(planetsEntity = planetsEntity, type = ItemType.PLANET)
        onSuccess(true, context.getString(R.string.league_created))
    }

    fun cleanTables() {
        viewModelScope.launch {
            repository.deleteCharacters()
            repository.deleteRaces()
            repository.deleteStarships()
            repository.deletePlanets()
        }

        _characterList.clear()
        _raceList.clear()
        _starshipList.clear()
        _planetList.clear()

        characterListLiveData.value = _characterList
        raceListLiveData.value = _raceList
        starshipListLiveData.value = _starshipList
        planetListLiveData.value = _planetList
    }

    private fun insertEntitiesToLeague(
        charactersEntity: List<CharacterEntity>? = arrayListOf(),
        racesEntity: List<RaceEntity>? = arrayListOf(),
        starshipsEntity: List<StarshipEntity>? = arrayListOf(),
        planetsEntity: List<PlanetEntity>? = arrayListOf(),
        type: ItemType
    ) {
        when {
            type.isCharacter() -> viewModelScope.launch {
                repository.insertCharacters(charactersEntity!!)
            }
            type.isRace() -> viewModelScope.launch {
                repository.insertRaces(racesEntity!!)
            }
            type.isStarship() -> viewModelScope.launch {
                repository.insertStarships(starshipsEntity!!)
            }
            type.isPlanet() -> viewModelScope.launch {
                repository.insertPlanets(planetsEntity!!)
            }
        }
    }

    fun switchLeagueStatus(status: Boolean) {
        _existLeague.value?.let {
            _existLeague.value = status
        }
    }

}


package com.example.starwarscharacters.ui.viewModel


import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.starwarscharacters.R
import com.example.starwarscharacters.application.AppConstants.MAX_CHARACTERS
import com.example.starwarscharacters.application.AppConstants.MAX_RACES
import com.example.starwarscharacters.application.AppConstants.TAG
import com.example.starwarscharacters.data.model.*
import com.example.starwarscharacters.domain.Repository
import com.example.starwarscharacters.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */

class MainViewModel(private val repository: Repository) : ViewModel() {
    private val _page = MutableLiveData<Int>()
    val page: LiveData<Int>
        get() = _page

    private val _listSize = MutableLiveData<Int>()
    val listSize: LiveData<Int>
        get() = _listSize

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean>
        get() = _loading

    private val _itemType = MutableLiveData<ItemType>()
    val itemType: LiveData<ItemType>
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
    var _starshipList: MutableList<Starship> = ArrayList()

    var planetListLiveData: MutableLiveData<List<Planet>> = MutableLiveData()
    var _planetList: MutableList<Planet> = ArrayList()


    init {
        _listSize.value = 0
        _page.value = 1
        _loading.value = false
        _itemType.value = ItemType.CHARACTER
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
        return ""
    }

    private fun checkIfUploadStarship(starship: Starship, context: Context): String {
        return ""
    }

    private fun checkIfUploadRace(race: Race, context: Context): String {
        return when {
            _raceList.size >= MAX_RACES -> context.getString(R.string.no_race_for_character)
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
                var containsCharacter: String? = null
                _raceList.forEach {
                    containsCharacter = it.people.find { url -> character.url == url }
                }
                if (containsCharacter == null) context.getString(R.string.no_race_for_character)
                else {
                    _characterList.add(character)
                    characterListLiveData.value = _characterList
                    context.getString(R.string.added_gl)
                }
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

    fun insertToLeague(character: CharacterEntity) {
        viewModelScope.launch {
            repository.insertCharacter(character)
        }
    }

    fun getLocalCharacters() = liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        try {
            emit(repository.getLocalCharacters())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }

    }

    suspend fun characterInLeague(character: Character): Boolean =
        repository.characterInLeague(character.url)

    suspend fun raceInLeague(race: Race): Boolean? = false
    suspend fun starshipInLeague(starship: Starship): Boolean? = false
    suspend fun planetInLeague(planet: Planet): Boolean? = false

    fun deleteFromLeague(characterEntity: CharacterEntity) {
        viewModelScope.launch {
            repository.deleteCharacter(characterEntity)
        }
    }

}


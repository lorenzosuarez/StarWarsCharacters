package com.example.starwarscharacters.domain

import com.example.starwarscharacters.data.DataSource
import com.example.starwarscharacters.data.model.*
import com.example.starwarscharacters.vo.Resource

/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */
class RepositoryImpl(private val dataSource: DataSource) : Repository {

    override suspend fun charactersFromPage(page: Int): CharacterList {
        return dataSource.charactersFromPage(page = page)
    }

    override suspend fun racesFromPage(page: Int): RaceList {
        return dataSource.racesFromPage(page = page)
    }

    override suspend fun starshipsFromPage(page: Int): StarshipList {
        return dataSource.starshipsFromPage(page = page)
    }

    override suspend fun planetsFromPage(page: Int): PlanetList {
        return dataSource.planetsFromPage(page = page)
    }

    override suspend fun getCharacters(): Resource<CharacterList> {
        return dataSource.getCharacters()
    }

    override suspend fun getRaces(): Resource<RaceList> {
        return dataSource.getRaces()
    }

    override suspend fun getStarships(): Resource<StarshipList> {
        return dataSource.getStarships()
    }

    override suspend fun getPlanets(): Resource<PlanetList> {
        return dataSource.getPlanets()
    }


    //
    override suspend fun getLocalCharacters(): Resource<List<CharacterEntity>> {
        return dataSource.getLocalCharacters()
    }

    override suspend fun insertCharacter(characterEntity: CharacterEntity) {
        dataSource.insertCharacterIntoRoom(characterEntity)
    }

    override suspend fun deleteCharacter(characterEntity: CharacterEntity) {
        dataSource.deleteCharacterFromRoom(characterEntity)
    }

    override suspend fun characterInLeague(url: String) : Boolean =
        dataSource.characterInLeague(url)
}
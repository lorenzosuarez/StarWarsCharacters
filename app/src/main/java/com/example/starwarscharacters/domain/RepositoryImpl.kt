package com.example.starwarscharacters.domain

import com.example.starwarscharacters.data.DataSource
import com.example.starwarscharacters.data.model.*
import com.example.starwarscharacters.vo.Resource

/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */
class RepositoryImpl(private val dataSource: DataSource) : Repository {
    //Network
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

    //Room
    //CHARACTERS
    override suspend fun getLocalCharacters(): List<CharacterEntity> {
        return dataSource.getLocalCharacters()
    }

    override suspend fun insertCharacters(characters: List<CharacterEntity>) {
        dataSource.insertCharactersIntoRoom(characters)
    }

    override suspend fun deleteCharacters() {
        dataSource.deleteCharactersFromRoom()
    }

    //RACES
    override suspend fun getLocalRaces(): List<RaceEntity> {
        return dataSource.getLocalRaces()
    }

    override suspend fun insertRaces(races: List<RaceEntity>) {
        dataSource.insertRacesIntoRoom(races)
    }

    override suspend fun deleteRaces() {
        dataSource.deleteRacesFromRoom()
    }

    //SARSHIPS
    override suspend fun getLocalStarships(): List<StarshipEntity> {
        return dataSource.getLocalStarships()
    }

    override suspend fun insertStarships(starships: List<StarshipEntity>) {
        dataSource.insertStarshipsIntoRoom(starships)
    }

    override suspend fun deleteStarships() {
        dataSource.deleteStarshipsFromRoom()
    }

    //PLANETS
    override suspend fun getLocalPlanets(): List<PlanetEntity> {
        return dataSource.getLocalPlanets()
    }

    override suspend fun insertPlanets(planets: List<PlanetEntity>) {
        dataSource.insertPlanetsIntoRoom(planets)
    }

    override suspend fun deletePlanets() {
        dataSource.deletePlanetsFromRoom()
    }
}
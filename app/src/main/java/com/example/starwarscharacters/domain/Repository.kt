package com.example.starwarscharacters.domain

import com.example.starwarscharacters.data.model.*
import com.example.starwarscharacters.vo.Resource

/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */
interface Repository {
    //Network
    suspend fun charactersFromPage(page: Int): CharacterList
    suspend fun racesFromPage(page: Int): RaceList
    suspend fun starshipsFromPage(page: Int): StarshipList
    suspend fun planetsFromPage(page: Int): PlanetList
    suspend fun getCharacters(): Resource<CharacterList>
    suspend fun getRaces(): Resource<RaceList>
    suspend fun getStarships(): Resource<StarshipList>
    suspend fun getPlanets(): Resource<PlanetList>
    //Room
    //CHARACTERS ->
    suspend fun getLocalCharacters(): List<CharacterEntity>
    suspend fun insertCharacters(characters: List<CharacterEntity>)
    suspend fun deleteCharacters()
    //RACES ->
    suspend fun getLocalRaces(): List<RaceEntity>
    suspend fun insertRaces(races: List<RaceEntity>)
    suspend fun deleteRaces()
    //STARSHIPS
    suspend fun getLocalStarships(): List<StarshipEntity>
    suspend fun insertStarships(starships: List<StarshipEntity>)
    suspend fun deleteStarships()
    //PLANETS
    suspend fun getLocalPlanets(): List<PlanetEntity>
    suspend fun insertPlanets(planets: List<PlanetEntity>)
    suspend fun deletePlanets()
}
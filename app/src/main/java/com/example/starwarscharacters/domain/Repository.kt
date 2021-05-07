package com.example.starwarscharacters.domain

import com.example.starwarscharacters.data.model.*
import com.example.starwarscharacters.vo.Resource

/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */
interface Repository {
    suspend fun charactersFromPage(page: Int): CharacterList
    suspend fun racesFromPage(page: Int): RaceList
    suspend fun starshipsFromPage(page: Int): StarshipList
    suspend fun planetsFromPage(page: Int): PlanetList

    suspend fun getCharacters(): Resource<CharacterList>
    suspend fun getRaces(): Resource<RaceList>
    suspend fun getStarships(): Resource<StarshipList>
    suspend fun getPlanets(): Resource<PlanetList>
}
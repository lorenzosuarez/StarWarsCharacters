package com.example.starwarscharacters.data

import com.example.starwarscharacters.data.model.*
import com.example.starwarscharacters.vo.Resource
import com.example.starwarscharacters.vo.RetrofitClient

/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */

class DataSource {
    suspend fun charactersFromPage(page : Int): CharacterList {
        return RetrofitClient.webService.charactersFromPage(page = page)
    }

    suspend fun racesFromPage(page : Int): RaceList {
        return RetrofitClient.webService.racesFromPage(page = page)
    }

    suspend fun starshipsFromPage(page : Int): StarshipList {
        return RetrofitClient.webService.starshipsFromPage(page = page)
    }

    suspend fun planetsFromPage(page : Int): PlanetList {
        return RetrofitClient.webService.planetsFromPage(page = page)
    }

    suspend fun getCharacters(): Resource<CharacterList> {
        return Resource.Success(RetrofitClient.webService.getCharacters())
    }

    suspend fun getRaces(): Resource<RaceList> {
        return Resource.Success(RetrofitClient.webService.getRaces())
    }

    suspend fun getStarships(): Resource<StarshipList> {
        return Resource.Success(RetrofitClient.webService.getStarships())
    }

    suspend fun getPlanets(): Resource<PlanetList> {
        return Resource.Success(RetrofitClient.webService.getPlanets())
    }

}
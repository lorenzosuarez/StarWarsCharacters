package com.example.starwarscharacters.data.remote

import com.example.starwarscharacters.data.model.CharacterList
import com.example.starwarscharacters.data.model.PlanetList
import com.example.starwarscharacters.data.model.RaceList
import com.example.starwarscharacters.data.model.StarshipList
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */
interface WebService {
    @GET("people")
    suspend fun charactersFromPage(
        @Query("page") page: Int
    ): CharacterList

    @GET("species")
    suspend fun racesFromPage(
        @Query("page") page: Int
    ): RaceList

    @GET("starships")
    suspend fun starshipsFromPage(
        @Query("page") page: Int
    ): StarshipList

    @GET("planets")
    suspend fun planetsFromPage(
        @Query("page") page: Int
    ): PlanetList

    //Characters
    @GET("people")
    suspend fun getCharacters() : CharacterList
    //Races
    @GET("species")
    suspend fun getRaces(): RaceList
    //Starships
    @GET("starships")
    suspend fun getStarships(): StarshipList
    //Planets
    @GET("planets")
    suspend fun getPlanets(): PlanetList
}
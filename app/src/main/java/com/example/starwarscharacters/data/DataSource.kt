package com.example.starwarscharacters.data

import com.example.starwarscharacters.application.AppDatabase
import com.example.starwarscharacters.data.model.*
import com.example.starwarscharacters.vo.Resource
import com.example.starwarscharacters.vo.RetrofitClient

/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */

class DataSource(private val appDatabase: AppDatabase) {
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


    //ROOM
    //CHARACTERS ->
    suspend fun insertCharactersIntoRoom(charactersEntity: List<CharacterEntity>){
        appDatabase.itemDao().insertCharactersToLeague(charactersEntity)
    }
    suspend fun getLocalCharacters(): List<CharacterEntity> {
        return appDatabase.itemDao().getCharactersLeague()
    }
    suspend fun deleteCharactersFromRoom() {
        appDatabase.itemDao().deleteCharactersFromLeague()
    }

    //RACES ->
    suspend fun insertRacesIntoRoom(racesEntity: List<RaceEntity>){
        appDatabase.itemDao().insertRacesToLeague(racesEntity)
    }
    suspend fun getLocalRaces(): List<RaceEntity> {
        return appDatabase.itemDao().getRacesLeague()
    }
    suspend fun deleteRacesFromRoom() {
        appDatabase.itemDao().deleteRacesFromLeague()
    }

    //STARSHIPS ->
    suspend fun insertStarshipsIntoRoom(starshipsEntity: List<StarshipEntity>){
        appDatabase.itemDao().insertStarshipsToLeague(starshipsEntity)
    }
    suspend fun getLocalStarships(): List<StarshipEntity> {
        return appDatabase.itemDao().getStarshipsLeague()
    }
    suspend fun deleteStarshipsFromRoom() {
        appDatabase.itemDao().deleteStarshipsFromLeague()
    }

    //PLANETS ->
    suspend fun insertPlanetsIntoRoom(planetsEntity: List<PlanetEntity>){
        appDatabase.itemDao().insertPlanetsToLeague(planetsEntity)
    }
    suspend fun getLocalPlanets(): List<PlanetEntity> {
        return appDatabase.itemDao().getPlanetsLeague()
    }
    suspend fun deletePlanetsFromRoom() {
        appDatabase.itemDao().deletePlanetsFromLeague()
    }

}
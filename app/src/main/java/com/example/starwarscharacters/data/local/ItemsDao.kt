package com.example.starwarscharacters.data.local

import androidx.room.*
import com.example.starwarscharacters.data.model.CharacterEntity
import com.example.starwarscharacters.data.model.PlanetEntity
import com.example.starwarscharacters.data.model.RaceEntity
import com.example.starwarscharacters.data.model.StarshipEntity

/**
 * Created by Lorenzo Suarez on 6/5/2021.
 */

@Dao
interface ItemsDao {
    //CHARACTERS
    @Query("SELECT * FROM characterTable")
    suspend fun getCharactersLeague(): List<CharacterEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharactersToLeague(characters: List<CharacterEntity>)

    @Query("DELETE FROM characterTable")
    suspend fun deleteCharactersFromLeague()

    //RACES
    @Query("SELECT * FROM raceTable")
    suspend fun getRacesLeague(): List<RaceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRacesToLeague(racesEntity: List<RaceEntity>)

    @Query("DELETE FROM raceTable")
    suspend fun deleteRacesFromLeague()

    //STARSHIPS
    @Query("SELECT * FROM starshipTable")
    suspend fun getStarshipsLeague(): List<StarshipEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStarshipsToLeague(starshipsEntity: List<StarshipEntity>)

    @Query("DELETE FROM starshipTable")
    suspend fun deleteStarshipsFromLeague()

    //PLANETS
    @Query("SELECT * FROM planetTable")
    suspend fun getPlanetsLeague(): List<PlanetEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlanetsToLeague(planetsEntity: List<PlanetEntity>)

    @Query("DELETE FROM planetTable")
    suspend fun deletePlanetsFromLeague()
}
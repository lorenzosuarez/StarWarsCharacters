package com.example.starwarscharacters.data.local

import androidx.room.*
import com.example.starwarscharacters.data.model.Character
import com.example.starwarscharacters.data.model.CharacterEntity

/**
 * Created by Lorenzo Suarez on 6/5/2021.
 */

@Dao
interface ItemsDao {
    @Query("SELECT * FROM characterTable")
    suspend fun getCharactersLeague(): List<CharacterEntity>

    @Query("SELECT * FROM characterTable WHERE url = :url")
    suspend fun characterInLeague(url: String): CharacterEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterToLeague(character: CharacterEntity)

    @Delete
    suspend fun deleteCharacterFromLeague(characterEntity: CharacterEntity)
}
package com.example.starwarscharacters.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.starwarscharacters.data.local.ItemsDao
import com.example.starwarscharacters.data.model.CharacterEntity
import com.example.starwarscharacters.data.model.PlanetEntity
import com.example.starwarscharacters.data.model.RaceEntity
import com.example.starwarscharacters.data.model.StarshipEntity
import com.example.starwarscharacters.vo.Converters

/**
 * Created by Lorenzo Suarez on 6/5/2021.
 */
@Database(entities = [CharacterEntity::class, RaceEntity::class, StarshipEntity::class, PlanetEntity::class], version = 2)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemsDao

    companion object {
        private var INSTANCE : AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase{
            INSTANCE = INSTANCE ?: Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "characters table").build()
            return INSTANCE!!
        }

        fun destroyInstance(){
            INSTANCE = null
        }

    }

}
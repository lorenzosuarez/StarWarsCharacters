package com.example.starwarscharacters.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.starwarscharacters.vo.Converters
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */

@Parcelize
data class Character(
    val name: String = "",
    val height: String = "",
    val mass: String = "",
    @SerializedName("hair_color") val hairColor: String = "",
    @SerializedName("skin_color") val skinColor: String = "",
    @SerializedName("eye_color") val eyeColor: String = "",
    @SerializedName("birth_year") val birthYear: String = "",
    val gender: String,
    @SerializedName("homeworld") val homeWorld: String = "",
    val films: ArrayList<String> = arrayListOf(),
    val species: ArrayList<String> = arrayListOf(),
    val vehicles: ArrayList<String> = arrayListOf(),
    val starships: ArrayList<String> = arrayListOf(),
    val created: String = "",
    val edited: String = "",
    val url: String = ""
) : Item(ItemType.CHARACTER) {

    constructor() : this(
        "test",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        "",
        arrayListOf<String>(),
        arrayListOf<String>(),
        arrayListOf<String>(),
        arrayListOf<String>(),
        "",
        "",
        ""
    )
}

@Parcelize
data class CharacterList(
    @SerializedName("results") val characterList: List<Character>
) : ResponseHeader()

@Entity(tableName = "characterTable")
data class CharacterEntity(
    val name: String = "",
    val height: String = "",
    val mass: String = "",
    @ColumnInfo(name = "hair_color") val hairColor: String = "",
    @ColumnInfo(name = "skin_color") val skinColor: String = "",
    @ColumnInfo(name = "eye_color") val eyeColor: String = "",
    @ColumnInfo(name = "birth_year") val birthYear: String = "",
    val gender: String,
    @ColumnInfo(name = "homeworld") val homeWorld: String = "",
    val films: ArrayList<String> = arrayListOf(),
    val species: ArrayList<String> = arrayListOf(),
    val vehicles: ArrayList<String> = arrayListOf(),
    val starships: ArrayList<String> = arrayListOf(),
    val created: String = "",
    val edited: String = "",
    @PrimaryKey
    val url: String
)

fun Character.asCharacter(): CharacterEntity =
    CharacterEntity(
        this.name,
        this.height,
        this.height,
        this.hairColor,
        this.skinColor,
        this.eyeColor,
        this.birthYear,
        this.gender,
        this.homeWorld,
        this.films,
        this.species,
        this.vehicles,
        this.starships,
        this.created,
        this.edited,
        this.url
    )

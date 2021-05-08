package com.example.starwarscharacters.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Lorenzo Suarez on 4/5/2021.
 */

@Parcelize
data class Race(
    val name: String = "",
    val classification: String = "",
    val designation: String = "",
    @SerializedName("average_height") val averageHeight: String = "",
    @SerializedName("skin_color") val skinColor: String = "",
    @SerializedName("hair_color") val hairColor: String = "",
    @SerializedName("eye_color") val eyeColor: String = "",
    @SerializedName("average_lifespan") val averageLifespan: String = "",
    @SerializedName("homeworld") val homeWorld: String? = "",
    val language: String,
    val people: ArrayList<String> = arrayListOf(),
    val films: ArrayList<String> = arrayListOf(),
    val created: String = "",
    val edited: String = "",
    val url: String = ""
) : Item(ItemType.RACE) {

    constructor() : this(
        "", "", "",
        "", "", "",
        "", "", "",
        "", arrayListOf<String>(), arrayListOf<String>(),
        "", "", ""
    )
}

@Parcelize
data class RaceList(
    @SerializedName("results") val raceList: List<Race>
) : ResponseHeader()

@Entity(tableName = "raceTable")
data class RaceEntity(
    val name: String = "",
    val classification: String = "",
    val designation: String = "",
    @ColumnInfo(name = "average_height") val averageHeight: String = "",
    @ColumnInfo(name = "skin_color") val skinColor: String = "",
    @ColumnInfo(name = "hair_color") val hairColor: String = "",
    @ColumnInfo(name = "eye_color") val eyeColor: String = "",
    @ColumnInfo(name = "average_lifespan") val averageLifespan: String = "",
    @ColumnInfo(name = "homeworld") val homeWorld: String? = "",
    val language: String,
    val people: ArrayList<String> = arrayListOf(),
    val films: ArrayList<String> = arrayListOf(),
    val created: String = "",
    val edited: String = "",
    @PrimaryKey
    val url: String = ""
)

fun Race.asRaceEntity(): RaceEntity =
    RaceEntity(
        this.name,
        this.classification,
        this.designation,
        this.averageHeight,
        this.skinColor,
        this.hairColor,
        this.eyeColor,
        this.averageLifespan,
        this.homeWorld,
        this.language,
        this.people,
        this.films,
        this.created,
        this.edited,
        this.url
    )


fun List<RaceEntity>.asRaces(): List<Race> = this.map {
    Race(
        it.name,
        it.classification,
        it.designation,
        it.averageHeight,
        it.skinColor,
        it.hairColor,
        it.eyeColor,
        it.averageLifespan,
        it.homeWorld,
        it.language,
        it.people,
        it.films,
        it.created,
        it.edited,
        it.url
    )
}

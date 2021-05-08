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
data class Planet(
    val name: String = "",
    @SerializedName("rotation_period") val rotationPeriod: String = "",
    @SerializedName("orbital_period") val orbitalPeriod: String = "",
    val diameter: String = "",
    val climate: String = "",
    val gravity: String = "",
    val terrain: String = "",
    @SerializedName("surface_water") val surfaceWater: String = "",
    val population: String = "",
    val residents: ArrayList<String> = arrayListOf(),
    val films: ArrayList<String> = arrayListOf(),
    val created: String = "",
    val edited: String = "",
    val url: String = ""
) : Item(ItemType.PLANET) {

    constructor() : this(
        "", "", "",
        "", "", "",
        "", "", "",
        arrayListOf<String>(), arrayListOf<String>(), "",
        "", ""
    )
}

@Parcelize
data class PlanetList(
    @SerializedName("results") val planetList: List<Planet>
) : ResponseHeader()

@Entity(tableName = "planetTable")
data class PlanetEntity(
    val name: String = "",
    @ColumnInfo(name = "rotation_period") val rotationPeriod: String = "",
    @ColumnInfo(name = "orbital_period") val orbitalPeriod: String = "",
    val diameter: String = "",
    val climate: String = "",
    val gravity: String = "",
    val terrain: String = "",
    @ColumnInfo(name = "surface_water") val surfaceWater: String = "",
    val population: String = "",
    val residents: ArrayList<String> = arrayListOf(),
    val films: ArrayList<String> = arrayListOf(),
    val created: String = "",
    val edited: String = "",
    @PrimaryKey
    val url: String = ""
)

fun Planet.asPlanetEntity(): PlanetEntity =
    PlanetEntity(
        this.name,
        this.rotationPeriod,
        this.orbitalPeriod,
        this.diameter,
        this.climate,
        this.gravity,
        this.terrain,
        this.surfaceWater,
        this.population,
        this.residents,
        this.films,
        this.created,
        this.edited,
        this.url
    )

fun List<PlanetEntity>.asPlanet(): List<Planet> = this.map {
    Planet(
        it.name,
        it.rotationPeriod,
        it.orbitalPeriod,
        it.diameter,
        it.climate,
        it.gravity,
        it.terrain,
        it.surfaceWater,
        it.population,
        it.residents,
        it.films,
        it.created,
        it.edited,
        it.url
    )
}
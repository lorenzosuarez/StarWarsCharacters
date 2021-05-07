package com.example.starwarscharacters.data.model

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

    constructor() : this("", "", "",
            "", "", "",
            "", "", "",
            arrayListOf<String>(), arrayListOf<String>(), "",
            "", "")
}

@Parcelize
data class PlanetList(
        @SerializedName("results") val planetList: List<Planet>
) : ResponseHeader()

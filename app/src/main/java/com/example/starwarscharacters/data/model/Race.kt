package com.example.starwarscharacters.data.model

import android.os.Parcelable
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
    @SerializedName("homeworld") val homeWorld: String = "",
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

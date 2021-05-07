package com.example.starwarscharacters.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by Lorenzo Suarez on 4/5/2021.
 */

@Parcelize
data class Starship(
    val name: String = "",
    val model: String = "",
    val manufacturer: String = "",
    @SerializedName("cost_in_credits") val costInCredits: String = "",
    val length: String = "",
    @SerializedName("max_atmosphering_speed") val maxAtmospheringSpeed: String = "",
    val crew: String = "",
    val passengers: String = "",
    @SerializedName("cargo_capacity") val cargoCapacity: String = "",
    val consumables: String = "",
    @SerializedName("hyperdrive_rating") val hyperdriveRating: String = "",
    @SerializedName("MGLT") val mglt: String = "",
    @SerializedName("starship_class") val starshipClass: String = "",
    val pilots: ArrayList<String> = arrayListOf(),
    val films: ArrayList<String> = arrayListOf(),
    val created: String = "",
    val edited: String = "",
    val url: String = ""
) : Item(ItemType.STARSHIP) {

    constructor() : this(
        "", "", "",
        "", "", "",
        "", "", "",
        "", "", "",
        "", arrayListOf<String>(), arrayListOf<String>(), "", "", ""
    )
}

@Parcelize
data class StarshipList(
    @SerializedName("results") val starshipList: List<Starship>
) : ResponseHeader()


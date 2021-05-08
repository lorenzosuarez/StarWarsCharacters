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

@Entity(tableName = "starshipTable")
data class StarshipEntity(
    val name: String = "",
    val model: String = "",
    val manufacturer: String = "",
    @ColumnInfo(name = "cost_in_credits") val costInCredits: String = "",
    val length: String = "",
    @ColumnInfo(name = "max_atmosphering_speed") val maxAtmospheringSpeed: String = "",
    val crew: String = "",
    val passengers: String = "",
    @ColumnInfo(name = "cargo_capacity") val cargoCapacity: String = "",
    val consumables: String = "",
    @ColumnInfo(name = "hyperdrive_rating") val hyperdriveRating: String = "",
    @ColumnInfo(name = "MGLT") val mglt: String = "",
    @ColumnInfo(name = "starship_class") val starshipClass: String = "",
    val pilots: ArrayList<String> = arrayListOf(),
    val films: ArrayList<String> = arrayListOf(),
    val created: String = "",
    val edited: String = "",
    @PrimaryKey
    val url: String = ""
)

fun Starship.asStarshipEntity(): StarshipEntity =
    StarshipEntity(
        this.name,
        this.model,
        this.manufacturer,
        this.costInCredits,
        this.length,
        this.maxAtmospheringSpeed,
        this.crew,
        this.passengers,
        this.cargoCapacity,
        this.consumables,
        this.hyperdriveRating,
        this.mglt,
        this.starshipClass,
        this.pilots,
        this.films,
        this.created,
        this.edited,
        this.url
    )

fun List<StarshipEntity>.asStarships(): List<Starship> = this.map {
    Starship(
        it.name,
        it.model,
        it.manufacturer,
        it.costInCredits,
        it.length,
        it.maxAtmospheringSpeed,
        it.crew,
        it.passengers,
        it.cargoCapacity,
        it.consumables,
        it.hyperdriveRating,
        it.mglt,
        it.starshipClass,
        it.pilots,
        it.films,
        it.created,
        it.edited,
        it.url
    )
}
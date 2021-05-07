package com.example.starwarscharacters.data.model;

/**
 * Created by Lorenzo Suarez on 4/5/2021.
 */

enum class ItemType {
    CHARACTER,
    RACE,
    STARSHIP,
    PLANET;

    /**
     * Returns `true` if is [CHARACTER] `.
     */
    fun isCharacter() = this == CHARACTER

    /**
     * Returns `true` if is [RACE] `.
     */
    fun isRace() = this == RACE

    /**
     * Returns `true` if is [STARSHIP] `.
     */
    fun isStarship() = this == STARSHIP

    /**
     * Returns `true` if is [PLANET] `.
     */
    fun isPlanet() = this == PLANET
}
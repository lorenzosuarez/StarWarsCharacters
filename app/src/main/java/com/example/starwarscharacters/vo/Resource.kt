package com.example.starwarscharacters.vo

import java.lang.Exception

/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */

sealed class Resource<out T> {
    object Loading: Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val exception: Exception) : Resource<Nothing>()
}

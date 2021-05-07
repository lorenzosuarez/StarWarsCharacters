package com.example.starwarscharacters.vo

import com.example.starwarscharacters.application.AppConstants.BASE_URL
import com.example.starwarscharacters.data.remote.WebService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */

object RetrofitClient {
    val webService: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }


}
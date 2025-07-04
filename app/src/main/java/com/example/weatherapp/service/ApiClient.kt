package com.example.weatherapp.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val baseUrl = "https://api.openweathermap.org/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    val weatherApiService: WeatherApiService by lazy {
                retrofit.create(WeatherApiService::class.java)
    }

    val cityApiService:CityApiService by lazy {
            retrofit.create(CityApiService::class.java)
    }


}
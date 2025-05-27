package com.example.weatherapp.service

import retrofit2.http.GET
import com.example.weatherapp.model.WeatherResponse
import retrofit2.http.Query


interface ApiService {

    @GET("data/3.0/onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "uz"
    ): WeatherResponse
}
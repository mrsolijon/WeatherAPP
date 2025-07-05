package com.example.weatherapp.service

import com.example.weatherapp.model.CityResponse
import retrofit2.http.GET
import com.example.weatherapp.model.WeatherResponse
import retrofit2.http.Query


interface WeatherApiService {
    @GET("data/3.0/onecall")
    suspend fun getWeather(
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "uz"
    ): WeatherResponse
}

interface CityApiService {
    @GET("geo/1.0/direct")
    suspend fun getCityCoordinates(
        @Query("q") cityName: String?,
        @Query("limit") limit: Int = 5,
        @Query("appid") apiKey: String
    ): List<CityResponse>
}
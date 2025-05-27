package com.example.weatherapp.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private val baseUrl = "https://api.openweathermap.org/"

    val apiService: ApiService by lazy {
            Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }


}
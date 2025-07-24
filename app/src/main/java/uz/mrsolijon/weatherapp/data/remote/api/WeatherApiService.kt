package uz.mrsolijon.weatherapp.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import uz.mrsolijon.weatherapp.data.remote.model.WeatherResponse

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
package uz.mrsolijon.weatherapp.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Query
import uz.mrsolijon.weatherapp.data.remote.model.CityResponse

interface GeocodingApiService {
    @GET("geo/1.0/direct")
    suspend fun getCitiesByName(
        @Query("q") cityName: String,
        @Query("limit") limit: Int = 5,
        @Query("appid") apiKey: String
    ): List<CityResponse>
}
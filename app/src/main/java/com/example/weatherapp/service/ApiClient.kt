package com.example.weatherapp.service

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {



    companion object{

        private const val baseUrl = "https://api.openweathermap.org/data/3.0/"

        private var INSTANCE: Retrofit? = null

        fun getRetrofitInstance(): Retrofit {

            var retrofit : Retrofit

            if (INSTANCE==null){

                val retrofitBuilder = Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(baseUrl)
                    .build()
                    .create(Retrofit::class.java)

            }

            retrofit = INSTANCE!!

            return retrofit

        }

    }

}
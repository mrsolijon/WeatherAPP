package com.example.weatherapp.model

import android.graphics.drawable.Icon

data class hourlyForecast(
    val time: String,
    val picPath: String,
    val temp: Int
    )

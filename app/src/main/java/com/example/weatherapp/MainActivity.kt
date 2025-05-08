package com.example.weatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.adapter.HourlyForecastRvAdapter
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.model.hourlyForecast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val currentWeatherFragment = CurrentWeatherFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_container, currentWeatherFragment)
            .commit()







    }





}
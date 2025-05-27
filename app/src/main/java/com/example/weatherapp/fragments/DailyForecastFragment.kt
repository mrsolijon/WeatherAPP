package com.example.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.adapter.DailyForecastRvAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentDailyForecastBinding
import com.example.weatherapp.model.dailyForecastData

class DailyForecastFragment : Fragment(R.layout.fragment_daily_forecast) {
    private var _binding: FragmentDailyForecastBinding? = null
    private val binding get() = _binding!!

    val dailyForecast = listOf<dailyForecastData>(
        dailyForecastData("Dushanba", 20, "sunny"),
        dailyForecastData("Seshanba", 20, "sunny"),
        dailyForecastData("Chorshanba", 20, "sunny"),
        dailyForecastData("Payshanba", 20, "sunny"),
        dailyForecastData("Juma", 20, "sunny"),
        dailyForecastData("Shanba", 20, "sunny"),

        )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDailyForecastBinding.bind(view)

        // Kunlik bashorat RecyclerView
        val adapter = DailyForecastRvAdapter(dailyForecast)
        binding.dailyForecastRV.layoutManager = LinearLayoutManager(context)
        binding.dailyForecastRV.adapter = adapter

        //Ortga qaytish tugmasi
        binding.dailyForecastBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
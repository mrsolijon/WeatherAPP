package uz.mrsolijon.weatherapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.mrsolijon.weatherapp.R
import uz.mrsolijon.weatherapp.adapter.DailyForecastRvAdapter
import uz.mrsolijon.weatherapp.databinding.FragmentDailyForecastBinding
import uz.mrsolijon.weatherapp.viewmodels.WeatherViewModel
import kotlin.getValue

class DailyForecastFragment : Fragment(R.layout.fragment_daily_forecast) {

    private var _binding: FragmentDailyForecastBinding? = null
    private val binding get() = _binding!!
    private val weatherViewModel: WeatherViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDailyForecastBinding.bind(view)

        binding.dailyForecastBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
        observeDailyForecastFlow()

    }


    private fun observeDailyForecastFlow() {
        lifecycleScope.launch {
            weatherViewModel.dailyForecastData.collectLatest { dailyList ->
                if (dailyList.isNotEmpty()) {
                    val dailyAdapter =
                        DailyForecastRvAdapter(requireContext(), dailyList.drop(2).take(6))
                    val (iconRes, status) = CurrentWeatherFragment.getWeatherUI(
                        requireContext(),
                        dailyList[1].weather[0].icon
                    )
                    binding.dailyForecastRV.layoutManager = LinearLayoutManager(context)
                    binding.tomorrowStatusIcon.setImageResource(iconRes)
                    binding.tomorrowStatusWeather?.text = status
                    @SuppressLint("SetTextI18n")
                    binding.tommorrowTemp?.text = "${dailyList[1].temp.day.toInt()}°"
                    @SuppressLint("SetTextI18n")
                    binding.tomorrowHumidity?.text = "${dailyList[1].humidity.toInt()} %"
                    @SuppressLint("SetTextI18n")
                    binding.tomorrowWind?.text = "${dailyList[1].wind_speed} m/s"
                    binding.dailyForecastRV.adapter = dailyAdapter
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Kunlik ob-havo ma'lumotlari mavjud emas.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }

        }
        lifecycleScope.launch {
            weatherViewModel.errorMessage.collectLatest { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(requireContext(), "Xatolik: $it", Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
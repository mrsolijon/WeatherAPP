package uz.mrsolijon.weatherapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import uz.mrsolijon.weatherapp.R
import uz.mrsolijon.weatherapp.databinding.FragmentDailyForecastBinding
import uz.mrsolijon.weatherapp.ui.adapter.DailyForecastRvAdapter
import uz.mrsolijon.weatherapp.ui.viewmodel.WeatherViewModel

@AndroidEntryPoint
class DailyForecastFragment : Fragment(R.layout.fragment_daily_forecast) {

    private var _binding: FragmentDailyForecastBinding? = null
    private val binding get() = _binding!!
    private val weatherViewModel: WeatherViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentDailyForecastBinding.bind(view)

        setupListeners()
        observeDailyForecastFlow()
    }

    private fun observeDailyForecastFlow() {
        lifecycleScope.launch {
            Log.d(
                "DailyForecastFragment",
                "observeDailyForecastFlow:${weatherViewModel.dailyForecastData.value}"
            )
            weatherViewModel.dailyForecastData.collectLatest { dailyList ->
                if (dailyList.isNotEmpty()) {
                    val dailyAdapter = DailyForecastRvAdapter(requireContext(), dailyList.take(7))
                    binding.dailyForecastRV.layoutManager = LinearLayoutManager(context)
                    binding.dailyForecastRV.adapter = dailyAdapter
                } else {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.daily_information_is_not_available),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        lifecycleScope.launch {
            weatherViewModel.errorMessage.collectLatest { errorMessage ->
                errorMessage?.let {
                    Toast.makeText(
                        requireContext(),
                        "${getString(R.string.error)}: $it",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    fun setupListeners() {
        binding.dailyForecastBackBtn.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
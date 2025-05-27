package com.example.weatherapp.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentAddChangeCityBinding

class AddChangeCityFragment : Fragment(R.layout.fragment_add_change_city) {

    private var _binding:FragmentAddChangeCityBinding? = null
    private val binding get()= _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentAddChangeCityBinding.bind(view)

        binding.addChangeCityBackBtn.setOnClickListener {
            findNavController().navigate(R.id.action_addChangeCityFragment_to_currentWeatherFragment)
        }

    }
    override fun onDestroyView() {

        super.onDestroyView()
        _binding = null


    }



}
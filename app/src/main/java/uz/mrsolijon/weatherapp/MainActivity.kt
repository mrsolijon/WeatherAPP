package uz.mrsolijon.weatherapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import uz.mrsolijon.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}
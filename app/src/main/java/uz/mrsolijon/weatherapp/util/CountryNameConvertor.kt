package uz.mrsolijon.weatherapp.util

import java.util.Locale

object CountryNameConvertor {

    fun getFullNameFromCode(countryCode: String): String? {
        return try {
            val locale = Locale("", countryCode)
            locale.displayCountry
        } catch (e: Exception) {
            countryCode
        }
    }
}
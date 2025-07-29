package uz.mrsolijon.weatherapp.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import uz.mrsolijon.weatherapp.data.remote.model.LocationInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationSharedPreferencesManager @Inject constructor(@ApplicationContext private val context: Context) {

    private val PREF_NAME = "location_preferences"
    private val KEY_LATITUDE = "last_latitude"
    private val KEY_LONGITUDE = "last_longitude"
    private val KEY_CITY_NAME = "last_city_name"
    private val KEY_IS_MANUALLY_SELECTED = "is_manually_selected"

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveLastLocation(locationInfo: LocationInfo) {
        sharedPreferences.edit().apply {
            locationInfo.latitude?.let {
                putLong(
                    KEY_LATITUDE,
                    java.lang.Double.doubleToRawLongBits(it)
                )
            } ?: remove(KEY_LATITUDE)
            locationInfo.longitude?.let {
                putLong(
                    KEY_LONGITUDE,
                    java.lang.Double.doubleToRawLongBits(it)
                )
            } ?: remove(KEY_LONGITUDE)
            putString(KEY_CITY_NAME, locationInfo.city)
            putBoolean(KEY_IS_MANUALLY_SELECTED, locationInfo.isManuallySelected)
            apply()
        }
    }

    fun getLastLocation(): LocationInfo {
        val latitudeBits =
            sharedPreferences.getLong(KEY_LATITUDE, java.lang.Double.doubleToRawLongBits(-1.0))
        val longitudeBits =
            sharedPreferences.getLong(KEY_LONGITUDE, java.lang.Double.doubleToRawLongBits(-1.0))

        val latitude =
            if (latitudeBits != java.lang.Double.doubleToRawLongBits(-1.0)) java.lang.Double.longBitsToDouble(
                latitudeBits
            ) else null
        val longitude =
            if (longitudeBits != java.lang.Double.doubleToRawLongBits(-1.0)) java.lang.Double.longBitsToDouble(
                longitudeBits
            ) else null

        val city = sharedPreferences.getString(KEY_CITY_NAME, null)
        val isManuallySelected = sharedPreferences.getBoolean(KEY_IS_MANUALLY_SELECTED, false)

        return LocationInfo(latitude, longitude, city, isManuallySelected = isManuallySelected)
    }

    fun clearLastLocation() {
        sharedPreferences.edit().apply {
            remove(KEY_LATITUDE)
            remove(KEY_LONGITUDE)
            remove(KEY_CITY_NAME)
            remove(KEY_IS_MANUALLY_SELECTED)
            apply()
        }
    }
}
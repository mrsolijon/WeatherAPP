package uz.mrsolijon.weatherapp

import android.app.Application
import android.util.Log
import com.yariksoffice.lingver.Lingver
import uz.mrsolijon.weatherapp.data.local.WeatherAppDatabase
import java.util.Locale

class MyAPP : Application() {
    override fun onCreate() {
        super.onCreate()
        Lingver.init(this, Locale("uz"))
        // Room Database’ni boshlash
        WeatherAppDatabase.getDatabase(this)
        Log.d("MyAPP", "Database initialized")
    }
}

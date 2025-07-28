package uz.mrsolijon.weatherapp

import android.app.Application
import android.util.Log
import com.yariksoffice.lingver.Lingver
import dagger.hilt.android.HiltAndroidApp
import java.util.Locale

@HiltAndroidApp
class MyAPP : Application() {
    override fun onCreate() {
        super.onCreate()
        Lingver.init(this, Locale("uz"))
        Log.d("MyAPP", "Hilt application started.")
    }
}
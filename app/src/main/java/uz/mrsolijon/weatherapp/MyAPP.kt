package uz.mrsolijon.weatherapp

import android.app.Application
import com.yariksoffice.lingver.Lingver
import java.util.Locale

class MyAPP : Application() {
    override fun onCreate() {
        super.onCreate()
        Lingver.init(this, Locale("uz"))
    }
}

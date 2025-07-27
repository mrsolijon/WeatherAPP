package uz.mrsolijon.weatherapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.mrsolijon.weatherapp.data.local.dao.WeatherDao
import uz.mrsolijon.weatherapp.data.local.entity.WeatherEntity
import uz.mrsolijon.weatherapp.util.Converters

@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class WeatherAppDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: WeatherAppDatabase? = null

        fun getDatabase(context: Context): WeatherAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WeatherAppDatabase::class.java,
                    "weather_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
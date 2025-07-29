package uz.mrsolijon.weatherapp.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.mrsolijon.weatherapp.data.local.WeatherAppDatabase
import uz.mrsolijon.weatherapp.data.local.prefs.LocationSharedPreferencesManager
import uz.mrsolijon.weatherapp.data.remote.api.WeatherApiService
import uz.mrsolijon.weatherapp.data.remote.model.mapper.WeatherDataMapper
import uz.mrsolijon.weatherapp.repository.WeatherRepository
import uz.mrsolijon.weatherapp.util.NetworkHelper
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideWeatherDataMapper(application: Application): WeatherDataMapper {
        return WeatherDataMapper(application)
    }

    @Singleton
    @Provides
    fun provideLocationSharedPreferencesManager(@ApplicationContext context: Context): LocationSharedPreferencesManager {
        return LocationSharedPreferencesManager(context)
    }

    @Singleton
    @Provides
    fun provideWeatherRepository(
        database: WeatherAppDatabase,
        networkHelper: NetworkHelper,
        weatherDataMapper: WeatherDataMapper,
        weatherApiService: WeatherApiService,
        application: Application,
        locationSharedPreferencesManager: LocationSharedPreferencesManager
    ): WeatherRepository {
        return WeatherRepository(
            database,
            networkHelper,
            weatherDataMapper,
            weatherApiService,
            application,
            locationSharedPreferencesManager
        )
    }
}
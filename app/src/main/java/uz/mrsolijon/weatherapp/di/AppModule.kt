package uz.mrsolijon.weatherapp.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.mrsolijon.weatherapp.data.local.WeatherAppDatabase
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
    fun provideWeatherRepository(
        database: WeatherAppDatabase,
        networkHelper: NetworkHelper,
        weatherDataMapper: WeatherDataMapper,
        weatherApiService: WeatherApiService,
        application: Application
    ): WeatherRepository {
        return WeatherRepository(
            database,
            networkHelper,
            weatherDataMapper,
            weatherApiService,
            application
        )
    }
}
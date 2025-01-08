package com.example.bestdaytofish.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bestdaytofish.data.DailyWeather
import com.example.bestdaytofish.network.WeatherService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.bestdaytofish.util.Constants

class WeatherViewModel : ViewModel() {
    var weatherState by mutableStateOf<List<DailyWeather>>(emptyList())
        private set
    
    var isLoading by mutableStateOf(false)
        private set
    
    var error by mutableStateOf<String?>(null)
        private set

    private val weatherService = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(WeatherService::class.java)

    fun fetchWeather(lat: Double = 44.9778, lon: Double = -93.2650) {
        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val response = weatherService.getWeatherForecast(
                    lat = lat,
                    lon = lon,
                    apiKey = Constants.OPENWEATHER_API_KEY
                )
                weatherState = response.list.filterIndexed { index, _ -> index % 8 == 0 }.take(7)
            } catch (e: Exception) {
                error = e.message
            } finally {
                isLoading = false
            }
        }
    }
} 
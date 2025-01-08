package com.example.bestdaytofish.util

import com.example.bestdaytofish.data.DailyWeather
import kotlin.math.max
import kotlin.math.min

object FishingConditions {
    fun calculateFishingScore(weather: DailyWeather): Int {
        // Temperature score (ideal range 60-75°F)
        val tempScore = when (weather.main.temp) {
            in Double.NEGATIVE_INFINITY..32.0 -> 0.0  // Too cold
            in 32.0..50.0 -> 30.0  // Cold
            in 50.0..60.0 -> 70.0  // Good
            in 60.0..75.0 -> 100.0 // Ideal
            in 75.0..85.0 -> 70.0  // Good
            else -> 30.0  // Too hot
        }

        // Wind score (ideal range 3-8 mph)
        val windScore = when (weather.wind.speed) {
            in 0.0..3.0 -> 70.0  // Too calm
            in 3.0..8.0 -> 100.0 // Ideal
            in 8.0..12.0 -> 60.0 // Bit too windy
            in 12.0..15.0 -> 30.0 // Very windy
            else -> 0.0  // Too windy
        }

        // Weather condition score
        val weatherScore = when (weather.weather.firstOrNull()?.main?.lowercase()) {
            "clear" -> 100.0
            "clouds" -> 90.0
            "drizzle" -> 70.0
            "rain" -> 40.0
            "thunderstorm" -> 0.0
            "snow" -> 20.0
            else -> 50.0
        }

        // Calculate final score (weighted average)
        val finalScore = (tempScore * 0.4 + windScore * 0.3 + weatherScore * 0.3)
        
        return finalScore.toInt()
    }

    fun getFishingConditionText(score: Int): String = when (score) {
        in 0..20 -> "Poor"
        in 21..40 -> "Below Average"
        in 41..60 -> "Fair"
        in 61..80 -> "Good"
        else -> "Excellent"
    }
} 
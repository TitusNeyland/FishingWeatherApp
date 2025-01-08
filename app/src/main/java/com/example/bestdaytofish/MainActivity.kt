package com.example.bestdaytofish

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bestdaytofish.data.DailyWeather
import com.example.bestdaytofish.ui.theme.BestDayToFishTheme
import com.example.bestdaytofish.util.FishingConditions
import com.example.bestdaytofish.viewmodel.WeatherViewModel
import java.text.SimpleDateFormat
import java.util.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BestDayToFishTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherScreen()
                }
            }
        }
    }
}

@Composable
fun WeatherScreen(viewModel: WeatherViewModel = viewModel()) {
    LaunchedEffect(Unit) {
        viewModel.fetchWeather()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "7-Day Weather Forecast",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        when {
            viewModel.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
            viewModel.error != null -> {
                Text(
                    text = "Error: ${viewModel.error}",
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(viewModel.weatherState) { weather ->
                        WeatherCard(weather)
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherCard(weather: DailyWeather) {
    val fishingScore = FishingConditions.calculateFishingScore(weather)
    val conditionText = FishingConditions.getFishingConditionText(fishingScore)
    
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatDate(weather.dt),
                    style = MaterialTheme.typography.titleMedium
                )
                FishingScoreIndicator(score = fishingScore)
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text("Temperature: ${weather.main.temp}°F")
                    Text("Wind: ${weather.wind.speed} mph")
                    Text("Humidity: ${weather.main.humidity}%")
                    Text("Conditions: ${weather.weather.firstOrNull()?.main ?: ""}")
                }
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = conditionText,
                        style = MaterialTheme.typography.bodyLarge,
                        color = getFishingScoreColor(fishingScore)
                    )
                }
            }
        }
    }
}

@Composable
fun FishingScoreIndicator(score: Int) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .background(
                color = getFishingScoreColor(score),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "$score%",
            color = Color.White,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun getFishingScoreColor(score: Int): Color = when (score) {
    in 0..20 -> Color.Red
    in 21..40 -> Color(0xFFFF6B6B)  // Light red
    in 41..60 -> Color(0xFFFFB347)  // Orange
    in 61..80 -> Color(0xFF77DD77)  // Light green
    else -> Color(0xFF2ECC71)  // Green
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
    return sdf.format(Date(timestamp * 1000))
}
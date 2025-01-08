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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.material3.MaterialTheme.shapes

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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Best Days to Fish",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                textAlign = TextAlign.Center
            )

            when {
                viewModel.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(50.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
                viewModel.error != null -> {
                    ErrorMessage(viewModel.error!!)
                }
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(viewModel.weatherState) { weather ->
                            WeatherCard(weather)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ErrorMessage(error: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer
        )
    ) {
        Text(
            text = "Error: $error",
            color = MaterialTheme.colorScheme.onErrorContainer,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
fun WeatherCard(weather: DailyWeather) {
    val fishingScore = FishingConditions.calculateFishingScore(weather)
    val conditionText = FishingConditions.getFishingConditionText(fishingScore)
    val gradientColors = getGradientColors(fishingScore)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shapes.large),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
    ) {
        Box(
            modifier = Modifier
                .background(
                    Brush.verticalGradient(
                        colors = gradientColors
                    )
                )
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
                    Column {
                        Text(
                            text = formatDate(weather.dt),
                            style = MaterialTheme.typography.titleLarge,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = conditionText,
                            style = MaterialTheme.typography.titleMedium,
                            color = Color.White.copy(alpha = 0.9f)
                        )
                    }
                    FishingScoreIndicator(score = fishingScore)
                }
                Spacer(modifier = Modifier.height(16.dp))
                WeatherDetails(weather)
            }
        }
    }
}

@Composable
fun WeatherDetails(weather: DailyWeather) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Color.White.copy(alpha = 0.15f),
                shape = shapes.medium
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        WeatherDetailItem(
            label = "Temp",
            value = "${weather.main.temp.toInt()}°F"
        )
        WeatherDetailItem(
            label = "Wind",
            value = "${weather.wind.speed.toInt()} mph"
        )
        WeatherDetailItem(
            label = "Humidity",
            value = "${weather.main.humidity}%"
        )
        WeatherDetailItem(
            label = "Conditions",
            value = weather.weather.firstOrNull()?.main ?: ""
        )
    }
}

@Composable
fun WeatherDetailItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun FishingScoreIndicator(score: Int) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .background(
                Color.White.copy(alpha = 0.15f),
                CircleShape
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = score / 100f,
            modifier = Modifier.fillMaxSize(),
            color = Color.White,
            strokeWidth = 5.dp,
            trackColor = Color.White.copy(alpha = 0.2f)
        )
        Text(
            text = "$score%",
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun getGradientColors(score: Int): List<Color> = when (score) {
    in 0..20 -> listOf(
        Color(0xFF8B0000),
        Color(0xFFD32F2F)
    )
    in 21..40 -> listOf(
        Color(0xFFD32F2F),
        Color(0xFFFF5722)
    )
    in 41..60 -> listOf(
        Color(0xFFFF9800),
        Color(0xFFFFA726)
    )
    in 61..80 -> listOf(
        Color(0xFF7CB342),
        Color(0xFF8BC34A)
    )
    else -> listOf(
        Color(0xFF2E7D32),
        Color(0xFF43A047)
    )
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
    return sdf.format(Date(timestamp * 1000))
}
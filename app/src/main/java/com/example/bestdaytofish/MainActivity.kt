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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

data class BottomNavigationItem(
    val title: String,
    val icon: @Composable () -> Unit,
    val route: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BestDayToFishTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var selectedItem by remember { mutableStateOf(0) }
    
    val navigationItems = listOf(
        BottomNavigationItem(
            title = "Weather",
            icon = { Icon(Icons.Default.Cloud, contentDescription = "Weather") },
            route = "weather"
        ),
        BottomNavigationItem(
            title = "Search",
            icon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            route = "search"
        ),
        BottomNavigationItem(
            title = "Favorites",
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
            route = "favorites"
        ),
        BottomNavigationItem(
            title = "Account",
            icon = { Icon(Icons.Default.Person, contentDescription = "Account") },
            route = "account"
        ),
        BottomNavigationItem(
            title = "FAQ",
            icon = { Icon(Icons.Outlined.Info, contentDescription = "FAQ") },
            route = "faq"
        )
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                navigationItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = item.icon,
                        label = { Text(item.title) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedItem) {
                0 -> WeatherScreen()
                1 -> Text("Search Screen - Coming Soon", modifier = Modifier.align(Alignment.Center))
                2 -> Text("Favorites Screen - Coming Soon", modifier = Modifier.align(Alignment.Center))
                3 -> Text("Account Screen - Coming Soon", modifier = Modifier.align(Alignment.Center))
                4 -> Text("FAQ Screen - Coming Soon", modifier = Modifier.align(Alignment.Center))
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
    val textColor = getDarkerTextColor(fishingScore)
    
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
                            color = textColor,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = conditionText,
                            style = MaterialTheme.typography.titleMedium,
                            color = textColor.copy(alpha = 0.9f)
                        )
                    }
                    FishingScoreIndicator(score = fishingScore, textColor = textColor)
                }
                Spacer(modifier = Modifier.height(16.dp))
                WeatherDetails(weather, textColor)
            }
        }
    }
}

@Composable
fun WeatherDetails(weather: DailyWeather, textColor: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                textColor.copy(alpha = 0.1f),
                shape = shapes.medium
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        WeatherDetailItem(
            label = "Temp",
            value = "${weather.main.temp.toInt()}°F",
            textColor = textColor
        )
        WeatherDetailItem(
            label = "Wind",
            value = "${weather.wind.speed.toInt()} mph",
            textColor = textColor
        )
        WeatherDetailItem(
            label = "Humidity",
            value = "${weather.main.humidity}%",
            textColor = textColor
        )
        WeatherDetailItem(
            label = "Conditions",
            value = weather.weather.firstOrNull()?.main ?: "",
            textColor = textColor
        )
    }
}

@Composable
fun WeatherDetailItem(label: String, value: String, textColor: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = textColor.copy(alpha = 0.7f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            color = textColor,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun FishingScoreIndicator(score: Int, textColor: Color) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .background(
                textColor.copy(alpha = 0.1f),
                CircleShape
            )
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            progress = score / 100f,
            modifier = Modifier.fillMaxSize(),
            color = textColor,
            strokeWidth = 5.dp,
            trackColor = textColor.copy(alpha = 0.2f)
        )
        Text(
            text = "$score%",
            color = textColor,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun getDarkerTextColor(score: Int): Color = when (score) {
    in 0..20 -> Color(0xFF4A0000)  // Dark red
    in 21..40 -> Color(0xFF7A1C1C)  // Darker red
    in 41..60 -> Color(0xFF875100)  // Dark orange
    in 61..80 -> Color(0xFF2C5A1E)  // Dark green
    else -> Color(0xFF1B4F1F)       // Darker green
}

@Composable
fun getGradientColors(score: Int): List<Color> = when (score) {
    in 0..20 -> listOf(
        Color(0xFFFFCDD2),  // Lighter red
        Color(0xFFEF9A9A)   // Light red
    )
    in 21..40 -> listOf(
        Color(0xFFFFCCBC),  // Lighter orange-red
        Color(0xFFFFAB91)   // Light orange-red
    )
    in 41..60 -> listOf(
        Color(0xFFFFE0B2),  // Lighter orange
        Color(0xFFFFCC80)   // Light orange
    )
    in 61..80 -> listOf(
        Color(0xFFDCEDC8),  // Lighter green
        Color(0xFFC5E1A5)   // Light green
    )
    else -> listOf(
        Color(0xFFC8E6C9),  // Lighter green
        Color(0xFFA5D6A7)   // Light green
    )
}

private fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
    return sdf.format(Date(timestamp * 1000))
}
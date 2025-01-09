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
import com.example.bestdaytofish.data.FaqArticle
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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlinx.coroutines.delay
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Divider
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.Menu
import androidx.compose.ui.text.font.FontStyle
import com.example.bestdaytofish.data.Fish
import com.example.bestdaytofish.viewmodel.FishSearchViewModel
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import com.example.bestdaytofish.viewmodel.FavoritesViewModel
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.bestdaytofish.viewmodel.AccountViewModel

data class BottomNavigationItem(
    val title: String,
    val icon: @Composable () -> Unit,
    val route: String
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        
        super.onCreate(savedInstanceState)
        
        var keepSplashScreen = true
        splashScreen.setKeepOnScreenCondition { keepSplashScreen }
        
        setContent {
            LaunchedEffect(Unit) {
                // Simulate loading time
                delay(1000)
                keepSplashScreen = false
            }
            
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
            title = "Guide",
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
                1 -> SearchScreen()
                2 -> FavoritesScreen()
                3 -> AccountScreen()
                4 -> FaqScreen()
            }
        }
    }
}

@Composable
fun TopBar(accountViewModel: AccountViewModel = viewModel()) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.WaterDrop,
                contentDescription = "Water Drop",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            if (accountViewModel.isLoggedIn && accountViewModel.firstName.isNotEmpty()) {
                Text(
                    text = "Welcome, ${accountViewModel.firstName}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        
        IconButton(
            onClick = { /* TODO: Handle menu click */ }
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun WeatherScreen(
    viewModel: WeatherViewModel = viewModel(),
    accountViewModel: AccountViewModel = viewModel()
) {
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
            TopBar(accountViewModel)

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

@Composable
fun WeatherDetailsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Hello",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

private val faqArticles = listOf(
    FaqArticle(
        "Best Time of Day to Fish",
        "Dawn and dusk are generally the best times to fish. During these periods, fish are more active due to cooler temperatures and reduced light. Early morning is particularly good as many fish species feed actively during this time.\n\n" +
        "• Dawn (30 min before sunrise): Prime feeding time\n" +
        "• Early Morning (until 8am): High activity\n" +
        "• Mid-Day: Reduced activity, fish deeper\n" +
        "• Dusk (30 min before sunset): Second feeding period\n" +
        "• Night: Good for certain species",
        "Timing"
    ),
    FaqArticle(
        "Seasonal Fishing Guide",
        "Each season offers unique fishing opportunities:\n\n" +
        "Spring (March-May):\n" +
        "• Fish move to shallow waters\n" +
        "• Pre-spawn feeding increases\n" +
        "• Best time for bass and crappie\n\n" +
        "Summer (June-August):\n" +
        "• Early morning and late evening best\n" +
        "• Fish move to deeper, cooler waters\n" +
        "• Focus on structure and shade\n\n" +
        "Fall (September-November):\n" +
        "• Fish feed heavily for winter\n" +
        "• Follow baitfish movements\n" +
        "• Excellent for all species\n\n" +
        "Winter (December-February):\n" +
        "• Slower presentations needed\n" +
        "• Fish deeper waters\n" +
        "• Ice fishing opportunities",
        "Seasons"
    ),
    FaqArticle(
        "Weather Impact on Fishing",
        "Weather conditions significantly affect fish behavior:\n\n" +
        "Barometric Pressure:\n" +
        "• Rising: Increased activity\n" +
        "• Stable High: Moderate activity\n" +
        "• Falling: Decreased activity\n\n" +
        "Cloud Cover:\n" +
        "• Overcast: Extended feeding periods\n" +
        "• Sunny: Fish seek shade\n\n" +
        "Rain:\n" +
        "• Light Rain: Excellent fishing\n" +
        "• Heavy Rain: Decreased visibility\n" +
        "• Post-Rain: Increased activity",
        "Weather"
    ),
    FaqArticle(
        "Moon Phases and Fishing",
        "Moon phases influence fish feeding patterns:\n\n" +
        "Full Moon:\n" +
        "• Increased nighttime activity\n" +
        "• Better for surface feeding\n" +
        "• Peak feeding 2 days before/after\n\n" +
        "New Moon:\n" +
        "• Enhanced daytime feeding\n" +
        "• Fish more structure-oriented\n" +
        "• Strong dawn/dusk periods\n\n" +
        "Quarter Moons:\n" +
        "• Moderate activity levels\n" +
        "• More predictable patterns",
        "Timing"
    ),
    FaqArticle(
        "Water Temperature Guide",
        "Optimal temperature ranges for common species:\n\n" +
        "Bass:\n" +
        "• Largemouth: 60-75°F\n" +
        "• Smallmouth: 65-70°F\n\n" +
        "Trout:\n" +
        "• Rainbow: 50-65°F\n" +
        "• Brown: 50-65°F\n\n" +
        "Other Species:\n" +
        "• Walleye: 55-70°F\n" +
        "• Catfish: 70-85°F\n" +
        "• Crappie: 60-75°F\n" +
        "• Pike: 55-65°F",
        "Conditions"
    ),
    FaqArticle(
        "Essential Fishing Equipment",
        "Basic gear for successful fishing:\n\n" +
        "Rod & Reel:\n" +
        "• Spinning: Versatile, beginner-friendly\n" +
        "• Baitcasting: Better control, experienced\n" +
        "• Length: 6-7ft for general use\n\n" +
        "Line Types:\n" +
        "• Monofilament: All-purpose, good stretch\n" +
        "• Fluorocarbon: Less visible, sensitive\n" +
        "• Braided: Strong, no stretch\n\n" +
        "Basic Tackle:\n" +
        "• Hooks: Various sizes (4-3/0)\n" +
        "• Sinkers: Split-shot, bullet weights\n" +
        "• Bobbers: Visual strike indication",
        "Equipment"
    ),
    FaqArticle(
        "Bait Selection Guide",
        "Choosing the right bait is crucial:\n\n" +
        "Live Bait:\n" +
        "• Worms: Universal appeal\n" +
        "• Minnows: Great for predatory fish\n" +
        "• Crickets: Pan fish favorite\n\n" +
        "Artificial Lures:\n" +
        "• Soft Plastics: Worms, creatures\n" +
        "• Crankbaits: Various depths\n" +
        "• Spinners: Flash and vibration\n" +
        "• Topwater: Surface action\n\n" +
        "Match the Hatch:\n" +
        "• Observe local forage\n" +
        "• Match size and color\n" +
        "• Consider water clarity",
        "Techniques"
    ),
    FaqArticle(
        "Reading Water Features",
        "Understanding where fish hold:\n\n" +
        "Structure Types:\n" +
        "• Points: Extend into deeper water\n" +
        "• Drop-offs: Depth changes\n" +
        "• Weed edges: Cover and food\n\n" +
        "Current Areas:\n" +
        "• Eddies: Resting spots\n" +
        "• Seams: Different flow speeds\n" +
        "• Undercut banks: Protection\n\n" +
        "Cover Elements:\n" +
        "• Logs: Ambush points\n" +
        "• Rocks: Heat retention\n" +
        "• Vegetation: Oxygen and shelter",
        "Locations"
    ),
    FaqArticle(
        "Fishing Techniques",
        "Popular fishing methods:\n\n" +
        "Casting Techniques:\n" +
        "• Overhead: Basic, accurate\n" +
        "• Sidearm: Under obstacles\n" +
        "• Flip/Pitch: Close quarters\n\n" +
        "Retrieval Styles:\n" +
        "• Steady: Constant speed\n" +
        "• Stop-and-go: Triggering strikes\n" +
        "• Jerking: Erratic action\n\n" +
        "Presentation Tips:\n" +
        "• Match speed to temperature\n" +
        "• Vary retrieve until success\n" +
        "• Consider fish mood",
        "Techniques"
    )
)

@Composable
fun FaqScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Fishing Guide & Tips",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(faqArticles) { article ->
                FaqArticleCard(article)
            }
        }
    }
}

@Composable
fun FaqArticleCard(article: FaqArticle) {
    var expanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Expand",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            Text(
                text = article.category,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = 4.dp)
            )
            
            if (expanded) {
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
                Text(
                    text = article.content,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
fun SearchScreen(
    viewModel: FishSearchViewModel = viewModel(),
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        TextField(
            value = viewModel.searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            placeholder = { Text("Search for fish species...") },
            leadingIcon = { 
                Icon(
                    Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.searchResults) { fish ->
                FishCard(
                    fish = fish,
                    isFavorite = favoritesViewModel.favoriteFish.contains(fish.name),
                    onFavoriteClick = { favoritesViewModel.toggleFavorite(fish) }
                )
            }
        }
    }
}

@Composable
fun FishCard(
    fish: Fish,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = fish.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = fish.scientificName,
                        style = MaterialTheme.typography.bodyMedium,
                        fontStyle = FontStyle.Italic,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                IconButton(onClick = onFavoriteClick) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                        tint = if (isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            
            if (expanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = fish.description)
                
                Spacer(modifier = Modifier.height(8.dp))
                InfoSection("Habitat", fish.habitat)
                InfoSection("Diet", fish.diet)
                InfoSection("Average Size", fish.averageSize)
                InfoSection("Temperature Range", fish.temperatureRange)
                
                InfoSection("Best Baits", fish.bestBaits.joinToString("\n• ", prefix = "• "))
                InfoSection("Fishing Techniques", fish.fishingTechniques.joinToString("\n• ", prefix = "• "))
            }
        }
    }
}

@Composable
private fun InfoSection(title: String, content: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = viewModel(),
    favoritesViewModel: FavoritesViewModel = viewModel()
) {
    val favoriteFish = favoritesViewModel.getFavoriteFish()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Favorites",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            textAlign = TextAlign.Center
        )
        
        if (favoriteFish.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No favorite fish yet",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favoriteFish) { fish ->
                    FishCard(
                        fish = fish,
                        isFavorite = true,
                        onFavoriteClick = { favoritesViewModel.toggleFavorite(fish) }
                    )
                }
            }
        }
    }
}

@Composable
fun AccountScreen(viewModel: AccountViewModel = viewModel()) {
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!viewModel.isLoggedIn) {
            // Login/Sign Up Form
            Text(
                text = if (viewModel.isSignUpMode) "Create Account" else "Login",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 32.dp)
            )
            
            if (viewModel.isSignUpMode) {
                // Name fields only shown during sign up
                OutlinedTextField(
                    value = viewModel.firstName,
                    onValueChange = { viewModel.updateFirstName(it) },
                    label = { Text("First Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "First Name"
                        )
                    }
                )
                
                OutlinedTextField(
                    value = viewModel.lastName,
                    onValueChange = { viewModel.updateLastName(it) },
                    label = { Text("Last Name") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Last Name"
                        )
                    }
                )
            }
            
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.updateEmail(it) },
                label = { Text("Email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email"
                    )
                }
            )
            
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.updatePassword(it) },
                label = { Text("Password") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Password"
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )
            
            if (viewModel.isSignUpMode) {
                OutlinedTextField(
                    value = viewModel.confirmPassword,
                    onValueChange = { viewModel.updateConfirmPassword(it) },
                    label = { Text("Confirm Password") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Confirm Password"
                        )
                    },
                    trailingIcon = {
                        IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                            Icon(
                                imageVector = if (confirmPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                contentDescription = if (confirmPasswordVisible) "Hide password" else "Show password"
                            )
                        }
                    },
                    visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )
            }
            
            if (viewModel.error != null) {
                Text(
                    text = viewModel.error!!,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            Button(
                onClick = { 
                    if (viewModel.isSignUpMode) {
                        viewModel.signUp()
                    } else {
                        viewModel.login()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Text(if (viewModel.isSignUpMode) "Sign Up" else "Login")
            }
            
            TextButton(onClick = { viewModel.toggleSignUpMode() }) {
                Text(
                    if (viewModel.isSignUpMode) {
                        "Already have an account? Login"
                    } else {
                        "Don't have an account? Sign up"
                    }
                )
            }
        } else {
            // Logged In State
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Account",
                    modifier = Modifier
                        .size(100.dp)
                        .padding(bottom = 16.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                
                Text(
                    text = "Welcome${if (viewModel.firstName.isNotEmpty()) ", ${viewModel.firstName}" else ""}!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Text(
                    text = viewModel.email,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                
                Button(
                    onClick = { viewModel.logout() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Logout")
                }
            }
        }
    }
}
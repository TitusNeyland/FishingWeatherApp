package com.example.bestdaytofish.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bestdaytofish.data.Fish
import com.example.bestdaytofish.data.FishRepository

class FavoritesViewModel : ViewModel() {
    var favoriteFish by mutableStateOf<Set<String>>(emptySet())
        private set
    
    fun toggleFavorite(fish: Fish) {
        favoriteFish = if (favoriteFish.contains(fish.name)) {
            favoriteFish - fish.name
        } else {
            favoriteFish + fish.name
        }
    }
    
    fun getFavoriteFish(): List<Fish> {
        return FishRepository.getAllFish().filter { fish ->
            favoriteFish.contains(fish.name)
        }
    }
} 
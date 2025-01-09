package com.example.bestdaytofish.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.bestdaytofish.data.Fish
import com.example.bestdaytofish.data.FishRepository

class FishSearchViewModel : ViewModel() {
    var searchQuery by mutableStateOf("")
        private set
    
    var searchResults by mutableStateOf<List<Fish>>(FishRepository.searchFish(""))
        private set

    fun onSearchQueryChange(query: String) {
        searchQuery = query
        searchResults = FishRepository.searchFish(query)
    }
} 
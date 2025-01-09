package com.example.bestdaytofish.data

data class Fish(
    val name: String,
    val scientificName: String,
    val description: String,
    val habitat: String,
    val diet: String,
    val averageSize: String,
    val temperatureRange: String,
    val bestBaits: List<String>,
    val fishingTechniques: List<String>,
    val imageUrl: String? = null
) 
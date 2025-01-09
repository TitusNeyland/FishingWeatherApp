package com.example.bestdaytofish.data

object FishRepository {
    private val fishList = listOf(
        Fish(
            name = "Largemouth Bass",
            scientificName = "Micropterus salmoides",
            description = "The largemouth bass is one of North America's most popular gamefish. Known for their aggressive strikes and fighting ability.",
            habitat = "Found in lakes, ponds, rivers, and reservoirs with vegetation and structure",
            diet = "Fish, crawfish, frogs, and large insects",
            averageSize = "2-8 pounds",
            temperatureRange = "60-75°F",
            bestBaits = listOf(
                "Plastic worms",
                "Jigs",
                "Crankbaits",
                "Live minnows",
                "Topwater lures"
            ),
            fishingTechniques = listOf(
                "Flipping and pitching",
                "Carolina rig",
                "Texas rig",
                "Topwater walking"
            )
        ),
        Fish(
            name = "Rainbow Trout",
            scientificName = "Oncorhynchus mykiss",
            description = "A beautiful fish known for its distinctive coloring and its popularity among fly fishermen.",
            habitat = "Cold, clear streams, rivers, and lakes",
            diet = "Insects, smaller fish, and crustaceans",
            averageSize = "1-5 pounds",
            temperatureRange = "50-65°F",
            bestBaits = listOf(
                "Powerbait",
                "Worms",
                "Salmon eggs",
                "Small spinners",
                "Flies"
            ),
            fishingTechniques = listOf(
                "Fly fishing",
                "Drift fishing",
                "Spinner casting",
                "Bottom fishing"
            )
        ),
        Fish(
            name = "Walleye",
            scientificName = "Sander vitreus",
            description = "Popular for their excellent taste and challenging fishing experience. Known for their distinctive eyes that reflect light.",
            habitat = "Deep lakes and large rivers",
            diet = "Small fish, leeches, and nightcrawlers",
            averageSize = "2-8 pounds",
            temperatureRange = "55-70°F",
            bestBaits = listOf(
                "Minnows",
                "Nightcrawlers",
                "Leeches",
                "Jigs",
                "Crankbaits"
            ),
            fishingTechniques = listOf(
                "Trolling",
                "Jigging",
                "Live bait rigging",
                "Bottom bouncing"
            )
        ),
        Fish(
            name = "Channel Catfish",
            scientificName = "Ictalurus punctatus",
            description = "Known for their whisker-like barbels and their ability to be caught on various baits.",
            habitat = "Rivers, lakes, and ponds with deep holes",
            diet = "Fish, insects, crustaceans, and plant matter",
            averageSize = "2-10 pounds",
            temperatureRange = "70-85°F",
            bestBaits = listOf(
                "Chicken liver",
                "Nightcrawlers",
                "Cut bait",
                "Stinkbait",
                "Shrimp"
            ),
            fishingTechniques = listOf(
                "Bottom fishing",
                "Drift fishing",
                "Float fishing",
                "Night fishing"
            )
        )
    )

    fun searchFish(query: String): List<Fish> {
        return if (query.isEmpty()) {
            fishList
        } else {
            fishList.filter { fish ->
                fish.name.contains(query, ignoreCase = true) ||
                fish.scientificName.contains(query, ignoreCase = true)
            }
        }
    }
} 
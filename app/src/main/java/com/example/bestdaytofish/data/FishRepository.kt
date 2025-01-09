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
        ),
        Fish(
            name = "Smallmouth Bass",
            scientificName = "Micropterus dolomieu",
            description = "Known for their acrobatic fights and preference for cooler, rocky habitats. Pound for pound, considered one of the best fighting fish.",
            habitat = "Rocky areas in lakes, rivers, and streams with clear water",
            diet = "Crayfish, small fish, insects",
            averageSize = "1-4 pounds",
            temperatureRange = "65-70°F",
            bestBaits = listOf(
                "Tube jigs",
                "Crawfish imitations",
                "Small spinnerbaits",
                "Soft plastic crayfish",
                "Dropshot rigs"
            ),
            fishingTechniques = listOf(
                "Drop shot fishing",
                "Finesse techniques",
                "Drift fishing",
                "Casting to structure"
            )
        ),
        Fish(
            name = "Northern Pike",
            scientificName = "Esox lucius",
            description = "Aggressive predator known for explosive strikes and sharp teeth. Popular among anglers seeking trophy fish.",
            habitat = "Cool lakes and rivers with abundant vegetation",
            diet = "Fish, frogs, small mammals",
            averageSize = "5-15 pounds",
            temperatureRange = "55-65°F",
            bestBaits = listOf(
                "Large spinnerbaits",
                "Swimbaits",
                "Live suckers",
                "Large spoons",
                "Jerkbaits"
            ),
            fishingTechniques = listOf(
                "Casting to weed edges",
                "Trolling",
                "Dead bait fishing",
                "Figure-8 retrieves"
            )
        ),
        Fish(
            name = "Bluegill",
            scientificName = "Lepomis macrochirus",
            description = "Popular panfish known for their abundance and willingness to bite. Great for introducing children to fishing.",
            habitat = "Shallow waters near vegetation and structure",
            diet = "Insects, small crustaceans, worms",
            averageSize = "4-12 ounces",
            temperatureRange = "65-80°F",
            bestBaits = listOf(
                "Worms",
                "Crickets",
                "Small jigs",
                "Bread balls",
                "Corn"
            ),
            fishingTechniques = listOf(
                "Bobber fishing",
                "Light tackle fishing",
                "Ice fishing",
                "Fly fishing"
            )
        ),
        Fish(
            name = "Muskellunge",
            scientificName = "Esox masquinongy",
            description = "Known as the 'fish of 10,000 casts,' muskies are prized for their size and challenging nature.",
            habitat = "Large lakes and rivers with deep structure",
            diet = "Fish, waterfowl, small mammals",
            averageSize = "15-30 pounds",
            temperatureRange = "50-70°F",
            bestBaits = listOf(
                "Large bucktails",
                "Crankbaits",
                "Live suckers",
                "Large rubber baits",
                "Topwater lures"
            ),
            fishingTechniques = listOf(
                "Figure-8 boat side",
                "Casting to structure",
                "Trolling",
                "Live bait rigging"
            )
        ),
        Fish(
            name = "Brown Trout",
            scientificName = "Salmo trutta",
            description = "Cunning and cautious, brown trout are known for their selective feeding and challenging nature.",
            habitat = "Cold streams and rivers with plenty of cover",
            diet = "Insects, small fish, crustaceans",
            averageSize = "2-8 pounds",
            temperatureRange = "50-65°F",
            bestBaits = listOf(
                "Nymphs",
                "Streamers",
                "Small spinners",
                "Live worms",
                "Minnows"
            ),
            fishingTechniques = listOf(
                "Fly fishing",
                "Nymphing",
                "Spinner fishing",
                "Night fishing"
            )
        ),
        Fish(
            name = "Lake Trout",
            scientificName = "Salvelinus namaycush",
            description = "Deep-water dwellers known for their size and strength. Popular among trolling enthusiasts.",
            habitat = "Deep, cold lakes",
            diet = "Fish, crustaceans",
            averageSize = "5-20 pounds",
            temperatureRange = "45-55°F",
            bestBaits = listOf(
                "Spoons",
                "Large minnows",
                "Tube jigs",
                "Cut bait",
                "Swimbaits"
            ),
            fishingTechniques = listOf(
                "Deep trolling",
                "Jigging",
                "Downrigger fishing",
                "Ice fishing"
            )
        ),
        Fish(
            name = "Crappie",
            scientificName = "Pomoxis nigromaculatus",
            description = "Popular panfish known for schooling behavior and excellent taste. Great for both sport and food.",
            habitat = "Around brush piles and vegetation in lakes",
            diet = "Small fish, insects, zooplankton",
            averageSize = "0.5-2 pounds",
            temperatureRange = "60-75°F",
            bestBaits = listOf(
                "Minnows",
                "Jigs",
                "Soft plastics",
                "Small spinners",
                "Tube baits"
            ),
            fishingTechniques = listOf(
                "Spider rigging",
                "Vertical jigging",
                "Dock shooting",
                "Brush pile fishing"
            )
        ),
        Fish(
            name = "Yellow Perch",
            scientificName = "Perca flavescens",
            description = "Schooling fish known for their tasty fillets and year-round availability.",
            habitat = "Clear lakes and rivers with sandy or weedy bottoms",
            diet = "Small fish, insects, crustaceans",
            averageSize = "4-12 ounces",
            temperatureRange = "60-70°F",
            bestBaits = listOf(
                "Minnows",
                "Worms",
                "Small jigs",
                "Spinners",
                "Ice fishing jigs"
            ),
            fishingTechniques = listOf(
                "Drift fishing",
                "Ice fishing",
                "Bottom bouncing",
                "Slip bobber fishing"
            )
        ),
        Fish(
            name = "Flathead Catfish",
            scientificName = "Pylodictis olivaris",
            description = "Predatory catfish known for their large size and preference for live bait.",
            habitat = "Deep holes in rivers and reservoirs",
            diet = "Live fish, crawfish",
            averageSize = "20-40 pounds",
            temperatureRange = "70-85°F",
            bestBaits = listOf(
                "Live bluegill",
                "Live bullheads",
                "Large minnows",
                "Crawfish",
                "Cut bait"
            ),
            fishingTechniques = listOf(
                "Live bait fishing",
                "Night fishing",
                "River hole fishing",
                "Structure fishing"
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

    fun getAllFish(): List<Fish> = fishList
} 
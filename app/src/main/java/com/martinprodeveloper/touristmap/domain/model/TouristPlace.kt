package com.martinprodeveloper.touristmap.domain.model

import com.google.gson.Gson

data class TouristPlace(
    var id: Int? = null,
    var name: String? = null,
    var description: String? = null,
    var imageUrl: String? = null,
    var location: Location? = null,
    var attractions: Attractions? = null
) {
    fun toJson(): String {
        return Gson().toJson(this)
    }
}

data class Location(
    var lat: String? = null,
    var lng: String? = null
)

data class Attractions(
    var attraction1: String? = null,
    var attraction2: String? = null,
    var attraction3: String? = null,
    var attraction4: String? = null
)
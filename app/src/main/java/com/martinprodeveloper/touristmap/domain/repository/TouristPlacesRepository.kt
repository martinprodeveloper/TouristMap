package com.martinprodeveloper.touristmap.domain.repository

import com.martinprodeveloper.touristmap.common.Result
import com.martinprodeveloper.touristmap.domain.model.TouristPlace

interface TouristPlacesRepository {
    suspend fun getAllTouristPlaces(): Result<List<TouristPlace>>
}
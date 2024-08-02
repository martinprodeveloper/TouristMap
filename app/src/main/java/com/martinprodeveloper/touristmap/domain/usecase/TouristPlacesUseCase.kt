package com.martinprodeveloper.touristmap.domain.usecase

import com.martinprodeveloper.touristmap.common.Result
import com.martinprodeveloper.touristmap.domain.model.TouristPlace
import com.martinprodeveloper.touristmap.domain.repository.TouristPlacesRepository
import javax.inject.Inject

class TouristPlacesUseCase @Inject constructor(
    private val touristPlacesRepository: TouristPlacesRepository
) {
    suspend fun getAllTouristPlaces(): Result<List<TouristPlace>> {
        return touristPlacesRepository.getAllTouristPlaces()
    }
}
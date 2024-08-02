package com.martinprodeveloper.touristmap.data.api

import com.martinprodeveloper.touristmap.domain.model.TouristPlace
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/touristplaces")
    suspend fun getAllTouristPlaces(
    ): Response<List<TouristPlace>>

}
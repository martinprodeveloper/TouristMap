package com.martinprodeveloper.touristmap.data.repository

import android.util.Log
import com.google.gson.Gson
import com.martinprodeveloper.touristmap.common.Result
import com.martinprodeveloper.touristmap.data.api.ApiService
import com.martinprodeveloper.touristmap.domain.model.TouristPlace
import com.martinprodeveloper.touristmap.domain.repository.TouristPlacesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TouristPlacesRepositoryImpl @Inject constructor(
    private val touristPlacesApiService: ApiService
) : TouristPlacesRepository {

    private val TAG = "TouristPlacesRepositoryImpl"

    override suspend fun getAllTouristPlaces(): Result<List<TouristPlace>> {
        return try {
            val response = touristPlacesApiService.getAllTouristPlaces()
            Log.d(TAG, "Response Code: ${response.code()}")
            Log.d(TAG, "Response Message: ${response.message()}")

            if (response.isSuccessful) {
                response.body()?.let {
                    val jsonTouristPlaces = Gson().toJson(it)
                    Log.d(TAG, "Response Body (JSON): $jsonTouristPlaces")
                    Result.Success(it)
                } ?: Result.Error(response.code(), "Empty body")
            } else {
                Log.e(TAG, "Error Body: ${response.errorBody()?.string()}") // Log del cuerpo del error
                Result.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            Log.e(TAG, "Exception: ${e.message}") // Log de la excepción
            Result.Error(null, e.message ?: "Unknown error")
        }
    }
}
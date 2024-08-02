package com.martinprodeveloper.touristmap.ui.home

import com.martinprodeveloper.touristmap.common.Result
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.martinprodeveloper.touristmap.domain.model.TouristPlace
import com.martinprodeveloper.touristmap.domain.usecase.TouristPlacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val touristPlacesUseCase: TouristPlacesUseCase
) : ViewModel() {

    val isLoading = MutableLiveData<Boolean>()
    val onError = MutableLiveData<Boolean>()

    private val _touristPlacesList = MutableLiveData<List<TouristPlace>?>()
    val touristPlacesList: LiveData<List<TouristPlace>?> = _touristPlacesList

    private var allTouristPlaces: List<TouristPlace>? = listOf()

    init {
        loadInitialData()
    }

    init {
        loadInitialData()
    }

    private fun loadInitialData() {
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = touristPlacesUseCase.getAllTouristPlaces()
            isLoading.value = false
            when (result) {
                is Result.Success -> {
                    allTouristPlaces = result.data
                    _touristPlacesList.value = allTouristPlaces
                    onError.value = false
                }
                is Result.Error -> {
                    _touristPlacesList.value = null
                    onError.value = true
                }
            }
        }
    }

    fun filterTouristPlaces(query: String) {
        val filteredList = allTouristPlaces?.filter { place ->
            place.name?.contains(query, ignoreCase = true) ?: false
        }
        _touristPlacesList.value = filteredList
    }
}

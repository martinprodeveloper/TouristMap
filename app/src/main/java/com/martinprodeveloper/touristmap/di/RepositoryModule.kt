package com.martinprodeveloper.touristmap.di

import com.martinprodeveloper.touristmap.data.repository.TouristPlacesRepositoryImpl
import com.martinprodeveloper.touristmap.domain.repository.TouristPlacesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTouristPlacesRepository(
        impl: TouristPlacesRepositoryImpl
    ): TouristPlacesRepository
}
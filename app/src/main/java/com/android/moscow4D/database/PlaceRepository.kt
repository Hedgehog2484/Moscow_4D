package com.android.moscow4D.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import androidx.lifecycle.*

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class PlaceRepository(private val plasesDao: PlaceListDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    val allPlaces: LiveData<List<PlaceEntity>> = plasesDao.getAllPlacesLive()

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    suspend fun getAllPlacesAsync(): ArrayList<PlaceEntity> {
        return plasesDao.getAllPlacesAsync() as ArrayList<PlaceEntity>
    }

    suspend fun insert(place: PlaceEntity) {
        plasesDao.addPlace(place)
    }
}
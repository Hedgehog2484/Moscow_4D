package com.android.moscow4D.database

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceListDao {

    @Query("SELECT * FROM ${PlaceEntity.TABLE_NAME}")
    fun getAllPlaces(): List<PlaceEntity>

    @Query("SELECT * FROM ${PlaceEntity.TABLE_NAME}")
    suspend fun getAllPlacesAsync(): List<PlaceEntity>

    @Query("SELECT * FROM ${PlaceEntity.TABLE_NAME}")
    fun getAllPlacesLive(): LiveData<List<PlaceEntity>>

    @Query("SELECT * FROM ${PlaceEntity.TABLE_NAME} ORDER BY placeName ASC")
    fun getAlphabetizedWords(): LiveData<List<PlaceEntity>>

    @Insert(entity = PlaceEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlace(placeEntity: PlaceEntity)

    @Delete(entity = PlaceEntity::class)
    suspend fun deletPlase(placeEntity: PlaceEntity)

    @Query("DELETE FROM ${PlaceEntity.TABLE_NAME}")
    suspend fun deleteAll()
}
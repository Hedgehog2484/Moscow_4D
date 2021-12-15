package com.android.moscow4D.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = PlaceEntity.TABLE_NAME)
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true) //@ColumnInfo(name = "placeId")
    val place_id : Int,

    @ColumnInfo(name = "placeType")
    val place_type : String,

    @ColumnInfo(name = "placeName")
    val place_name : String,

    @ColumnInfo(name = "placeDescription")
    val place_description : String,

    @ColumnInfo(name = "placeThumbnails")
    val place_thumbnails : String,

    @ColumnInfo(name = "placeImage")
    val place_image : String,

    @ColumnInfo(name = "placeSound")
    val place_sound : String
) {
    companion object {
        const val TABLE_NAME = "place_list_entities_table"
    }
}
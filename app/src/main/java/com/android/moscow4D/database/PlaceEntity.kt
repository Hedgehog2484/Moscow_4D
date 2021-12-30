package com.android.moscow4D.database

import android.os.Parcel
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcelable

@Entity(tableName = PlaceEntity.TABLE_NAME)
data class PlaceEntity(
    @PrimaryKey(autoGenerate = true) //@ColumnInfo(name = "placeId")
    val place_id: Int,

    @ColumnInfo(name = "placeType")
    val place_type: String?,

    @ColumnInfo(name = "placeName")
    val place_name: String?,

    @ColumnInfo(name = "placeDescription")
    val place_description: String?,

    @ColumnInfo(name = "placeThumbnails")
    val place_thumbnails: String?,

    @ColumnInfo(name = "placeImage")
    val place_image: String?,

    @ColumnInfo(name = "placeSound")
    val place_sound: String?)
    : Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeInt(place_id)
        p0.writeString(place_type)
        p0.writeString(place_name)
        p0.writeString(place_description)
        p0.writeString(place_thumbnails)
        p0.writeString(place_image)
        p0.writeString(place_sound)
    }

    companion object CREATOR : Parcelable.Creator<PlaceEntity> {
        const val TABLE_NAME = "place_list_entities_table"

        override fun createFromParcel(parcel: Parcel): PlaceEntity {
            return PlaceEntity(parcel)
        }

        override fun newArray(size: Int): Array<PlaceEntity?> {
            return arrayOfNulls(size)
        }
    }
}
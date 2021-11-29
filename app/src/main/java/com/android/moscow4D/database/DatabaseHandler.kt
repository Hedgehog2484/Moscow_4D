package com.android.moscow4D.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

//creating the database logic, extending the SQLiteOpenHelper base class
class DatabaseHandler(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "PlacesDatabase"

        private const val TABLE_CONTACTS = "PlacesTable"

        private const val KEY_ID = "_id"
        private const val KEY_TYPE = "type"
        private const val KEY_NAME = "name"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_THUMBNAIL = "thumbnail"
        private const val KEY_IMAGE = "image"
        private const val KEY_SOUND = "sound"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_TYPE + " TEXT," + KEY_DESCRIPTION + " TEXT,"
                + KEY_THUMBNAIL + " BLOB," + KEY_IMAGE + " BLOB,"
                + KEY_SOUND + " BLOB" + ")")

        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    /**
     * Function to create new place
     */
    fun addPlace(place: PlaceModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_TYPE, place.place_type)
        contentValues.put(KEY_NAME, place.place_name)
        contentValues.put(KEY_DESCRIPTION, place.place_description)
        contentValues.put(KEY_THUMBNAIL, place.place_thumbnails)
        contentValues.put(KEY_IMAGE, place.place_image)
        contentValues.put(KEY_SOUND, place.place_sound)

        // Inserting place details using insert query.
        val success = db.insert(TABLE_CONTACTS, null, contentValues)
        //2nd argument is String containing nullColumnHack

        db.close() // Closing database connection
        return success
    }
}
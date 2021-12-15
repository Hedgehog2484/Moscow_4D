package com.android.moscow4D.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.internal.synchronized


@Database(entities = arrayOf(PlaceEntity::class), version = 1, exportSchema = false)
abstract class PlaceRoomDatabase() : RoomDatabase(){

    abstract fun plaseListDao(): PlaceListDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: PlaceRoomDatabase? = null

        @InternalCoroutinesApi
        fun getDatabase(
            context: Context/*,
            scope: CoroutineScope*/
        ): PlaceRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            val tempInst = INSTANCE

                if(tempInst != null) {
                    return tempInst
                }
                synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        PlaceRoomDatabase::class.java,
                        "place_database"
                    ).build()
                    INSTANCE = instance
                    // return instance
                    return instance
                }
            }
        }
    }

/*
* private class PlaceDatabaseCallback(
    private val scope: CoroutineScope
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        INSTANCE?.let { database ->
            scope.launch {
                populateDatabase(database.plaseListDao())
            }
        }
    }

    suspend fun populateDatabase(placeDao: PlaceListDao) {
        // Delete all content here.
        placeDao.deleteAll()

        // Add sample words.
        var word = PlaceEntity(0, "Palace", "Tsaritsino Palace", "A palace ;)", "nope", "not now", "not here")
        placeDao.addPlace(word)
        word = PlaceEntity(0, "Park", "Tsaritsino DendroPark", "A park ;)", "nope", "not now", "not here")
        placeDao.addPlace(word)

        // TODO: Add your own Places!
    }
}*/
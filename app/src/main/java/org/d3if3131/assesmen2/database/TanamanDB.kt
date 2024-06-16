package org.d3if3131.assesmen2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3131.assesmen2.model.Plants

@Database(entities = [Plants::class], version = 1, exportSchema = false)
abstract class TanamanDb : RoomDatabase() {

    abstract val dao:TanamanDao

    companion object {

        @Volatile
        private var INSTANCE:TanamanDb? = null

        fun getInstance(context: Context): TanamanDb {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TanamanDb::class.java,
                        "tanaman.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
package com.share.greencloud.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.share.greencloud.domain.model.RentalOffice

@Database(entities = [RentalOffice::class], version = 1, exportSchema = false)
abstract class RentalOfficeDatabase : RoomDatabase() {

    companion object {
        @Volatile private var INSTANCE: RentalOfficeDatabase? = null

        fun getDatabase(context: Context): RentalOfficeDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            RentalOfficeDatabase::class.java, "rentaloffices"
                    ).build()
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun RentalOfficeDao(): RentalOfficeDao
}
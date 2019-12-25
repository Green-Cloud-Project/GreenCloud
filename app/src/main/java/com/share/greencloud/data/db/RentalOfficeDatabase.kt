/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.share.greencloud.domain.model.RentalOffice

@Database(entities = [RentalOffice::class], version = 2, exportSchema = false)
abstract class RentalOfficeDatabase : RoomDatabase() {

    companion object {
        @Volatile
        private var INSTANCE: RentalOfficeDatabase? = null

        fun getDatabase(context: Context): RentalOfficeDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = Room.databaseBuilder(
                            context,
                            RentalOfficeDatabase::class.java, "rentaloffices"
                    ).fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun RentalOfficeDao(): RentalOfficeDao
}

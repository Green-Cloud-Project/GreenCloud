package com.share.greencloud.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.share.greencloud.model.RentalOffice

@Dao
interface RentalOfficeDao {

    @Query("SELECT * from rentaloffices ORDER BY office_id ASC")
    fun getAllRentalOffice(): LiveData<List<RentalOffice>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(rentalOffice: RentalOffice)

    @Query("DELETE FROM rentaloffices")
    fun deleteAll()
}
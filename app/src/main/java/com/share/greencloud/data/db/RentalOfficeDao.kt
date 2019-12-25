package com.share.greencloud.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.share.greencloud.domain.model.RentalOffice

@Dao
interface RentalOfficeDao {

    //    ORDER BY office_id ASC
    @Query("SELECT * from rentaloffices ORDER BY distance ASC")
    fun getAllRentalOffice(): LiveData<List<RentalOffice>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(rentalOffice: RentalOffice)

    @Query("DELETE FROM rentaloffices")
    fun deleteAll()
}
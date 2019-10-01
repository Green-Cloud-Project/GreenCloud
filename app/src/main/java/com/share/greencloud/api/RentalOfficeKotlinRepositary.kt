package com.share.greencloud.api

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.share.greencloud.db.RentalOfficeDao
import com.share.greencloud.db.RentalOfficeDatabase
import com.share.greencloud.model.RentalOffice

class RentalOfficeKotlinRepositary {

    companion object {
        private class insertAsyncTask constructor(private val asyncTaskDao: RentalOfficeDao) :
                AsyncTask<RentalOffice, Void, Void>() {
            override fun doInBackground(vararg params: RentalOffice): Void? {
                asyncTaskDao.insert(params[0])
                return null
            }
        }
    }

    private val rentalOfficeDao: RentalOfficeDao
    private val allRentalOffice: LiveData<List<RentalOffice>>

    constructor (app: Application) {
        val db = RentalOfficeDatabase.getDatabase(app)
        rentalOfficeDao = db.RentalOfficeDao()
        allRentalOffice = rentalOfficeDao.getAllRentalOffice()
    }

    fun getAllRentalOffices(): LiveData<List<RentalOffice>> {
        return allRentalOffice
    }

    fun insert(rentalOffice: RentalOffice) {
        insertAsyncTask(rentalOfficeDao).execute(rentalOffice)
    }

}
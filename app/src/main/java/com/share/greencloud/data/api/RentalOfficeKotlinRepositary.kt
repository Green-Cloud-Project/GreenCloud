package com.share.greencloud.data.api

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.share.greencloud.data.db.RentalOfficeDao
import com.share.greencloud.data.db.RentalOfficeDatabase
import com.share.greencloud.domain.model.RentalOffice
import timber.log.Timber

class RentalOfficeKotlinRepositary {

    companion object {
        private class insertAsyncTask constructor(private val asyncTaskDao: RentalOfficeDao) :
                AsyncTask<RentalOffice, Void, Void>() {
            override fun doInBackground(vararg params: RentalOffice): Void? {
                asyncTaskDao.insert(params[0])
                return null
            }
        }

        private class queryAsyncTask constructor(private val asyncTaskDao: RentalOfficeDao) :
                AsyncTask<Void , Void, LiveData<List<RentalOffice>>>() {
            override fun doInBackground(vararg params: Void?): LiveData<List<RentalOffice>> {
                return asyncTaskDao.getAllRentalOffice()
            }

            override fun onPostExecute(result: LiveData<List<RentalOffice>>) {
                super.onPostExecute(result)
                Timber.d("result: %s", result.value?.size)
            }
        }

    }

    private val rentalOfficeDao: RentalOfficeDao
    private var  allRentalOffice: List<RentalOffice>  = arrayListOf()

    constructor (app: Application) {
        val db = RentalOfficeDatabase.getDatabase(app)
        rentalOfficeDao = db.RentalOfficeDao()
//        allRentalOffice = rentalOfficeDao.getAllRentalOffice()
    }

    fun getAllRentalOffices(): LiveData<List<RentalOffice>> {
        return queryAsyncTask(rentalOfficeDao).execute().get()
    }

    fun insert(rentalOffice: RentalOffice) {
        insertAsyncTask(rentalOfficeDao).execute(rentalOffice)
    }

}
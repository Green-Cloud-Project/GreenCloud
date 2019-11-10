package com.share.greencloud.presentation.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.nhaarman.mockitokotlin2.mock
import com.share.greencloud.data.api.RentalOfficeRepository
import org.junit.Rule
import org.junit.Test

class MapFragmentViewModelTest  {

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    val repository = mock<RentalOfficeRepository>()
    val application = mock<Application>()
    val viewmodel by lazy { MapFragmentViewModel(application) }

    @Test
    fun `loading Rental Office Data from Remote Server`(){

        Truth.assertThat(LiveDataTestUtil.getValue(viewmodel.rentalOfficeData)).hasSize(20)
    }
}
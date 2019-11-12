package com.share.greencloud.presentation.viewmodel

import android.app.Application
import android.location.Location
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.common.truth.Truth
import com.google.maps.android.SphericalUtil
import com.nhaarman.mockitokotlin2.mock
import com.share.greencloud.common.Constants.fixDistanceError
import com.share.greencloud.data.api.RentalOfficeRepository
import com.share.greencloud.domain.model.RentalOffice
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MapFragmentViewModelTest {

    @Rule
    @JvmField
    var instantExecutorRule = InstantTaskExecutorRule()

    val repository = mock<RentalOfficeRepository>()
    val application = mock<Application>()
    val viewmodel by lazy { MapFragmentViewModel(application) }

    val list: List<RentalOffice> = arrayListOf(
            RentalOffice(1, "글래스 타워", "강남역", 37.4990611, 127.027777, 10)
    )

    @Before
    fun setup() {
//        MapsInitializer.initialize(application)
    }

    @Test
    fun `loading Rental Office Data from Remote Server`() {

        Truth.assertThat(LiveDataTestUtil.getValue(viewmodel.rentalOfficeData)).hasSize(20)
    }

    @Test
    fun `make MarkOptions List from RentalOfficeData`() {
        val markerUnit = MarkerOptions().position(LatLng(list[0].lat, list[0].lon)).title("10")
        viewmodel.makeRentalOfficeMarkers(list)
        Truth.assertThat(viewmodel.markerOptionsList).hasSize(1)
        Truth.assertThat(viewmodel.markerOptionsList[0].position).isEqualTo(markerUnit.position)
        Truth.assertThat(viewmodel.markerOptionsList[0].title).isEqualTo(markerUnit.title)
    }

    // todo fail case 이므로 수정요망
    // location 변수에 값이 들어가지 않아서 test 결과가 fail됨
    @Test
    fun `add distance info into RentalOffice Data`() {
        val currentLocation = LatLng(37.4910180, 127.006799)

        val locationInfo = Location("")
        locationInfo.latitude = 37.4910180
        locationInfo.longitude = 127.006799

        println(locationInfo.latitude)

        val distance: Int = fixDistanceError(SphericalUtil.computeDistanceBetween(currentLocation,
                LatLng(list[0].lat, list[0].lon)))

        val rentalOfficeData = viewmodel.makeRentalOfficeListwithDistanceInfo(list, locationInfo)

        println(rentalOfficeData[0].distance)

        Truth.assertThat(rentalOfficeData[0].distance).isEqualTo(distance)
    }
}
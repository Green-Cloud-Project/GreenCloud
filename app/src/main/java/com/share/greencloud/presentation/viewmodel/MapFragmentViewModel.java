package com.share.greencloud.presentation.viewmodel;

import android.app.Application;
import android.location.Location;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;
import com.share.greencloud.data.api.RentalOfficeKotlinRepositary;
import com.share.greencloud.data.api.RentalOfficeRepository;
import com.share.greencloud.domain.model.RentalOffice;

import java.util.ArrayList;
import java.util.List;

import static com.share.greencloud.common.Constants.fixDistanceError;

public class MapFragmentViewModel extends AndroidViewModel {
    private RentalOfficeRepository repository;

    private RentalOfficeKotlinRepositary kotlinRepositary;
    private List<RentalOffice> allRentalOffices;

    private List<RentalOffice> rentalOfficeList;
    private List<MarkerOptions> markerOptionsList;

    public MapFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = new RentalOfficeRepository(application);
        kotlinRepositary = new RentalOfficeKotlinRepositary(application);
        rentalOfficeList = new ArrayList<>();
        markerOptionsList = new ArrayList<>();
    }

    public LiveData<List<RentalOffice>> getRentalOfficeData() {
        return repository.getMutableLiveData();
    }

    public LiveData<List<RentalOffice>> getAllRentalOfficesFromDB() {
        return kotlinRepositary.getAllRentalOffices();
    }

    public List<RentalOffice> getRentalOffice() {
        return rentalOfficeList;
    }

    public List<MarkerOptions> getMarkerOptionsList() {
        return markerOptionsList;
    }

    private void insert(RentalOffice rentalOffice) {
        kotlinRepositary.insert(rentalOffice);
    }

    public void makeRentalOfficeMarkers(List<RentalOffice> rentalOffices) {
        // Remote DB에서 가져온 정보를 저장
        rentalOfficeList = rentalOffices;

        LatLng position;
        MarkerOptions markerUnit;

        for (RentalOffice rentalOffice : rentalOfficeList) {
            position = new LatLng(rentalOffice.getLat(), rentalOffice.getLon());
            markerUnit = new MarkerOptions().position(position).title(String.valueOf(rentalOffice.getUmbrella_count()));
            markerOptionsList.add(markerUnit);
        }
    }

    public void addDistanceInfoToRentalOffice(Location userLocation) {
        LatLng currentLocation = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
        List<RentalOffice> newRentalOfficeData = makeRentalOfficeListwithDistanceInfo(rentalOfficeList, currentLocation);

        for (RentalOffice rentalOffice : newRentalOfficeData) {
            insert(rentalOffice);
        }
    }


    public List<RentalOffice> makeRentalOfficeListwithDistanceInfo(List<RentalOffice> originalData, LatLng userLocation) {
        int distance;

        // 테스트 코드 작성 커스텀 아이콘시 추가하는 케이스가 에러가 나서 임시로 빼둠
        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.um_green))
        for (RentalOffice rentalOffice : originalData) {
            distance = fixDistanceError(SphericalUtil.computeDistanceBetween(userLocation,
                    new LatLng(rentalOffice.getLat(), rentalOffice.getLon())));
            rentalOffice.setDistance(distance);
        }
        return originalData;
    }
}

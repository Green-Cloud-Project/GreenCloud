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

import timber.log.Timber;

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
//        allRentalOffices = kotlinRepositary.getAllRentalOffices();
        rentalOfficeList = new ArrayList<>();
        markerOptionsList = new ArrayList<>();
    }

    public LiveData<List<RentalOffice>> getRentalOfficeData() {
        return repository.getMutableLiveData();
    }

    public LiveData<List<RentalOffice>>getAllRentalOfficesFromDB() {
        return kotlinRepositary.getAllRentalOffices();
    }

    public List<RentalOffice> getRentalOffice() {
        return rentalOfficeList;
    }

    public List<MarkerOptions> getMarkerOptionsList() {
        return markerOptionsList;
    }

    public void insert(RentalOffice rentalOffice) {
        kotlinRepositary.insert(rentalOffice);
    }

//    public List<RentalOffice> search(String request) {
//        return kotlinRepositary.getAllRentalOffices();
//    }

    public void makeRentalOfficeMarkers(List<RentalOffice> rentalOffices) {
        // Remote DB에서 가져온 정보를 저장
        rentalOfficeList = rentalOffices;

        LatLng position;
        MarkerOptions markerUnit;
//        List<MarkerOptions> markerOptions = new ArrayList<>();

        for (RentalOffice rentalOffice : rentalOfficeList) {
            position = new LatLng(rentalOffice.getLat(), rentalOffice.getLon());
            markerUnit = new MarkerOptions().position(position).title(String.valueOf(rentalOffice.getUmbrella_count()));
            markerOptionsList.add(markerUnit);
        }
    }

    public void addDistanceInfoToRentalOffice(Location userLocation) {
        Timber.d("addDistanceInfoToRentalOffice");

        LatLng currentLocation = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
        int distance;

        // 테스트 코드 커스텀 아이콘
       //.icon(BitmapDescriptorFactory.fromResource(R.drawable.um_green))
        for (RentalOffice rentalOffice : rentalOfficeList) {
            distance = fixDistanceError(SphericalUtil.computeDistanceBetween(currentLocation,
                    new LatLng(rentalOffice.getLat(), rentalOffice.getLon())));
            rentalOffice.setDistance(distance);
            insert(rentalOffice);
        }

        Timber.d("rentalOfficeList %s", rentalOfficeList.get(0).getDistance());
    }

    public List<RentalOffice> makeRentalOfficeListwithDistanceInfo(List<RentalOffice> list, Location userLocation){
        LatLng currentLocation = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
        int distance;

        // 테스트 코드 커스텀 아이콘
        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.um_green))
        for (RentalOffice rentalOffice : list) {
            distance = fixDistanceError(SphericalUtil.computeDistanceBetween(currentLocation,
                    new LatLng(rentalOffice.getLat(), rentalOffice.getLon())));
            rentalOffice.setDistance(distance);
        }

        return  list;
    }
}

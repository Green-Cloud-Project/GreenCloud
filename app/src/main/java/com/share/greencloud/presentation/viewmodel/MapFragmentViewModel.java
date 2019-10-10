package com.share.greencloud.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.share.greencloud.R;
import com.share.greencloud.data.api.RentalOfficeKotlinRepositary;
import com.share.greencloud.data.api.RentalOfficeRepository;
import com.share.greencloud.domain.model.RentalOffice;

import java.util.ArrayList;
import java.util.List;

public class MapFragmentViewModel extends AndroidViewModel {
    private RentalOfficeRepository repository;

    private RentalOfficeKotlinRepositary kotlinRepositary;
    private LiveData<List<RentalOffice>> allRentalOffices;

    private List<RentalOffice> rentalOfficeList;
    private List<MarkerOptions> markerOptionsList;

    public MapFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = new RentalOfficeRepository(application);
        kotlinRepositary = new RentalOfficeKotlinRepositary(application);
        allRentalOffices = kotlinRepositary.getAllRentalOffices();
        rentalOfficeList = new ArrayList<>();
        markerOptionsList = new ArrayList<>();
    }

    public LiveData<List<RentalOffice>> getRentalOfficeData() {
        return repository.getMutableLiveData();
    }

    public LiveData<List<RentalOffice>> getAllRentalOfficesFromDB() {
        return allRentalOffices;
    }

    public List<RentalOffice> getRentalOffice() { return rentalOfficeList; }

    public List<MarkerOptions> getMarkerOptionsList() {
        return markerOptionsList;
    }

    public void insert(RentalOffice rentalOffice) {
        kotlinRepositary.insert(rentalOffice);
    }

    public void makeRentalOfficeMarkers(List<RentalOffice> rentalOffices) {

        // Remote DB에서 가져온 정보를 저장
        rentalOfficeList = rentalOffices;

        LatLng position;
        MarkerOptions markerUnit;
        List<MarkerOptions> markerOptions = new ArrayList<>();

        for (RentalOffice rentalOffice : rentalOfficeList) {
            position = new LatLng(rentalOffice.getLat(), rentalOffice.getLon());
            markerUnit = new MarkerOptions().position(position).title(String.valueOf(rentalOffice.getUmbrella_count()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.um_green));
            markerOptionsList.add(markerUnit);
        }
    }
}

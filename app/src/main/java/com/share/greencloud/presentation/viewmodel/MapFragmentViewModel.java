package com.share.greencloud.presentation.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
    private MutableLiveData<List<MarkerOptions>> liveDataMarkerOptions = new MutableLiveData<>();

    private RentalOfficeKotlinRepositary kotlinRepositary;
    private LiveData<List<RentalOffice>> allRentalOffices;

    public MapFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = new RentalOfficeRepository(application);
        kotlinRepositary = new RentalOfficeKotlinRepositary(application);
        allRentalOffices = kotlinRepositary.getAllRentalOffices();
    }

    public LiveData<List<RentalOffice>> getRentalOfficeData() {
        return repository.getMutableLiveData();
    }

    public MutableLiveData<List<MarkerOptions>> getLiveDataMarkerOptions() {
        return liveDataMarkerOptions;
    }

    public LiveData<List<RentalOffice>> getAllRentalOfficesFromDB() {
        return allRentalOffices;
    }

    public void insert(RentalOffice rentalOffice) {
        kotlinRepositary.insert(rentalOffice);
    }

    public void makeRentalOfficeMarkers(List<RentalOffice> rentalOffices) {
        LatLng position;
        MarkerOptions markerUnit;
        List<MarkerOptions> markerOptions = new ArrayList<>();

        for (RentalOffice rentalOffice : rentalOffices) {
            position = new LatLng(rentalOffice.getLat(), rentalOffice.getLon());
            markerUnit = new MarkerOptions().position(position).title(String.valueOf(rentalOffice.getUmbrella_count()))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.um_green));
            markerOptions.add(markerUnit);
        }
        liveDataMarkerOptions.setValue(markerOptions);
    }
}

package com.share.greencloud.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.share.greencloud.R;
import com.share.greencloud.common.RentalOfficeRepository;
import com.share.greencloud.model.RentalOffice;

import java.util.ArrayList;
import java.util.List;

public class MapFragmentViewModel extends AndroidViewModel {
    private RentalOfficeRepository repository;
    private MutableLiveData<List<MarkerOptions>> liveDataMarkerOptions = new MutableLiveData<>();

    public MapFragmentViewModel(@NonNull Application application) {
        super(application);
        repository = new RentalOfficeRepository(application);
    }

    public LiveData<List<RentalOffice>> getRentalOfficeData() {
        return repository.getMutableLiveData();
    }

    public MutableLiveData<List<MarkerOptions>> getLiveDataMarkerOptions() {
        return liveDataMarkerOptions;
    }

    public void makeRentalOfficeMarkers(List<RentalOffice> rentalOffices) {
        LatLng position;
        MarkerOptions markerUnit;
        List<MarkerOptions> markerOptions = new ArrayList<>();

        for (RentalOffice rentalOffice : rentalOffices) {
            position = new LatLng(rentalOffice.getLatitude(), rentalOffice.getLongitude());
            markerUnit = new MarkerOptions().position(position).title(rentalOffice.getOffice_name())
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.umbrella_smallest));
            markerOptions.add(markerUnit);
        }
        liveDataMarkerOptions.setValue(markerOptions);
    }
}

package com.share.greencloud.presentation.presenter;

import android.location.Address;
import android.location.Location;

import androidx.lifecycle.Lifecycle;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.patloew.rxlocation.RxLocation;
import com.share.greencloud.domain.interator.LocationInfoMVP;
import com.share.greencloud.domain.model.UserLocation;
import com.share.greencloud.utils.AutoDisposable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

import static com.share.greencloud.common.Constants.REQEUST_TIME_INTERVAL;
import static com.share.greencloud.common.Constants.setDefaultLocation;

public class LocationPresenter implements LocationInfoMVP.Presenter {

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final RxLocation rxLocation;
    private final LocationRequest locationRequest;

    private LocationInfoMVP.View view;

    private UserLocation userLocation;

    private AutoDisposable autoDisposable = new AutoDisposable();

    public LocationPresenter(RxLocation rxLocation, Lifecycle lifecycle) {

        this.rxLocation = rxLocation;
        this.locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_LOW_POWER)
                .setInterval(REQEUST_TIME_INTERVAL);
        userLocation = new UserLocation(setDefaultLocation());
        autoDisposable.bindTo(lifecycle);
        autoDisposable.add(disposable);
    }

    public void attachView(LocationInfoMVP.View view) {
        Timber.d("attachView is Called");
        this.view = view;
        startLocationRefresh();
    }

    public void detachView() {
        Timber.d("detachView is Called");
        this.view = null;
        disposable.clear();
    }

    @Override
    public void updateUserLocation(Location updatedLocation, OnMapReadyCallback callback) {
        userLocation.updateCurrentLocation(updatedLocation);
        view.onMapUpdate(callback);
    }

    @Override
    public Location getUserLocation() {
        return userLocation.getCurrentLocation();
    }

    private void startLocationRefresh() {
        Timber.d("startLocationRefresh is Called");
        disposable.add(
                rxLocation.settings().checkAndHandleResolution(locationRequest)
                        .flatMapObservable(this::getAddressObservable)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(view::onAddressUpdate, throwable -> Timber.e("Error fetching location/address updates"))
        );
    }

    private Observable<Address> getAddressObservable(boolean success) {
        Timber.d("getAddressObservable is Called");
        if (success) {
            return rxLocation.location().updates(locationRequest)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext(view::onLocationUpdate)
                    .flatMap(this::getAddressFromLocation);

        } else {
            view.onLocationSettingsUnsuccessful();

            return rxLocation.location().lastLocation()
                    .doOnSuccess(view::onLocationUpdate)
                    .flatMapObservable(this::getAddressFromLocation);
        }
    }

    private Observable<Address> getAddressFromLocation(android.location.Location location) {
        Timber.d("getAddressFromLocation is Called");
        return rxLocation.geocoding().fromLocation(location).toObservable()
                .subscribeOn(Schedulers.io());
    }

}

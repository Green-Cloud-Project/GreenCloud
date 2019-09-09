package com.share.greencloud.common.location;

import android.location.Address;

import com.google.android.gms.location.LocationRequest;
import com.patloew.rxlocation.RxLocation;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LocationPresenter implements LocationInfo.Presenter {

    private final CompositeDisposable disposable = new CompositeDisposable();

    private final RxLocation rxLocation;
    private final LocationRequest locationRequest;

    private LocationInfo.View view;

    public LocationPresenter(RxLocation rxLocation) {

        this.rxLocation = rxLocation;
        this.locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_LOW_POWER)
                .setInterval(15000);
    }

    public void attachView(LocationInfo.View view) {
        Timber.d("attachView is Called");
        this.view = view;
        startLocationRefresh();
    }

    public void detachView() {
        Timber.d("detachView is Called");
        this.view = null;
        disposable.clear();
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

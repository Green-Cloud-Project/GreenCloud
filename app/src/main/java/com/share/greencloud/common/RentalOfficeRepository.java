package com.share.greencloud.common;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.share.greencloud.api.ApiManager;
import com.share.greencloud.model.RentalOffice;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RentalOfficeRepository {
    private List<RentalOffice> rentalOffices = new ArrayList<>();
    private MutableLiveData<List<RentalOffice>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public RentalOfficeRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<RentalOffice>> getMutableLiveData() {

        Call<List<RentalOffice>> call = ApiManager.getInstance().getService().getRentalOffices();
        call.enqueue(new Callback<List<RentalOffice>>() {
            @Override
            public void onResponse(Call<List<RentalOffice>> call, Response<List<RentalOffice>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Timber.d("대여소 데이터 로딩완료: %s", response.body().get(0).getOffice_location());
                        rentalOffices = response.body();
                        mutableLiveData.setValue(rentalOffices);
                    } else {
                        Timber.d("대여소 데이터 로딩실패: %s", response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<List<RentalOffice>> call, Throwable t) {
                Timber.d("대여소 데이터 로딩실패: %s", t.getMessage());
            }
        });

        return mutableLiveData;
    }
}

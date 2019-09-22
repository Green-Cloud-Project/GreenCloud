package com.share.greencloud.fragment;

import android.content.Context;
import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.patloew.rxlocation.RxLocation;
import com.share.greencloud.R;
import com.share.greencloud.common.location.LocationInfo;
import com.share.greencloud.common.location.LocationPresenter;
import com.share.greencloud.model.CurrentWeatherModel;
import com.share.greencloud.model.HourlyWeatherForecastModel;
import com.share.greencloud.model.WeatherCallbackListener;
import com.share.greencloud.model.WeatherCondition;
import com.share.greencloud.utils.BaseTime;
import com.share.greencloud.utils.Geocoding;
import com.share.greencloud.utils.LoadingIndicator;
import com.share.greencloud.utils.MappingCategory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

import static com.share.greencloud.common.Constants.REQEUST_TIME_INTERVAL;

public class WeatherFragment extends Fragment implements View.OnClickListener, WeatherCallbackListener, LocationInfo.View, OnMapReadyCallback {

//    Location mLastLocation;

    Context context;
    View view;

    TextView[] tv_hours;
    TextView[] tv_chances;
    ImageView[] iv_weathers;
    TextView[] tv_sky_state;


    TextView tv_name, tv_mileage;
    TextView tv_buy, tv_return;

    TextView tv_hour1, tv_hour2, tv_hour3, tv_hour4, tv_hour5;
    TextView tv_chance1, tv_chance2, tv_chance3, tv_chance4, tv_chance5;
    ImageView iv_weatther1, iv_weatther2, iv_weatther3, iv_weatther4, iv_weatther5;
    TextView tv_sky_state1, tv_sky_state2, tv_sky_state3, tv_sky_state4, tv_sky_state5;


    //지금 날씨
    ImageView iv_current_sky;
    TextView tv_current_area, tv_current_degree, tv_current_sky;


    Map<String, Integer> mHashMap;

    private RxLocation rxLocation;
    private LocationPresenter presenter;

    public WeatherFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_weather, container, false);
        view.setVisibility(View.GONE);
        initalizeView();
        initalizeLocation();
        return view;
    }

    private void initalizeView() {


        //날씨 예보
        tv_hours = new TextView[5];
        tv_chances = new TextView[5];
        tv_sky_state = new TextView[5];
        iv_weathers = new ImageView[5];

        tv_hour1 = (TextView) view.findViewById(R.id.tv_hour1);
        tv_hour2 = (TextView) view.findViewById(R.id.tv_hour2);
        tv_hour3 = (TextView) view.findViewById(R.id.tv_hour3);
        tv_hour4 = (TextView) view.findViewById(R.id.tv_hour4);
        tv_hour5 = (TextView) view.findViewById(R.id.tv_hour5);

        iv_weatther1 = (ImageView) view.findViewById(R.id.iv_weather1);
        iv_weatther2 = (ImageView) view.findViewById(R.id.iv_weather2);
        iv_weatther3 = (ImageView) view.findViewById(R.id.iv_weather3);
        iv_weatther4 = (ImageView) view.findViewById(R.id.iv_weather4);
        iv_weatther5 = (ImageView) view.findViewById(R.id.iv_weather5);

        tv_chance1 = (TextView) view.findViewById(R.id.tv_chance1);
        tv_chance2 = (TextView) view.findViewById(R.id.tv_chance2);
        tv_chance3 = (TextView) view.findViewById(R.id.tv_chance3);
        tv_chance4 = (TextView) view.findViewById(R.id.tv_chance4);
        tv_chance5 = (TextView) view.findViewById(R.id.tv_chance5);

        //tv_name    = (TextView)view.findViewById(R.id.tv_name);
        // tv_mileage = (TextView)view.findViewById(R.id.tv_mileage);

        tv_sky_state1 = (TextView) view.findViewById(R.id.tv_sky_state1);
        tv_sky_state2 = (TextView) view.findViewById(R.id.tv_sky_state2);
        tv_sky_state3 = (TextView) view.findViewById(R.id.tv_sky_state3);
        tv_sky_state4 = (TextView) view.findViewById(R.id.tv_sky_state4);
        tv_sky_state5 = (TextView) view.findViewById(R.id.tv_sky_state5);

        tv_buy = (TextView) view.findViewById(R.id.tv_buy);
        tv_return = (TextView) view.findViewById(R.id.tv_return);

        tv_buy.setOnClickListener(this);
        tv_return.setOnClickListener(this);

        tv_hours = new TextView[]{tv_hour1, tv_hour2, tv_hour3, tv_hour4, tv_hour5};
        tv_sky_state = new TextView[]{tv_sky_state1, tv_sky_state2, tv_sky_state3, tv_sky_state4, tv_sky_state5};
        iv_weathers = new ImageView[]{iv_weatther1, iv_weatther2, iv_weatther3, iv_weatther4, iv_weatther5};
        tv_chances = new TextView[]{tv_chance1, tv_chance2, tv_chance3, tv_chance4, tv_chance5};


        //지금 날씨
        iv_current_sky = (ImageView) view.findViewById(R.id.iv_current_sky);
        tv_current_area = (TextView) view.findViewById(R.id.tv_current_area);
        tv_current_degree = (TextView) view.findViewById(R.id.tv_current_degree);
        tv_current_sky = (TextView) view.findViewById(R.id.tv_current_sky);

    }

    private void initalizeLocation() {

        LoadingIndicator.getInstance().showProgress(getActivity());

        rxLocation = new RxLocation(getContext());
        rxLocation.setDefaultTimeout(REQEUST_TIME_INTERVAL, TimeUnit.SECONDS);
        presenter = new LocationPresenter(rxLocation);
    }

    public void initGettingWeatherData() {

        new WeatherCondition().getHourlyForecastData(presenter.getUserLocation(), WeatherFragment.this);
        new WeatherCondition().getCurrentWeatherData(presenter.getUserLocation(), WeatherFragment.this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        initGettingWeatherData();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.detachView();
    }

    @Override
    public void getWeatherData(Object weatherModel, Boolean success, String errorMsg) {

        if (success) {

            if (weatherModel instanceof HourlyWeatherForecastModel.Body) {

                int index = 0;
                String prevForecastTime = "9999";
                boolean isFirstLoopDone = false;
                boolean isRaining = false;
                List<HourlyWeatherForecastModel.Item> weathers = ((HourlyWeatherForecastModel.Body) weatherModel).getItem().getItems();

                for (HourlyWeatherForecastModel.Item item : weathers) {

                    if (index == 5) {
                        break;
                    }

                    String category = item.getCategory();
                    String forecastTime = item.getFcstTime();
                    String forecastValue = item.getFcstValue();

                    if ((category.equals(String.valueOf(MappingCategory.CATEGORY.PTY))) && (!forecastValue.equals("0"))) {

                        isRaining = true;
                        mHashMap = MappingCategory.mapPTY(Integer.parseInt(forecastValue));
                        for (Map.Entry<String, Integer> entry : mHashMap.entrySet()) {

                            String ampm = new BaseTime().addAMPM(forecastTime);
                            tv_hours[index].setText(forecastTime.substring(0, 2) + ampm);
                            iv_weathers[index].setImageResource((int) entry.getValue());
                            tv_sky_state[index].setText(entry.getKey());
                        }
                    } else if (category.equals(String.valueOf(MappingCategory.CATEGORY.SKY)) && !isRaining) {
                        mHashMap = MappingCategory.mapSKY(Integer.parseInt(forecastValue));
                        for (Map.Entry<String, Integer> entry : mHashMap.entrySet()) {

                            String ampm = new BaseTime().addAMPM(forecastTime);
                            tv_hours[index].setText(forecastTime.substring(0, 2) + ampm);
                            iv_weathers[index].setImageResource((int) entry.getValue());
                            tv_sky_state[index].setText(entry.getKey());
                        }
                    } else if (category.equals(String.valueOf(MappingCategory.CATEGORY.POP))) {
                        tv_chances[index].setText(forecastValue + "%");
                    }

                    if ((index == 0) && (!prevForecastTime.equals(forecastTime)) && !isFirstLoopDone) {
                        prevForecastTime = forecastTime;
                        isFirstLoopDone = true;
                        index++;
                    } else {
                        if (!prevForecastTime.equals(forecastTime)) {
                            index++;
                            isRaining = false;
                            prevForecastTime = forecastTime;
                        }
                    }
                }

            } else if (weatherModel instanceof CurrentWeatherModel.Weather) {

                String area = Geocoding.getAddress(getActivity(),presenter.getUserLocation().getLatitude(),presenter.getUserLocation().getLongitude());
                //sryang 널체크
                if (((CurrentWeatherModel.Weather) weatherModel).getHourly() == null
                        || ((CurrentWeatherModel.Weather) weatherModel).getHourly().size() <= 0)
                    return;

                CurrentWeatherModel.Hourly weather = ((CurrentWeatherModel.Weather) weatherModel).getHourly().get(0);
                //String area = weather.getGrid().getCity() + " " + weather.getGrid().getCounty() + " " + weather.getGrid().getVillage();
                tv_current_area.setText(area);

                String degree = weather.getTemperature().getTc();
                degree = degree.contains(".") ? degree.replaceAll("0*$", "").replaceAll("\\.$", "") : degree;
                tv_current_degree.setText(degree + " \u2103");
                tv_current_sky.setText(weather.getSky().getName());
                iv_current_sky.setImageResource(MappingCategory.mapSKYCode(weather.getSky().getCode()));

                //Calendar now = Calendar.getInstance();
                //System.out.println(now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE));

                view.postDelayed(new Runnable() {
                    public void run() {
                        view.setVisibility(View.VISIBLE);
                        LoadingIndicator.getInstance().dismiss();
                    }
                }, 0005);


            }

        } else { //logical error

        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_buy:
                buyUmbrella();
            case R.id.tv_return:
                returnUmbrella();
                break;
        }
    }


    public void buyUmbrella() {

    }

    public void returnUmbrella() {

    }

    @Override
    public void onLocationUpdate(Location location) {
        Timber.d("onLocationUpdate is called");
        if (location != null) {
            Timber.d("updated location is " + location.getLatitude() + " , " + location.getLongitude());
            presenter.updateUserLocation(location, this);
        }

        initGettingWeatherData();
    }

    @Override
    public void onLocationSettingsUnsuccessful() {

    }

    @Override
    public void onAddressUpdate(Address address) {

    }

    @Override
    public void onMapUpdate(OnMapReadyCallback callback) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}

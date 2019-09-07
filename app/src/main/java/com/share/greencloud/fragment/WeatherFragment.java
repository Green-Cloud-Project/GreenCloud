package com.share.greencloud.fragment;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.share.greencloud.R;
import com.share.greencloud.common.ApiFactory;
import com.share.greencloud.common.ApiServices;
import com.share.greencloud.common.Constants;
import com.share.greencloud.common.LastLocationInfo;
import com.share.greencloud.model.CurrentWeatherModel;
import com.share.greencloud.model.HourlyWeatherForecastModel;
import com.share.greencloud.utils.BaseTime;
import com.share.greencloud.utils.GridxyConverter;
import com.share.greencloud.utils.MappingCategory;

import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherFragment extends Fragment  implements View.OnClickListener {




    String   mRainStatus;
    Location mLastLocation;

    Context     context;
    View        view;

    TextView[]  tv_hours;
    TextView[]  tv_chances;
    ImageView[] iv_weathers;
    TextView[]  tv_sky_state;


    TextView    tv_name, tv_mileage;
    TextView    tv_buy,  tv_return;

    TextView    tv_hour1,tv_hour2,tv_hour3,tv_hour4,tv_hour5;
    TextView    tv_chance1,tv_chance2,tv_chance3,tv_chance4,tv_chance5;
    ImageView   iv_weatther1,iv_weatther2,iv_weatther3,iv_weatther4,iv_weatther5;
    TextView    tv_sky_state1,tv_sky_state2,tv_sky_state3,tv_sky_state4,tv_sky_state5;


    //지금 날씨
    ImageView   iv_current_sky;
    TextView    tv_current_area,tv_current_degree,tv_current_sky;


    Map<String, Integer> mHashMap;


    public WeatherFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context  = container.getContext();
        view =  inflater.inflate(R.layout.fragment_weather, container, false);
        initalizeView();
        return view;
    }

    private void initalizeView() {


        //날씨 예보
        tv_hours     = new TextView[5];
        tv_chances   = new TextView[5];
        tv_sky_state = new TextView[5];
        iv_weathers  = new ImageView[5];

        tv_hour1 = (TextView)view.findViewById(R.id.tv_hour1);
        tv_hour2 = (TextView)view.findViewById(R.id.tv_hour2);
        tv_hour3 = (TextView)view.findViewById(R.id.tv_hour3);
        tv_hour4 = (TextView)view.findViewById(R.id.tv_hour4);
        tv_hour5 = (TextView)view.findViewById(R.id.tv_hour5);

        iv_weatther1 = (ImageView)view.findViewById(R.id.iv_weather1);
        iv_weatther2 = (ImageView)view.findViewById(R.id.iv_weather2);
        iv_weatther3 = (ImageView)view.findViewById(R.id.iv_weather3);
        iv_weatther4 = (ImageView)view.findViewById(R.id.iv_weather4);
        iv_weatther5 = (ImageView)view.findViewById(R.id.iv_weather5);

        tv_chance1 = (TextView)view.findViewById(R.id.tv_chance1);
        tv_chance2 = (TextView)view.findViewById(R.id.tv_chance2);
        tv_chance3 = (TextView)view.findViewById(R.id.tv_chance3);
        tv_chance4 = (TextView)view.findViewById(R.id.tv_chance4);
        tv_chance5 = (TextView)view.findViewById(R.id.tv_chance5);

        tv_name    = (TextView)view.findViewById(R.id.tv_name);
        // tv_mileage = (TextView)view.findViewById(R.id.tv_mileage);

        tv_sky_state1 = (TextView)view.findViewById(R.id.tv_sky_state1);
        tv_sky_state2 = (TextView)view.findViewById(R.id.tv_sky_state2);
        tv_sky_state3 = (TextView)view.findViewById(R.id.tv_sky_state3);
        tv_sky_state4 = (TextView)view.findViewById(R.id.tv_sky_state4);
        tv_sky_state5 = (TextView)view.findViewById(R.id.tv_sky_state5);

        tv_buy     = (TextView)view.findViewById(R.id.tv_buy);
        tv_return  = (TextView)view.findViewById(R.id.tv_return);

        tv_buy.setOnClickListener(this);
        tv_return.setOnClickListener(this);

        tv_hours     = new TextView[]{tv_hour1,tv_hour2,tv_hour3,tv_hour4,tv_hour5};
        tv_sky_state = new TextView[] {tv_sky_state1,tv_sky_state2,tv_sky_state3,tv_sky_state4,tv_sky_state5};
        iv_weathers  = new ImageView[]{iv_weatther1,iv_weatther2,iv_weatther3,iv_weatther4,iv_weatther5};
        tv_chances   = new TextView[] {tv_chance1,tv_chance2,tv_chance3,tv_chance4,tv_chance5};


        //지금 날씨
        iv_current_sky    = (ImageView)view.findViewById(R.id.iv_current_sky);
        tv_current_area   = (TextView)view.findViewById(R.id.tv_current_area);
        tv_current_degree = (TextView)view.findViewById(R.id.tv_current_degree);
        tv_current_sky    = (TextView)view.findViewById(R.id.tv_current_sky);


    }



    public void getWeatherData()  {

        String[]  gridXy = GridxyConverter.calculateGridxy(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        String lat  = gridXy[0];
        String lon  = gridXy[1];

        ApiServices apiServices = ApiFactory.createRetrofitApi(ApiServices.class,Constants.BASE_URL,GsonConverterFactory.create());
        BaseTime bt = new BaseTime();

        Call<HourlyWeatherForecastModel> call = apiServices.getHourlyWeatherData(Constants.SERVICE_KEY,bt.getToday(),bt.getBaseTime(),lat,lon,50,
                1,"json");

        Call<CurrentWeatherModel> call2 = apiServices.getCurrentWeather(Constants.SERVICE_KEY,bt.getToday(),bt.minus30Minutes(),lat,lon,10,
                1,"json");

        Call<HourlyWeatherForecastModel> call3 = apiServices.getShortForecastWeather(Constants.SERVICE_KEY,bt.getToday(),bt.getBaseTime(),lat,lon,10,
                1,"json");


        if (Constants.MODE == Constants.MODE.DEBUG)  {
            HttpUrl httpUrl = call.request().url();
        }

        call.enqueue(getHourlyForecastCallback());
        call2.enqueue(getCurrentWeatherCallback());
//       call3.enqueue(getShortForecastWeather());

    }

    @Override
    public void onResume() {
        super.onResume();

        mLastLocation = new LastLocationInfo(getActivity()).getLastLocation();
        getWeatherData();

    }


    public Callback<HourlyWeatherForecastModel> getHourlyForecastCallback() {


        return new Callback<HourlyWeatherForecastModel>() {

            @Override
            public void onResponse(@NonNull Call<HourlyWeatherForecastModel> call, @NonNull Response<HourlyWeatherForecastModel> response) {

                if (response.isSuccessful()) {

                    int index = 0;
                    String prevForecastTime = "9999";
                    boolean isFirstLoopDone = false;
                    boolean isRaining = false;

                    String resultCode = response.body().getResonse().getHeader().getResultCode();

                    if (resultCode.equals("0000")) {

                        List<HourlyWeatherForecastModel.Item> weathers = response.body().getResonse().getBody().getItem().getItems();

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
                                    tv_hours[index].setText(forecastTime.substring(0, 2));
                                    iv_weathers[index].setImageResource((int) entry.getValue());
                                    tv_sky_state[index].setText(entry.getKey());
                                }
                            }

                            else if (category.equals(String.valueOf(MappingCategory.CATEGORY.SKY)) && !isRaining) {
                                mHashMap = MappingCategory.mapSKY(Integer.parseInt(forecastValue));
                                for (Map.Entry<String, Integer> entry : mHashMap.entrySet()) {

                                    tv_hours[index].setText(forecastTime.substring(0, 2));
                                    iv_weathers[index].setImageResource((int) entry.getValue());
                                    tv_sky_state[index].setText(entry.getKey());
                                }
                            } else if (category.equals(String.valueOf(MappingCategory.CATEGORY.POP))) {
                                tv_chances[index].setText(forecastValue+"%");
                            }

                            if ((index == 0) && (!prevForecastTime.equals(forecastTime)) && !isFirstLoopDone) {
                                prevForecastTime = forecastTime;
                                isFirstLoopDone = true;
                            } else {
                                if (!prevForecastTime.equals(forecastTime)) {
                                    index++;
                                    isRaining = false;
                                    prevForecastTime = forecastTime;
                                }
                            }
                        }

                    } else { //handling logical error
                        //Dialog..
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<HourlyWeatherForecastModel> call, @NonNull Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                call.cancel();

            }
        };
    }


    public Callback<CurrentWeatherModel> getCurrentWeatherCallback() {

        return new Callback<CurrentWeatherModel>() {

            @Override
            public void onResponse(@NonNull Call<CurrentWeatherModel> call, @NonNull Response<CurrentWeatherModel> response) {

                if (response.isSuccessful()) {

                    String resultCode = response.body().getResonse().getHeader().getResultCode();

                    if (resultCode.equals("0000")) {

                        List<CurrentWeatherModel.Item> weathers = response.body().getResonse().getBody().getItem().getItems();

                        for (CurrentWeatherModel.Item item : weathers) {

                            String category     =     item.getCategory();
                            String obsrValueValue  =    item.getObsrValue();

                            if (category.equals(String.valueOf(MappingCategory.CATEGORY.T1H))) {
                                tv_current_degree.setText(obsrValueValue+"℃");
                            }else if (category.equals(String.valueOf(MappingCategory.CATEGORY.PTY))) {
                                mRainStatus =  obsrValueValue;

                                if (!mRainStatus.equals("0")) {
                                    mHashMap = MappingCategory.mapPTY(Integer.parseInt(obsrValueValue));
                                    for (Map.Entry<String, Integer> entry : mHashMap.entrySet()) {
                                        iv_current_sky.setImageResource((int) entry.getValue());
                                        tv_current_sky.setText(entry.getKey());
                                    }
                                }

                            }
                        }

                    } else { //handling logical error
                        //Dialog..
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<CurrentWeatherModel> call, @NonNull Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                call.cancel();

            }
        };
    }


    public Callback<HourlyWeatherForecastModel> getShortForecastWeather() {

        return new Callback<HourlyWeatherForecastModel>() {

            @Override
            public void onResponse(@NonNull Call<HourlyWeatherForecastModel> call, @NonNull Response<HourlyWeatherForecastModel> response) {

                if (response.isSuccessful()) {

                    String resultCode = response.body().getResonse().getHeader().getResultCode();

                    if (resultCode.equals("0000")) {

                        List<HourlyWeatherForecastModel.Item> weathers = response.body().getResonse().getBody().getItem().getItems();

                        for (HourlyWeatherForecastModel.Item item : weathers) {

                            String category       =    item.getCategory();
                            String forecastValue  =    item.getFcstValue();

                            if (mRainStatus.equals("0")) {

                                if (category.equals(String.valueOf(MappingCategory.CATEGORY.SKY))) {
                                    mHashMap = MappingCategory.mapSKY(Integer.parseInt(forecastValue));
                                    for (Map.Entry<String, Integer> entry : mHashMap.entrySet()) {
                                        iv_current_sky.setImageResource((int) entry.getValue());
                                        tv_current_sky.setText(entry.getKey());
                                    }
                                }
                            }
                        }

                    } else { //handling logical error
                        //Dialog..
                        //{"response":{"header":{"resultCode":"99","resultMsg":"파라미터가 잘못되엇습니다."}}}
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<HourlyWeatherForecastModel> call, @NonNull Throwable t) {
                Toast.makeText(context, t.toString(), Toast.LENGTH_SHORT).show();
                call.cancel();

            }
        };
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

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


}

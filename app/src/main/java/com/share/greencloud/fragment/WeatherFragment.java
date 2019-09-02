package com.share.greencloud.fragment;

import android.content.Context;
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
import com.share.greencloud.common.ApiServices;
import com.share.greencloud.common.Constants;
import com.share.greencloud.model.WeatherForecastModel;
import com.share.greencloud.utils.BaseTime;
import com.share.greencloud.utils.MappingCategory;

import java.util.List;
import java.util.Map;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherFragment extends Fragment  implements View.OnClickListener {


    double latitude;
    double longitude;
    LocationManager locationManager;

    Context     context;
    View        view;

    TextView[]  tv_hours;
    TextView[]  tv_chances;
    ImageView[] iv_weathers;

    TextView    tv_name, tv_mileage;
    TextView    tv_buy,  tv_return;

    TextView    tv_hour1,tv_hour2,tv_hour3,tv_hour4,tv_hour5;
    TextView    tv_chance1,tv_chance2,tv_chance3,tv_chance4,tv_chance5;
    ImageView   iv_weatther1,iv_weatther2,iv_weatther3,iv_weatther4,iv_weatther5;

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

        tv_hours    = new TextView[5];
        iv_weathers = new ImageView[5];
        tv_chances  = new TextView[5];

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
        tv_mileage = (TextView)view.findViewById(R.id.tv_mileage);

        tv_buy     = (TextView)view.findViewById(R.id.tv_buy);
        tv_return  = (TextView)view.findViewById(R.id.tv_return);

        tv_buy.setOnClickListener(this);
        tv_return.setOnClickListener(this);

        tv_hours    = new TextView[]{tv_hour1,tv_hour2,tv_hour3,tv_hour4,tv_hour5};
        iv_weathers = new ImageView[]{iv_weatther1,iv_weatther2,iv_weatther3,iv_weatther4,iv_weatther5};
        tv_chances  =  new TextView[] {tv_chance1,tv_chance2,tv_chance3,tv_chance4,tv_chance5};

    }


    public void getCurrentWeatherData()  {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        if (Constants.MODE == Constants.MODE.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logging);
        }

        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .client(httpClient.build())
                .build();

        ApiServices apiService = retrofit.create(ApiServices.class);


        BaseTime bt = new BaseTime();
        Call<WeatherForecastModel> call = apiService.getHourlyWeatherData(Constants.SERVICE_KEY,bt.getToday(),bt.getBaseTime(),"60","127",72,
                1,"json");

        if (Constants.MODE == Constants.MODE.DEBUG)  {
            HttpUrl httpUrl = call.request().url();
        }
        call.enqueue(new Callback<WeatherForecastModel>() {

            @Override
            public void onResponse(@NonNull Call<WeatherForecastModel> call, @NonNull Response<WeatherForecastModel> response) {
                if (response.isSuccessful()) {


                    int index = 0;
                    String prevForecastTime  = "9999";
                    boolean isFirstLoopDone  = false;

                    String resultCode  = response.body().getResonse().getHeader().getResultCode();

                    if (resultCode.equals("0000")) {

                        List<WeatherForecastModel.Item> weathers  = response.body().getResonse().getBody().getItem().getItems();

                        for (WeatherForecastModel.Item item : weathers) {

                            if (index == 5) {break;}

                            String category = item.getCategory();
                            String forecastTime = item.getFcstTime();
                            String forecastValue = item.getFcstValue();

                            if ((category.equals(String.valueOf(MappingCategory.CATEGORY.PTY))) && (!forecastValue.equals("0"))) {

                                mHashMap = MappingCategory.mapPTY(Integer.parseInt(forecastValue));
                                for (Map.Entry<String, Integer> entry : mHashMap.entrySet()) {

                                    tv_hours[index].setText(forecastTime.substring(0, 2));
                                    iv_weathers[index].setImageResource((int) entry.getValue());
                                    tv_chances[index].setText(entry.getKey());

                                }
                            } else if (category.equals(String.valueOf(MappingCategory.CATEGORY.SKY))) {

                                mHashMap = MappingCategory.mapSKY(Integer.parseInt(forecastValue));
                                for (Map.Entry<String, Integer> entry : mHashMap.entrySet()) {

                                    tv_hours[index].setText(forecastTime.substring(0, 2));
                                    iv_weathers[index].setImageResource((int) entry.getValue());
                                    tv_chances[index].setText(entry.getKey());
                                }
                            }

                            if ((index == 0) && (!prevForecastTime.equals(forecastTime)) && !isFirstLoopDone) {
                                prevForecastTime = forecastTime;
                                isFirstLoopDone = true;
                            }
                            else {
                                if (!prevForecastTime.equals(forecastTime)) {
                                    index++;
                                    prevForecastTime = forecastTime;
                                }
                            }

                        }

                    }else { //handling logical error
                        //Dialog..
                    }

                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherForecastModel> call, @NonNull Throwable t) {
                Toast.makeText(context,t.toString(), Toast.LENGTH_SHORT).show();
                call.cancel();

            }
        });

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

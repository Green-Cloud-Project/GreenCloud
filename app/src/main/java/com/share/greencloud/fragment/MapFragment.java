package com.share.greencloud.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.patloew.rxlocation.RxLocation;
import com.share.greencloud.R;
import com.share.greencloud.activity.BottomNavActivity;
import com.share.greencloud.common.Constants;
import com.share.greencloud.common.location.LocationInfo;
import com.share.greencloud.common.location.LocationPresenter;
import com.share.greencloud.databinding.FragmentMapBinding;
import com.share.greencloud.model.RentalOffice;
import com.share.greencloud.utils.LoadingIndicator;
import com.share.greencloud.viewmodel.MapFragmentViewModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.share.greencloud.common.Constants.REQEUST_TIME_INTERVAL;

public class MapFragment extends Fragment implements OnMapReadyCallback, LocationInfo.View {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    //    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int DEFAULT_ZOOM = 15;

    private String mParam1;
    private String mParam2;

    private MapFragment.OnFragmentInteractionListener mListener;

    private FragmentMapBinding binding;

    private GoogleMap mMap;
    private Marker marker;
    private RxLocation rxLocation;
    private LocationPresenter presenter;

    private MapFragmentViewModel viewModel;
    private List<MarkerOptions> rentalOfficeMarkersOptions = new ArrayList<>();


    public MapFragment() {
        // Required empty public constructor
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        MapsInitializer.initialize(getApplicationContext()); // 커스텀 마커에 아이콘 추가할때 필요

    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        viewModel = ViewModelProviders.of(this).get(MapFragmentViewModel.class);
        LoadingIndicator.getInstance().showProgress(getActivity());

        if (mMap == null) {
            Timber.d("getMapAsync is called");
            binding.map.getMapAsync(this);
        }

        setupInitialLocationInfo();
        getRentalOfficeData();

        //                todo 서버측 코드 반영시 주석처리
        makeRentalOfficeMarkers(new ArrayList<>());

        if (!checkPermissions()) {
            getLocationPermission();
        }

        return binding.getRoot();
    }

    private void getRentalOfficeData() {
        Timber.d("대여소 목록 가져오기: getRentalOfficeData()");

        viewModel.getRentalOfficeData().observe(this, new Observer<List<RentalOffice>>() {
            @Override
            public void onChanged(List<RentalOffice> rentalOffices) {
                Timber.d("대여소 데이터 로딩완료: %s", rentalOffices.size());

//                todo 서버측 코드 반영시 주석제거
//                viewModel.makeRentalOfficeMarkers(rentalOffices);
            }
        });

        viewModel.getLiveDataMarkerOptions().observe(this, new Observer<List<MarkerOptions>>() {
            @Override
            public void onChanged(List<MarkerOptions> markerOptions) {
                rentalOfficeMarkersOptions = markerOptions;
            }
        });
    }

    //                todo 서버측 코드 반영시 주석처리
    private void makeRentalOfficeMarkers(List<RentalOffice> rentalOffices) {
        Timber.d("makeRentalOfficeMarkers");
        LatLng myPosition;
        MarkerOptions markerUnit = null;
        rentalOffices.add(new RentalOffice("강남 대여소"));
        rentalOffices.add(new RentalOffice("서초 대여소"));
        rentalOffices.add(new RentalOffice("선릉 대여소"));
        rentalOffices.add(new RentalOffice("삼성 대여소"));
        rentalOffices.add(new RentalOffice("종로 대여소"));
        Timber.d("rentalOffices 갯수: " + rentalOffices.size());


        for (RentalOffice rentalOffice : rentalOffices) {

            if (rentalOffice.getOffice_name().equals(Constants.GeoData.GANGNAM.getName())) {
                myPosition = new LatLng(Constants.GeoData.GANGNAM.getLet(), Constants.GeoData.GANGNAM.getLon());
                markerUnit = new MarkerOptions().position(myPosition).title(rentalOffice.getOffice_name())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.umbrella_smallest));
            }

            if (rentalOffice.getOffice_name().equals(Constants.GeoData.SEOCHO.getName())) {
                myPosition = new LatLng(Constants.GeoData.SEOCHO.getLet(), Constants.GeoData.SEOCHO.getLon());
                markerUnit = new MarkerOptions().position(myPosition).title(rentalOffice.getOffice_name())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.umbrella_smallest));
            }

            if (rentalOffice.getOffice_name().equals(Constants.GeoData.SEONLEOUNG.getName())) {
                myPosition = new LatLng(Constants.GeoData.SEONLEOUNG.getLet(), Constants.GeoData.SEONLEOUNG.getLon());
                markerUnit = new MarkerOptions().position(myPosition).title(rentalOffice.getOffice_name())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.umbrella_smallest));
            }

            if (rentalOffice.getOffice_name().equals(Constants.GeoData.SAMSEUNG.getName())) {
                myPosition = new LatLng(Constants.GeoData.SAMSEUNG.getLet(), Constants.GeoData.SAMSEUNG.getLon());
                markerUnit = new MarkerOptions().position(myPosition).title(rentalOffice.getOffice_name())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.umbrella_smallest));
            }

            if (rentalOffice.getOffice_name().equals(Constants.GeoData.JONGLO.getName())) {
                myPosition = new LatLng(Constants.GeoData.JONGLO.getLet(), Constants.GeoData.JONGLO.getLon());
                markerUnit = new MarkerOptions().position(myPosition).title(rentalOffice.getOffice_name())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.umbrella_smallest));
            }

            rentalOfficeMarkersOptions.add(markerUnit);
        }

        Timber.d("대여소 마커 목록 갯수: %s", rentalOfficeMarkersOptions.size());
        Timber.d("대여소 마커 목록: %s", rentalOfficeMarkersOptions.get(0).getTitle());
    }

    private void setupInitialLocationInfo() {
        rxLocation = new RxLocation(getContext());
        rxLocation.setDefaultTimeout(REQEUST_TIME_INTERVAL, TimeUnit.SECONDS);
        presenter = new LocationPresenter(rxLocation);

        binding.currentLocation.setOnClickListener(v -> refresh());
    }

    private void refresh() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.detach(this).attach(this).commit();
    }

    private boolean checkPermissions() {
        int permissionState = checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void getLocationPermission() {
//        /*
//         * Request location permission, so that we can get the location of the
//         * device. The result of the permission request is handled by a callback,
//         * onRequestPermissionsResult.
//         */
//        if (checkSelfPermission(getContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION)
//                == PackageManager.PERMISSION_GRANTED) {
//        } else {
//            requestPermissions(
//                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
//                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
//        }

        final RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions
                .request(Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        refresh();
                    } else {
                        // Oups permission denied
                        Toast.makeText(getContext(), "위치정보 사용에 대한 동의가 거부되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String permissions[],
//                                           @NonNull int[] grantResults) {
//        Timber.d("onRequestPermissionsResult() is called");
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    refresh(); // Fragment 화면 갱신
//
//                } else {
//                    Toast.makeText(getContext(), "위치정보 사용에 대한 동의가 거부되었습니다.", Toast.LENGTH_SHORT).show();
//                }
//        }
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.attachView(this);
        postponeEnterTransition();
        Timber.d("onViewCreated is called");

    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof MapFragment.OnFragmentInteractionListener) {
//            mListener = (MapFragment.OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.map.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.map.onStop();
        presenter.detachView();
    }

    @Override
    public void onDestroyView() {
        if (mMap != null) {
            mMap.clear();
        }
        binding.map.onDestroy();
        super.onDestroyView();
        Timber.d("onDestroyView is called");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.map.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.map.onResume();

        Timber.i("onResume() is called");
        if (mMap != null) {
            onMapUpdate(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.map.onPause();
        LoadingIndicator.getInstance().dismiss();
        Timber.i("onPause() is called");
    }

    @Override
    public void onLowMemory() {
        Timber.d("onLowMemory is called again");
        super.onLowMemory();
        binding.map.onLowMemory();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //액티비티가 처음 생성될 때 실행되는 함수
        if (binding.map != null) {
            binding.map.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Timber.i("onMapReady() is called");


        mMap = googleMap;
        LatLng myPosition = new LatLng(presenter.getUserLocation().getLatitude(), presenter.getUserLocation().getLongitude()); // 현재 위치 정보 가져옴.

        marker = mMap.addMarker(new MarkerOptions().position(myPosition).title("현재 위치"));


        List<Marker> markers = new ArrayList<>();
        for (int i = 0; i < rentalOfficeMarkersOptions.size(); ++i) {
            Timber.d("대여소 위치: " + rentalOfficeMarkersOptions.get(i).getPosition());
            markers.add(mMap.addMarker(rentalOfficeMarkersOptions.get(i)));
        }

        marker.showInfoWindow();
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, DEFAULT_ZOOM));
        mMap.getUiSettings().setZoomControlsEnabled(true);

        //sryang 마커 클릭 이벤트 추가
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                ((BottomNavActivity) getActivity()).showBottomSlide();
                return false;
            }
        });

        LoadingIndicator.getInstance().dismiss();

    }

    @Override
    public void onLocationUpdate(Location location) {
        Timber.d("onLocationUpdate is Called");
        if (location != null) {
            presenter.updateUserLocation(location, this);
            Timber.d("updated location is " + location.getLatitude() + " , " + location.getLatitude());
        }
    }

    @Override
    public void onMapUpdate(OnMapReadyCallback callback) {
        Timber.d("getMapAsync is called again");
        marker.remove();
        binding.map.getMapAsync(callback);
        startPostponedEnterTransition();
    }

    @Override
    public void onLocationSettingsUnsuccessful() {

    }

    @Override
    public void onAddressUpdate(Address address) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}

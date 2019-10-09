package com.share.greencloud.presentation.fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import com.exlyo.gmfmt.MarkerInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.SphericalUtil;
import com.patloew.rxlocation.RxLocation;
import com.share.greencloud.R;
import com.share.greencloud.databinding.FragmentMapBinding;
import com.share.greencloud.domain.interator.LocationInfoMVP;
import com.share.greencloud.domain.model.RentalOffice;
import com.share.greencloud.presentation.activity.BottomNavActivity;
import com.share.greencloud.presentation.presenter.LocationPresenter;
import com.share.greencloud.presentation.viewmodel.MapFragmentViewModel;
import com.share.greencloud.utils.LoadingIndicator;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.share.greencloud.common.Constants.REQEUST_TIME_INTERVAL;

public class MapFragment extends Fragment implements OnMapReadyCallback, LocationInfoMVP.View,
        GoogleMap.OnMarkerClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int DEFAULT_ZOOM = 15;

    private String mParam1;
    private String mParam2;

    private MapFragment.OnFragmentInteractionListener mListener;

    private FragmentMapBinding binding;

    private GoogleMap mMap;
    private RxLocation rxLocation;
    private LocationPresenter presenter;

    private MapFragmentViewModel viewModel;
    private List<MarkerOptions> rentalOfficeMarkersOptions = new ArrayList<>();
    private List<RentalOffice> rentalOfficeArrayList = new ArrayList<>();

    public MapFragment() {
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

        bindView();

        if (mMap == null) {
            Timber.d("getMapAsync is called");
            binding.map.getMapAsync(this);
        }

        setupInitialLocationInfo();
        getRentalOfficeData();

        if (!checkPermissions()) {
            getLocationPermission();
        }

        if (!LoadingIndicator.getInstance().isShowing()) {
            LoadingIndicator.getInstance().showProgress(getApplicationContext());
        }

        return binding.getRoot();
    }

    private void bindView() {
        viewModel = ViewModelProviders.of(this).get(MapFragmentViewModel.class);
        binding.setBottomNavActivity((BottomNavActivity) getActivity());
        binding.fabCurrentLocation.setOnClickListener(v -> refresh());
    }

    private void getRentalOfficeData() {
        Timber.d("대여소 목록 가져오기: getRentalOfficeData()");

        viewModel.getRentalOfficeData().observe(this, rentalOffices -> {
            Timber.d("대여소 데이터 로딩완료: %s", rentalOffices.size());
            viewModel.makeRentalOfficeMarkers(rentalOffices);

            for (RentalOffice rentalOffice : rentalOffices) {
                viewModel.insert(rentalOffice);
            }

        });

        viewModel.getAllRentalOfficesFromDB().observe(this, rentalOffices -> {
            Timber.d("대여소 데이터 로딩완료 from DB(Room): %s", rentalOffices.size());
            rentalOfficeArrayList = rentalOffices;
        });

        viewModel.getLiveDataMarkerOptions().observe(this, markerOptions ->
                rentalOfficeMarkersOptions = markerOptions);
    }

    private void setupInitialLocationInfo() {
        rxLocation = new RxLocation(getContext());
        rxLocation.setDefaultTimeout(REQEUST_TIME_INTERVAL, TimeUnit.SECONDS);
        presenter = new LocationPresenter(rxLocation);
    }

    public void refresh() {
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //액티비티가 처음 생성될 때 실행되는 함수
        if (binding.map != null) {
            binding.map.onCreate(savedInstanceState);
        }
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
    public void onResume() {
        super.onResume();
        Timber.i("onResume() is called");

        binding.map.onResume();

        if (mMap != null) {
            onMapUpdate(this);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        Timber.i("onPause() is called");

        binding.map.onPause();
        if (LoadingIndicator.getInstance().isShowing()) {
            LoadingIndicator.getInstance().dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.map.onStop();
        presenter.detachView();
    }

    @Override
    public void onDestroyView() {
        Timber.d("onDestroyView is called");
        super.onDestroyView();
        if (mMap != null) {
            mMap.clear();
        }
        binding.map.onDestroy();
    }

    @Override
    public void onLowMemory() {
        Timber.d("onLowMemory is called again");
        super.onLowMemory();
        binding.map.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.map.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Timber.i("onMapReady() is called");
        mMap = googleMap;

        mMap.setOnMapLoadedCallback(() -> {
            if (LoadingIndicator.getInstance().isShowing()) {
                LoadingIndicator.getInstance().dismiss();
            }
        });

        binding.mapFloatingMarkersOverlay.setSource(mMap); // mapFloatingMarkersOverlay 초기화
        final int color = Color.GREEN;
        MarkerInfo mi;
        LatLng myPosition = new LatLng(presenter.getUserLocation().getLatitude(), presenter.getUserLocation().getLongitude()); // 현재 위치 정보 가져옴.

        // 대여소 Marker 및 Infpwindo 표시
        List<Marker> markers = new ArrayList<>();
        for (int i = 0; i < rentalOfficeMarkersOptions.size(); ++i) {
            Timber.d("대여소 위치: " + rentalOfficeMarkersOptions.get(i).getPosition());
            // Marker와 Infopwindow 연결
            mi = new MarkerInfo(rentalOfficeMarkersOptions.get(i).getPosition(), rentalOfficeMarkersOptions.get(i).getTitle(), color);
            markers.add(mMap.addMarker(new MarkerOptions().position(mi.getCoordinates())));
            binding.mapFloatingMarkersOverlay.addMarker(i, mi);
            // 대여소 Marker
            markers.add(mMap.addMarker(rentalOfficeMarkersOptions.get(i)));
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, DEFAULT_ZOOM)); // 현재 위치로 지도를 표시해줌
        mMap.getUiSettings().setZoomControlsEnabled(false);         // 줌 설정 비활화
        mMap.getUiSettings().setMyLocationButtonEnabled(false);     // gps 버튼 비활화
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.setOnMarkerClickListener(this);

        // 사용자 위치 주변에 서클 추가
        CircleOptions circleOptions = (new CircleOptions()
                .center(myPosition)
                .radius(50) // In meters
                .fillColor(0xffb2dfdb)
                .strokeColor(0xffb2dfdb)).strokeWidth(2);

        if (checkPermissions()) {
            // 사용자 위치 및 서클 표시
            mMap.setMyLocationEnabled(true);
            mMap.addCircle(circleOptions);
        }


        if (LoadingIndicator.getInstance().isShowing()) {
            LoadingIndicator.getInstance().dismiss();
        }
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
        binding.map.getMapAsync(callback);
        startPostponedEnterTransition();
    }

    // 대여소 마커를 클릭했을때 대여소의 정보를 표시해줌.
    @Override
    public boolean onMarkerClick(Marker marker) {
        TextView tv_retal_spot_name = ((BottomNavActivity) getActivity()).findViewById(R.id.tv_spot_name);
        TextView tv_retal_spot_location = ((BottomNavActivity) getActivity()).findViewById(R.id.tv_spot_location);
        TextView tv_um_count = ((BottomNavActivity) getActivity()).findViewById(R.id.tv_um_count);
        TextView tv_rental_spot_distance = ((BottomNavActivity) getActivity()).findViewById(R.id.tv_spot_distance);

        // 현재 위치에서 마커까지 거리 계산
        tv_rental_spot_distance.setText(String.format("현재 위치에서 %d km", calculateDistance(marker.getPosition())));

        if (marker.getTitle() != null) {
            int marker_name = Integer.valueOf(marker.getTitle());

            for (RentalOffice rentalOffice : rentalOfficeArrayList) {
                if (marker_name == rentalOffice.getUmbrella_count()) {
                    tv_retal_spot_name.setText(rentalOffice.getOffice_name());
                    tv_retal_spot_location.setText(rentalOffice.getOffice_location());
                    tv_um_count.setText(String.valueOf(rentalOffice.getUmbrella_count()));
                    ((BottomNavActivity) getActivity()).showBottomSlide();
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(rentalOffice.getLat(), rentalOffice.getLon()), DEFAULT_ZOOM));
                }
            }
        }
        return true;
    }

    // 현재 위치에서 마커까지 거리 계산
    private int calculateDistance(LatLng markerPosition) {
        LatLng currnetPosition = new LatLng(presenter.getUserLocation().getLatitude(), presenter.getUserLocation().getLongitude());
        double distance = SphericalUtil.computeDistanceBetween(currnetPosition, markerPosition) / 1000;
        return (int) distance;
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

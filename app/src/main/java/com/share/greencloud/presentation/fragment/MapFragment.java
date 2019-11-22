package com.share.greencloud.presentation.fragment;

import android.Manifest;
import android.content.Context;
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
import androidx.lifecycle.ViewModelProviders;

import com.exlyo.gmfmt.MarkerInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.patloew.rxlocation.RxLocation;
import com.share.greencloud.R;
import com.share.greencloud.databinding.FragmentMapBinding;
import com.share.greencloud.domain.interator.LocationInfoMVP;
import com.share.greencloud.domain.model.RentalOffice;
import com.share.greencloud.presentation.activity.MainActivity;
import com.share.greencloud.presentation.presenter.LocationPresenter;
import com.share.greencloud.presentation.viewmodel.MapFragmentViewModel;
import com.share.greencloud.presentation.viewmodel.SharedViewModel;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.facebook.FacebookSdk.getApplicationContext;
import static com.share.greencloud.common.Constants.CIRCLE_OPTION_COLOR;
import static com.share.greencloud.common.Constants.REQEUST_TIME_INTERVAL;

public class MapFragment extends Fragment implements OnMapReadyCallback, LocationInfoMVP.View,
        GoogleMap.OnMarkerClickListener {

    private static final int DEFAULT_ZOOM = 15;

    private MapFragment.OnFragmentInteractionListener mListener;

    private FragmentMapBinding binding;

    private GoogleMap mMap;
    private RxLocation rxLocation;
    private LocationPresenter presenter;

    private MapFragmentViewModel viewModel;
    Circle mapCircle;

    private SharedViewModel sharedViewModel;

    public MapFragment() {
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapsInitializer.initialize(getApplicationContext()); // 커스텀 마커에 아이콘 추가할때 필요
    }


    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        bindView(inflater, container);
        setupInitialLocationInfo();
        getRentalOfficeData();

        if (!checkPermissions()) {
            getLocationPermission();
        }


        return binding.getRoot();
    }

    private void bindView(LayoutInflater inflater, ViewGroup container) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);
        viewModel = ViewModelProviders.of(this).get(MapFragmentViewModel.class);

        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        binding.setMainActivity((MainActivity) getActivity());
        binding.setMapFragment(this);
        binding.fabCurrentLocation.setOnClickListener(v -> refresh());
        binding.map.getMapAsync(this);
    }

    private void getRentalOfficeData() {
        Timber.d("대여소 목록 가져오기: getRentalOfficeData()");

        viewModel.getRentalOfficeData().observe(getViewLifecycleOwner(), rentalOffices -> {
            Timber.d("대여소 데이터 로딩완료: %s", rentalOffices.size());
            viewModel.makeRentalOfficeMarkers(rentalOffices);
        });
    }

    private void setupInitialLocationInfo() {
        rxLocation = new RxLocation(getContext());
        rxLocation.setDefaultTimeout(REQEUST_TIME_INTERVAL, TimeUnit.SECONDS);
        presenter = new LocationPresenter(rxLocation, getLifecycle());
    }

    // 현재 위치를 다시 가져오도록 함
    public void refresh() {
        Timber.d("refresh is called");
        if (sharedViewModel.getMovedNewPosition()) {
            sharedViewModel.clearValue();
        }
        binding.progressBar.setVisibility(View.VISIBLE);
        presenter.detachView();
        presenter.attachView(this);
        postponeEnterTransition();
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
                    if (granted) {
                        refresh();
                    } else {
                        Toast.makeText(getContext(), "위치정보 사용에 대한 동의가 거부되었습니다.", Toast.LENGTH_SHORT).show();
                        refresh();
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

        Timber.d("onViewCreated is called");
        presenter.attachView(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //액티비티가 처음 생성될 때 실행되는 함수
        if (binding.map != null) {
            Timber.d("onActivityCreated is called");
            binding.map.onCreate(savedInstanceState);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Timber.d("onAttach is called");
        if (context instanceof MapFragment.OnFragmentInteractionListener) {
            mListener = (MapFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Timber.d("onDetach is called");
        mListener = null;
    }

    @Override
    public void onStart() {
        super.onStart();

        Timber.d("onStart is called");
        binding.map.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();

        Timber.d("onResume() is called");
        binding.map.onResume();

        if (mMap != null) {
            if (sharedViewModel.getMovedNewPosition()) {
                binding.map.getMapAsync(this);
            } else {
                refresh();
            }
        } else {
            binding.progressBar.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        Timber.d("onPause is called");
        binding.map.onPause();
        if (sharedViewModel.getMovedNewPosition()) {
            sharedViewModel.clearValue();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        Timber.d("onStop is called");
        binding.map.onStop();
        presenter.detachView();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Timber.d("onDestroyView is called");
        if (mMap != null) {
            mMap.clear();
        }
        binding.map.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        Timber.d("onLowMemory is called again");
        binding.map.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Timber.d("onSaveInstanceState is called again");
        binding.map.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Timber.d("onMapReady is called");

        mMap = googleMap;

        addRentalOfficeMarker();
        setupMap();

        mMap.setOnMapLoadedCallback(() -> binding.progressBar.setVisibility(View.INVISIBLE));
    }

    // Map에 필수적인 설정을 세팅
    private void setupMap() {

        // 기존 서클 제거 ( 제거 하지 않으면 중복으로 생김)
        if (mapCircle != null) {
            mapCircle.remove();
        }

        LatLng myPosition = new LatLng(presenter.getUserLocation().getLatitude(),
                presenter.getUserLocation().getLongitude()); // 현재 위치 정보 가져옴.

        mMap.getUiSettings().setZoomControlsEnabled(false);         // 줌 설정 비활화
        mMap.getUiSettings().setMyLocationButtonEnabled(false);     // gps 버튼 비활화
        mMap.setOnMarkerClickListener(this);                        //
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, DEFAULT_ZOOM)); // 현재 위치로 지도를 표시해줌


        if (sharedViewModel.getMovedNewPosition()) {
            LatLng clickedPositon = sharedViewModel.getPosition().getValue();
            if (clickedPositon != null) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(clickedPositon, DEFAULT_ZOOM));
            }
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, DEFAULT_ZOOM));
        }


        if (checkPermissions()) {
            // 사용자 위치 표시
            mMap.setMyLocationEnabled(true);
        }

        // 사용자 위치 주변에 서클 추가
        CircleOptions circleOptions = (new CircleOptions()
                .center(myPosition)
                .radius(50) // In meters
                .fillColor(CIRCLE_OPTION_COLOR)
                .strokeColor(CIRCLE_OPTION_COLOR)).strokeWidth(2);

        // 사용자 위치가 나타났을때 서클 추가 해주도록
        if (mMap.isMyLocationEnabled()) {
            mapCircle = mMap.addCircle(circleOptions);
        }
    }


    private void addRentalOfficeMarker() {

        List<Marker> markers = new ArrayList<>();
        List<MarkerOptions> markerOptions = viewModel.getMarkerOptionsList();
        final int color = Color.GREEN;
        MarkerInfo mi;

        binding.mapFloatingMarkersOverlay.setSource(mMap); // mapFloatingMarkersOverlay 초기화

        // 대여소 Marker 및 Infpwindo 표시
        for (int i = 0; i < markerOptions.size(); ++i) {

            // Marker와 Infopwindow 연결
            mi = new MarkerInfo(markerOptions.get(i).getPosition(), markerOptions.get(i).getTitle(), color);
            markers.add(mMap.addMarker(new MarkerOptions().position(mi.getCoordinates())));
            binding.mapFloatingMarkersOverlay.addMarker(i, mi);

            // 대여소 Marker
            markers.add(mMap.addMarker(markerOptions.get(i)));
        }

    }

    @Override
    public void onLocationUpdate(Location location) {

        Timber.d("onLocationUpdate is Called");
        if (location != null) {
            presenter.updateUserLocation(location, this);
            viewModel.addDistanceInfoToRentalOffice(location);
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

        TextView tv_rental_spot_name = ((MainActivity) getActivity()).findViewById(R.id.tv_spot_name);
        TextView tv_rental_spot_location = ((MainActivity) getActivity()).findViewById(R.id.tv_spot_location);
        TextView tv_um_count = ((MainActivity) getActivity()).findViewById(R.id.tv_um_count);
        TextView tv_rental_spot_distance = ((MainActivity) getActivity()).findViewById(R.id.tv_spot_distance);
        TextView tv_rental_spot_id = getActivity().findViewById(R.id.tv_spot_id);

        // 클릭된 마커를 구분하기 위하여 위치정보를 로딩
        LatLng marker_position = marker.getPosition();

        List<RentalOffice> rentalOfficeArrayList = viewModel.getRentalOffice();
        for (RentalOffice rentalOffice : rentalOfficeArrayList) {
            LatLng officePosition = new LatLng(rentalOffice.getLat(), rentalOffice.getLon());
            // 대여소 정보 리스트에서 위치정보를 동일한 대여소의 정보를 보여준다
            if (marker_position.equals(officePosition)) {
                tv_rental_spot_name.setText(rentalOffice.getOffice_name());
                tv_rental_spot_location.setText(rentalOffice.getOffice_location());
                tv_um_count.setText(String.valueOf(rentalOffice.getUmbrella_count()));
                tv_rental_spot_distance.setText("현재 위치에서 " + addDistanceSign(rentalOffice.getDistance()));
                tv_rental_spot_id.setText(String.valueOf(rentalOffice.getOffice_id())) ;

                ((MainActivity) getActivity()).showBottomSlide();
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(rentalOffice.getLat(), rentalOffice.getLon()), DEFAULT_ZOOM));
            }
        }
        return true;
    }

    // 현재 위치에서 마커까지 거리 계산
    private String addDistanceSign(int result) {

        // 1km 기준으로 표기법을 구분지음
        if (result > 1000)
            return ((int) result / 1000) + " km";
        else
            return (int) result + " m";
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

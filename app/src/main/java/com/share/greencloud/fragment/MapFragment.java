package com.share.greencloud.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import androidx.databinding.DataBindingUtil;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.share.greencloud.R;
import com.share.greencloud.common.LastLocationInfo;
import com.share.greencloud.databinding.FragmentMapBinding;
import com.share.greencloud.lifecycleobserver.MyObserver;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

import static androidx.core.content.ContextCompat.checkSelfPermission;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private MapFragment.OnFragmentInteractionListener mListener;

    private FragmentMapBinding binding;

    private GoogleMap mMap;
    LocationManager locationManager;
    private LastLocationInfo lastLocationInfo;

    private static final int DEFAULT_ZOOM = 15;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;

    private MyObserver observer;

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
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);

        observer = new MyObserver(this, getContext());
        lastLocationInfo = new LastLocationInfo(getContext());

        if (mMap == null) {
            Timber.d("getMapAsync is called");
            binding.map.getMapAsync(this);
            mLocationPermissionGranted = false;
        }

        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
            Timber.d("getMapAsync is called again");
            binding.map.getMapAsync(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.map.onPause();
        Timber.i("onPause() is called");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.map.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding.map.onDestroy();
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
        LatLng myPosition = getPosition(); // 현재 위치 정보 가져옴.

        mMap.addMarker(new MarkerOptions().position(myPosition).title("Marker in my current Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, DEFAULT_ZOOM));
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    private LatLng getPosition() {
        Location location = lastLocationInfo.getLastLocation();
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        return new LatLng(latitude, longitude);
    }

    private Location providerInfo() {
        Criteria criteria = new Criteria();
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        String provider = locationManager.getBestProvider(criteria, true);
        final Location location = new Location("");
        if (checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            location.setLatitude(37.56);
            location.setLongitude(126.97);
            return location;
        }
        return locationManager.getLastKnownLocation(provider);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

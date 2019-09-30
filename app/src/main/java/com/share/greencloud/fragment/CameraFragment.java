package com.share.greencloud.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.share.greencloud.R;

import java.io.IOException;


public class CameraFragment extends Fragment {

    Context context;
    View        view;

    private SurfaceView mCamera_preview;
    private BarcodeDetector mBarcodeDetector;
    private CameraSource mCameraSource;
    private ImageButton mScanButton;



    public static CameraFragment newInstance(String param1, String param2) {
        CameraFragment fragment = new CameraFragment();
        Bundle args = new Bundle();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        context  = container.getContext();
        view =  inflater.inflate(R.layout.activity_scan, container, false);
        bindView();
        setupScanner();
        return view;
    }

    private void bindView()  {

        mScanButton    =  view.findViewById(R.id.ib_qrcodescan);
        mCamera_preview = view.findViewById(R.id.camera_preview);
//        mCamera_preview.setVisibility(View.INVISIBLE);

        mCamera_preview.setVisibility(View.VISIBLE); //  Fragment가 실행되면 카메라가 바로 실행되도록 수정함. -Bentley
//        mScanButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCamera_preview.setVisibility(View.VISIBLE);
//            }
//        });
    }

    private void setupScanner() {

        //context = view.getContext().getApplicationContext();
        mBarcodeDetector = new BarcodeDetector.Builder(context)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();
        mCameraSource = new CameraSource.Builder(context, mBarcodeDetector)
                .setRequestedPreviewSize(1024, 768)
                .setAutoFocusEnabled(true)
                .build();


        mCamera_preview.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                        mCameraSource.start(mCamera_preview.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, 1024);
                    }
                } catch (IOException e) {
                    Log.e("Camera   error-->> ", e.getMessage().toString());
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                mCameraSource.stop();
            }
        });


        mBarcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() > 0){
                    mBarcodeDetector.release();
                    //result..
                }

            }
        });
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

}

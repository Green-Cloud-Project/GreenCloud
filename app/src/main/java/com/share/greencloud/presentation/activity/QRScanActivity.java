package com.share.greencloud.presentation.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.share.greencloud.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import timber.log.Timber;

public class QRScanActivity extends AppCompatActivity {
    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.d("onCreate");
        super.onCreate(savedInstanceState);
        rxPermissions = new RxPermissions(this);
    }

    private void parsingMessage() {
        Intent intent = getIntent();
        if (intent.getStringExtra("finish") != null) {
            if (intent.getStringExtra("finish").equals("close")) {
                Toast.makeText(this, R.string.finish_qr_code_scan_msg, Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            check_permission();
        }
    }

    private void check_permission() {
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                            if (granted) {
                                onQrcodeScanner();
                            } else {
                                Toast.makeText(this, R.string.reject_use_of_camera_access_msg, Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                );
    }

    private void onQrcodeScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setBeepEnabled(false);
        integrator.setCaptureActivity(CustomScannerActivity.class);
        integrator.setPrompt(getString(R.string.guide_msg_for_qr_scan));
        integrator.initiateScan();
    }


    @Override
    protected void onResume() {
        super.onResume();
        parsingMessage();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                finish(); // 액티비티 종료를 위해서 호출
            } else {
                //todo: 2019-11-15  QR코드 내용을 실행시키는 로직 추가해야됨.
                Toast.makeText(this, "Scan 결: " + result.getContents(), Toast.LENGTH_LONG).show();
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

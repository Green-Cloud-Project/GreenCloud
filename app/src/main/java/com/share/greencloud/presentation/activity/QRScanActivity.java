package com.share.greencloud.presentation.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
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
                Toast.makeText(this, intent.getStringExtra("QR코드 스캔을 종료합니"), Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(this, "카메라 접근이 거부되었습니다", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                );
    }

    private void onQrcodeScanner() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setBeepEnabled(false);
        integrator.setCaptureActivity(CustomScannerActivity.class);
        integrator.setPrompt("대여소에 부착된 QR코드를 인식해주세요");
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
//                Toast.makeText(this, "QR 코드 인식 실패하였습니다", Toast.LENTH_LONG).show();
                finish();
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

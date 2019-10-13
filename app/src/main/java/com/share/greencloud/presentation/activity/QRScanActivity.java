package com.share.greencloud.presentation.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;

import com.king.zxing.CaptureActivity;
import com.king.zxing.Intents;
import com.share.greencloud.R;
import com.tbruyelle.rxpermissions2.RxPermissions;

import timber.log.Timber;

public class QRScanActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_SCAN = 0X01;
    private RxPermissions rxPermissions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.d("onCreate");
        super.onCreate(savedInstanceState);
        rxPermissions = new RxPermissions(this);
//        doFullScreen();
    }

    private void doFullScreen() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE |
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    protected void onResume() {
        super.onResume();

        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeCustomAnimation(this, R.anim.in, R.anim.out);
        Intent intent = new Intent(this, CaptureActivity.class);

        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe(granted -> {
                            if (granted) {
                                ActivityCompat.startActivityForResult(this, intent, REQUEST_CODE_SCAN, optionsCompat.toBundle());
                            } else {
                                Toast.makeText(this, "카메라 접근이 거부되었습니다", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN:
                    String result = data.getStringExtra(Intents.Scan.RESULT);
                    Toast.makeText(this, result, Toast.LENGTH_SHORT).show(); // 현재는 QR코드 내용 출력 후 종료
                    // TODO: 2019-10-06  QR코드 내용을 실행시키는 로직 추가해야됨.
                    finish();
                    break;
            }
        }
    }

}

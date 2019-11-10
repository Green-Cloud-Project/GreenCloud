package com.share.greencloud.presentation.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.share.greencloud.R;
import com.share.greencloud.presentation.fragment.OnBoardingFragment;

public class OnBoardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        ((ViewPager) findViewById(R.id.vp_on_boarding)).setAdapter(new OnBoardingVpAdt(getSupportFragmentManager()));

        findViewById(R.id.rl_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.go(OnBoardingActivity.this);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }

    private class OnBoardingVpAdt extends FragmentPagerAdapter {

        public OnBoardingVpAdt(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new OnBoardingFragment();
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}

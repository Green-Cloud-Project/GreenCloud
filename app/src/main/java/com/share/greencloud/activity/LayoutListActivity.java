package com.share.greencloud.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.share.greencloud.R;
import com.share.greencloud.fragment.CameraFragment;
import com.share.greencloud.fragment.DrawerFragment;
import com.share.greencloud.fragment.GetUmbrellaCompleteFragment;
import com.share.greencloud.fragment.GreenCloudInfoFragment;
import com.share.greencloud.fragment.InputCodeFragment;
import com.share.greencloud.fragment.LayoutListFragment;
import com.share.greencloud.fragment.LoginFragment;
import com.share.greencloud.fragment.MyGreenFragment;
import com.share.greencloud.googlelogin.GoogleLoginActivity;
import com.share.greencloud.kakaologin.KakaoLoginActiviy;

public class LayoutListActivity extends AppCompatActivity implements LayoutListFragment.CommunicateListener,
        GreenCloudInfoFragment.OnFragmentInteractionListener,
        MyGreenFragment.OnFragmentInteractionListener,
        CameraFragment.OnFragmentInteractionListener,
        DrawerFragment.OnFragmentInteractionListener,
        LoginFragment.OnFragmentInteractionListener,
        GetUmbrellaCompleteFragment.OnFragmentInteractionListener,
        InputCodeFragment.OnFragmentInteractionListener {

    ViewPager vp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_list);

        vp = findViewById(R.id.vp);
        vp.setAdapter(new VpAdt(getSupportFragmentManager()));
    }

    @Override
    public void clickItem(LayoutListFragment.FragmentList fragmentListType) {
        switch (fragmentListType) {
            case LOGIN:
                vp.setCurrentItem(1);
                break;
            case MYGREEN:
                vp.setCurrentItem(2);
                break;
            case GREEN_CLOUD_INFO:
                vp.setCurrentItem(3);
                break;
            case CAMERA:
                vp.setCurrentItem(4);
                break;
            case DRAWER:
                vp.setCurrentItem(5);
                break;
            case COMPLETE:
                vp.setCurrentItem(6);
                break;
            case CODE:
                vp.setCurrentItem(6);
                break;
            case KAKAO_LOGIN:
                Intent intent = new Intent(LayoutListActivity.this, KakaoLoginActiviy.class);
                startActivity(intent);
                break;
            case GOOGLE_LOGIN:
                Intent g_intent = new Intent(LayoutListActivity.this, GoogleLoginActivity.class);
                startActivity(g_intent);
                break;

        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    private class VpAdt extends FragmentPagerAdapter {

        public VpAdt(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i) {
                case 0:
                    return new LayoutListFragment();
                case 1:
                    return LoginFragment.newInstance();
                case 2:
                    return MyGreenFragment.newInstance("", "");
                case 3:
                    return GreenCloudInfoFragment.newInstance("", "");
                case 4:
                    return CameraFragment.newInstance("", "");
                case 5:
                    return DrawerFragment.newInstance("", "");
                case 6:
                    return GetUmbrellaCompleteFragment.newInstance("", "");
                case 7:
                    return InputCodeFragment.newInstance("", "");
            }
            return null;
        }

        @Override
        public int getCount() {
            return 8;
        }
    }

    @Override
    public void onBackPressed() {
        vp.setCurrentItem(0);
    }
}

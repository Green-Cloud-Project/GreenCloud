package com.share.greencloud.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.share.greencloud.R;
import com.share.greencloud.com.jk.app.fragment.JkAppFragment;
import com.share.greencloud.fragment.CameraFragment;
import com.share.greencloud.fragment.DrawerFragment;
import com.share.greencloud.fragment.GetUmbrellaCompleteFragment;
import com.share.greencloud.fragment.GreenCloudInfoFragment;
import com.share.greencloud.fragment.InputCodeFragment;
import com.share.greencloud.fragment.LayoutListFragment;
import com.share.greencloud.fragment.LoginFragment;
import com.share.greencloud.fragment.MapFragment;
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
        InputCodeFragment.OnFragmentInteractionListener,
        JkAppFragment.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener {

    ViewPager vp;

    LoginFragment loginFragment;
    JkAppFragment facebookSNSFragment;

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
                Intent myGreenIntent = new Intent(LayoutListActivity.this, BottomNavActivity.class);
                startActivity(myGreenIntent);
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

            case MyApp:
                vp.setCurrentItem(8);       //
                break;

            case MAP:
                vp.setCurrentItem(7);
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
                    loginFragment = LoginFragment.newInstance();
                    return loginFragment;
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

                case 8:    // add
                    facebookSNSFragment = JkAppFragment.newInstance("", "");
                    return facebookSNSFragment;

                case 9:
                    return MapFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 10;
        }   // count
    }

    @Override
    public void onBackPressed() {
        vp.setCurrentItem(0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        // return
        if (facebookSNSFragment != null)
            facebookSNSFragment.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (loginFragment != null)
            loginFragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

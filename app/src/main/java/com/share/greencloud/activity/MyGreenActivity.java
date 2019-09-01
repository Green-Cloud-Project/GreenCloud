package com.share.greencloud.activity;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.share.greencloud.R;
import com.share.greencloud.fragment.NewsFragment;
import com.share.greencloud.fragment.MapFragment;
import com.share.greencloud.fragment.NewsFragment;
import com.share.greencloud.fragment.WeatherFragment;


public class MyGreenActivity extends AppCompatActivity  implements
        WeatherFragment.OnFragmentInteractionListener,
        MapFragment.OnFragmentInteractionListener,
        NewsFragment.OnFragmentInteractionListener {


    private final String[] PAGE_TITLES = new String[] {
            "MyGreen",
            "위치찾기",
            "환경뉴스"
    };

    private final Fragment[] PAGES = new Fragment[] {
            new WeatherFragment(),
            new MapFragment(),
            new NewsFragment()

    };

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mygreen);

        mViewPager = findViewById(R.id.viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout) ;
        ((TabLayout) findViewById(R.id.tab_layout)).setupWithViewPager(mViewPager);
        mViewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

    }


    public class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            return PAGES[position];
        }

        @Override
        public int getCount() {
            return PAGES.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return PAGE_TITLES[position];
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


}

package com.share.greencloud.presentation.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.share.greencloud.R;
import com.share.greencloud.domain.login.LoginManager;
import com.share.greencloud.presentation.fragment.LoginFragment;

public class LoginActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_container, LoginFragment.newInstance())
                .commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(LoginManager.getInstance().isLogin(this)){
            this.finish();
        }
    }

    public static void go(AppCompatActivity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}

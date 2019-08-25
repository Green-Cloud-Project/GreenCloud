package com.share.greencloud.googlelogin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.share.greencloud.R;
import com.share.greencloud.login.LoginManager;
import com.share.greencloud.login.LoginType;


public class GoogleLoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 9001;

    private GoogleApiClient mGoogleApiClient;
    private Button mBtnLogin;
    private Button mBtnLogOut;
    private GoogleSignInClient mGoogleSignInClient;
    private ProgressDialog mProgressDialog;

    private TextView email;
    private TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_login);

        mBtnLogin = (Button) findViewById(R.id.btn_google_login);
        mBtnLogOut = (Button) findViewById(R.id.btn_google_logout);


        email = (TextView) findViewById(R.id.tv_email);
        name = (TextView) findViewById(R.id.tv_name);

        initControl();
        initGoogleSettings();

    }

    private void initControl() {

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.btn_google_login:
                        //signIn();
                        LoginManager.getInstance().signIn(LoginType.GOOGLE, GoogleLoginActivity.this);
                        break;
                    case R.id.btn_google_logout:
                        signOut();
                        break;
                }
            }
        };

        mBtnLogin.setOnClickListener(onClickListener);
        mBtnLogOut.setOnClickListener(onClickListener);

    }


    private void initGoogleSettings() {

        //Not sure whether need to send id token to server.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                // .requestIdToken("424239551614-a5uk8o5d1148bgb374jhjbphe7370n31.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }


    private void signIn() {
        showProgressDialog();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        updateUI(null);
                    }
                });
    }


    @Override
    protected void onStart() {
        super.onStart();
        GoogleSignInAccount alreadyLogin = GoogleSignIn.getLastSignedInAccount(this);

        if (alreadyLogin != null) {
            Toast.makeText(this, "Already login", Toast.LENGTH_SHORT).show();
            updateUI(alreadyLogin);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        hideProgressDialog();

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else {
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        hideProgressDialog();
        Toast.makeText(this, "onConnectionFailed", Toast.LENGTH_SHORT).show();
    }


    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String token = account.getIdToken();
            //TODO : send s token to server to validate
            updateUI(account);
        } catch (ApiException e) {
            Toast.makeText(this, "handleSignInResult= " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
            updateUI(null);
        }
    }

    //Replace with real Activity later..
    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {

            email.setText(account.getEmail());
            name.setText(account.getDisplayName());

            findViewById(R.id.btn_google_login).setVisibility(View.GONE);
            findViewById(R.id.tv_email).setVisibility(View.VISIBLE);
            findViewById(R.id.tv_name).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_google_logout).setVisibility(View.VISIBLE);
        }
        else {
            findViewById(R.id.btn_google_login).setVisibility(View.VISIBLE);
            findViewById(R.id.btn_google_logout).setVisibility(View.GONE);
            findViewById(R.id.tv_email).setVisibility(View.GONE);
            findViewById(R.id.tv_name).setVisibility(View.GONE);

        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

}
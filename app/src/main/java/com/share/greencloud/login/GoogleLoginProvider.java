package com.share.greencloud.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.share.greencloud.R;

public class GoogleLoginProvider implements ILoginProvider {

    public static final int RC_SIGN_IN = 9001;

    private GoogleSignInClient mGoogleSignInClient;

    private ProgressDialog mProgressDialog;

    private void initGoogleSettings(Context context) {

        //Not sure whether need to send id token to server.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(context, gso);

    }

    public void signIn(AppCompatActivity activity) {
        showProgressDialog(activity);
        if (mGoogleSignInClient == null)
            initGoogleSettings(activity);
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /**
     로그아웃을 한다.
     */
    @Override
    public void signOut() {

    }

    /**
     현재 로그인 상태인지?
     */
    @Override
    public boolean isSignIn() {
        return false;
    }

    /**
     로그인 성공 실패여부

     @param isSuccess
     */
    @Override
    public void signInResult(boolean isSuccess) {

    }

    /**
     각 플렛폼에서 서버를 통해 본인을 정보에 접근할 수 있는 키값

     @return 키값(엑세스토큰, authkey...)
     */
    @Override
    public String getKey() {
        return null;
    }

    public void signOut(AppCompatActivity activity) {
        if (mGoogleSignInClient == null)
            initGoogleSettings(activity);

        mGoogleSignInClient.signOut()
                .addOnCompleteListener(activity, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }

    private void showProgressDialog(Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage(context.getString(R.string.loading));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    public void handleSignInResult(Context context, Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            //account.getIdToken()
            //updateUI(account);
        } catch (ApiException e) {
            Toast.makeText(context, "handleSignInResult= " + e.getStatusCode(), Toast.LENGTH_SHORT).show();
            //updateUI(null);
        }
    }

    public void onActivityResult(Context context, int requestCode, int resultCode, Intent data) {
        hideProgressDialog();
        if (requestCode == GoogleLoginProvider.RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(context, task);
        }
        else {
            Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }
}

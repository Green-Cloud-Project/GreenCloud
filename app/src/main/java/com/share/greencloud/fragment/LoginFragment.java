package com.share.greencloud.fragment;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.login.Login;
import com.share.greencloud.R;
import com.share.greencloud.api.ApiManager;
import com.share.greencloud.api.CallbackListener;
import com.share.greencloud.common.Constants;
import com.share.greencloud.databinding.FragmentLoginBinding;
import com.share.greencloud.login.KakaoLoginProvider;
import com.share.greencloud.login.LoginEventListener;
import com.share.greencloud.login.LoginManager;
import com.share.greencloud.login.LoginType;
import com.share.greencloud.model.UserBody;
import com.share.greencloud.utils.GreenCloudPreferences;

public class LoginFragment extends Fragment {

    private final String TAG = LoginFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;
    private FragmentLoginBinding fragmentLoginBinding;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentLoginBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false);
        fragmentLoginBinding.setLifecycleOwner(this);
        fragmentLoginBinding.setActivity((AppCompatActivity) getActivity());
        return fragmentLoginBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LoginManager.getInstance().setLoginEventListener(loginType -> {
            Toast.makeText(getContext(), loginType.name(), Toast.LENGTH_SHORT).show();
            //서버에 던지기
            if (loginType == LoginType.KAKAO)
                ApiManager.getInstance().join("KAKAO", LoginManager.getInstance().getKakaoKey()
                        , new CallbackListener<UserBody>() {
                            @Override
                            public void callback(UserBody userBody) {
                                Log.d(TAG, userBody.getToken());
                                Constants.token = userBody.getToken();
                                GreenCloudPreferences.setToken(getContext(), Constants.token);
                            }

                            @Override
                            public void failed(String msg) {
                                Log.d(TAG, msg);
                            }

                            @Override
                            public void startApi() {

                            }

                            @Override
                            public void endApi() {

                            }
                        });

        });
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
        else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        LoginManager.getInstance().onActivityResult(getContext(), requestCode, resultCode, data);
    }
}

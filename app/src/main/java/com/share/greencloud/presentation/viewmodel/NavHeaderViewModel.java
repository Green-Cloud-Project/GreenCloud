package com.share.greencloud.presentation.viewmodel;

import android.widget.ImageView;

import androidx.databinding.BaseObservable;
import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.share.greencloud.R;
import com.share.greencloud.domain.model.User;

public class NavHeaderViewModel extends BaseObservable {
    private User user;
    private boolean check_login;

    private MutableLiveData<Boolean> hideSearchMenu = new MutableLiveData<>();

    public NavHeaderViewModel(User newUser) {
        user = newUser;
    }

    public MutableLiveData<Boolean> getHideSearchMenu() {
        return hideSearchMenu;
    }

    public void hideSearchMenu() {
        hideSearchMenu.setValue(true);
    }

    public void showSearchMenu() {
        hideSearchMenu.setValue(false);
    }

    public String getUserName() {
        return user.getName();
    }

    public String getUserProfileImage() {
        return user.getImg_url();
    }

    @BindingAdapter("profileImageUrl")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).placeholder(R.drawable.um_green).circleCrop().into(imageView);
    }
}

package com.share.greencloud.domain.model;

import java.util.List;

public class User {
    private String name;
    private String img_url;
    private List<RentalOffice> favorite;

    public User(String name, String img_url) {
        this.name = name;
        this.img_url = img_url;
    }

    public String getName() {
        return name;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}

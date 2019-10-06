package com.share.greencloud.domain.model;

/**
 * Created by Belal on 10/18/2017.
 */


public class News {

    private int    image;
    private String title;
    private String desc;
    private String from;
    private String url;

    public News( int image, String title, String desc, String from,String url) {
        this.title = title;
        this.desc  = desc;
        this.image = image;
        this.from  = from;
        this.url   = url;
    }

    public int getImage() {
        return image;
    }
    public String getTitle() {
        return title;
    }
    public String getDesc() {
        return desc;
    }
    public String getFrom() {
        return from;
    }
    public String getUrl() {
        return url;
    }

}


package com.share.greencloud.domain.model;

public class News {

    private String imageUrl;
    private String title;
    private String desc;
    private String from;
    private String url;

    public News( String imageUrl, String title, String desc, String from,String url) {
        this.title = title;
        this.desc  = desc;
        this.imageUrl = imageUrl;
        this.from  = from;
        this.url   = url;
    }

    public String getImage() {
        return imageUrl;
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


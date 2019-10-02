package com.share.greencloud.domain.listener;

public class BookingRestResponse<RespModel extends Object> {
    private int response;
    private RespModel data;

    public int getResponse() {
        return response;
    }

    public RespModel getData() {
        return data;
    }
}
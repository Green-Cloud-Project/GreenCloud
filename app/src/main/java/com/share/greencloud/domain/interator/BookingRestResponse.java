/*
 * Copyright (c) 2019. GreenCloud All rights reserved.
 */

package com.share.greencloud.domain.interator;

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
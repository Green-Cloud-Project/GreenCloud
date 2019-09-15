package com.share.greencloud.api;

public class BookingRestResponse<RespModel extends Object> {
    private int result;
    private int errorCode;
    private String errorMessage;
    private RespModel model;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public RespModel getModel() {
        return model;
    }

    public void setModel(RespModel model) {
        this.model = model;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
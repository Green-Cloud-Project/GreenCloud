package com.share.greencloud.domain.listener;

public abstract class CallbackListener<T extends Object> {
    public abstract void callback(T result);

    public abstract void failed(String msg);

    public abstract void startApi();

    public abstract void endApi();
}

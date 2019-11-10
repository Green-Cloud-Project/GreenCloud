package com.share.greencloud.utils;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class RxBus {

    private static RxBus mInstace;
    private BehaviorSubject<Object> mSubject;

    private RxBus() {

        mSubject = BehaviorSubject.create();
    }


    public static RxBus getInstance() {
        if (mInstace == null) {
            mInstace = new RxBus();
        }
        return mInstace;
    }

    public void sendBus(Object object) {
        mSubject.onNext(object);
    }

    public Observable<Object> getBust() {
        return mSubject;
    }
}

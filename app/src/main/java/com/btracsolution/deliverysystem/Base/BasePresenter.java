package com.btracsolution.deliverysystem.Base;

/**
 * Created by mahmudul.hasan on 1/2/2018.
 */

public interface BasePresenter<V> {
    void attach(V view);
    void detach();
}

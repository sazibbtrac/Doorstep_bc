package com.btracsolution.deliverysystem.Base;

/**
 * Created by mahmudul.hasan on 2/7/2018.
 */

public interface BaseServerListener {
    void onServerSuccessOrFailure(boolean isSuccess, Object object, String message);
}

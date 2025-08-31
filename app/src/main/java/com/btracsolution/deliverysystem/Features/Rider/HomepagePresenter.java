package com.btracsolution.deliverysystem.Features.Rider;

import android.app.Activity;

import com.btracsolution.deliverysystem.Base.BasePresenter;

/**
 * Created by mahmudul.hasan on 12/27/2017.
 */


interface HomepagePresenter extends BasePresenter<Activity> {
    void load();

    void loadMore();

    void queryChanged(String query);

}

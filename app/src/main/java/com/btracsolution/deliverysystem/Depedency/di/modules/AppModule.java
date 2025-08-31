package com.btracsolution.deliverysystem.Depedency.di.modules;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.btracsolution.deliverysystem.Depedency.di.myAnnotation.HomeContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mahmudul.hasan on 12/31/2017.
 */

@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

}

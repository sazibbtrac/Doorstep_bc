package com.btracsolution.deliverysystem.Depedency.di.components;

import android.content.SharedPreferences;

import com.btracsolution.deliverysystem.Depedency.di.modules.AppModule;
import com.btracsolution.deliverysystem.Depedency.di.modules.NetModule;
import com.btracsolution.deliverysystem.RoomDB.DaoInterfaces.CartItemDao;
import com.btracsolution.deliverysystem.RoomDB.DatabaseInstance;
import com.btracsolution.deliverysystem.Utility.ProgressDialogOwn;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.Utility.Validation;
import com.btracsolution.deliverysystem.network.api.RetroHubApiInterface;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
/*

*
 * Created by mahmudul.hasan on 1/1/2018.


*/

@Singleton
@Component(modules = {AppModule.class, NetModule.class})
public interface NetComponent {
    // remove injection methods if downstream modules will perform injection

    // downstream components need these exposed
    // the method name does not matter, only the return type
//    DatabaseInstance databaseinstance();
////
//    CartItemDao cartItemDao();

    Retrofit retrofit();

    OkHttpClient okHttpClient();

    SharedPreferences sharedPreferences();

    SharedData sharedData();

    RetroHubApiInterface retroFitApi();

    Validation getvalidation();

    ProgressDialogOwn getProgressDialog();

}

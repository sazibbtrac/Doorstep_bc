package com.btracsolution.deliverysystem.Features.Rider.LocationBackground;

import android.content.Context;
import android.util.Log;

import com.btracsolution.deliverysystem.Base.BaseServerListener;
import com.btracsolution.deliverysystem.DeliveryApp;
import com.btracsolution.deliverysystem.Model.CommonDataResponse;
import com.btracsolution.deliverysystem.Utility.SharedData;
import com.btracsolution.deliverysystem.network.api.RetroHubApiInterface;
import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by mahmudul.hasan on 2/27/2018.
 */

public class BackgroundLocationUpdateModel {
    Context context;
    BaseServerListener baseServerListener;

    @Inject
    RetroHubApiInterface retroHubApiInterface;
    @Inject
    SharedData sharedData;


    public BackgroundLocationUpdateModel(Context context, BaseServerListener baseServerListener) {
        ((DeliveryApp) context.getApplicationContext()).getDoNetworkComponent().inject(this);
        this.context = context;
        this.baseServerListener = baseServerListener;
    }

    public void updateMyCurrentLocation(LatLng latLng) {
        if (retroHubApiInterface != null && sharedData != null) {
            Log.w("LocationCheck","Get Locations"+ sharedData.getMyData().getData().getApiToken()+": "+sharedData.getMyData().getData().getUserId()
            +": "+latLng.latitude+": "+latLng.longitude);
            Call<CommonDataResponse> orderDetailsModelCall = retroHubApiInterface.updateLocation(
                    sharedData.getMyData().getData().getApiToken(),
                    sharedData.getMyData().getData().getUserId(),
                    latLng.latitude,
                    latLng.longitude,
                    "location-name");


            updateFirebaseLocationInsect(latLng, sharedData.getMyData().getData().getUserId()
                    , sharedData.getMyData().getData().getFullName(), sharedData.getMyData().getData().getMobileNo());



         /*   if (sharedData.getLastUdpateTime() == null) {
                sharedData.setDate();
            } else {
                if (LocationCalculation.isPermitToUpdateLocaction(sharedData.getLastUdpateTime())) {
                    sharedData.setDate();
                } else {
                    return;
                }
            }*/

            orderDetailsModelCall.enqueue(new Callback<CommonDataResponse>() {
                @Override
                public void onResponse(Call<CommonDataResponse> call, Response<CommonDataResponse> response) {
                    try {
                        if (response.isSuccessful()) {

                            Log.d("LcoationUpdate", "Sucess"+response.body().toString());
                            baseServerListener.onServerSuccessOrFailure(true, response.body(), response.body().getMessage());

                        } else {
                            if (response.body().getMessage()!=null) {
                                Log.d("LcoationUpdate", "Failed");
                                baseServerListener.onServerSuccessOrFailure(false, response.body(), response.body().getMessage());
                            }

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<CommonDataResponse> call, Throwable t) {
                    Log.d("TokenUpdate", "Failed");
                    if (t != null)
                        baseServerListener.onServerSuccessOrFailure(false, null, t.getMessage());
                    else
                        baseServerListener.onServerSuccessOrFailure(false, null, "Failed to update location");


                }
            });


        } else {
            baseServerListener.onServerSuccessOrFailure(false, null, "not_logged_in");

        }
    }

    public void updateFirebaseLocationInsect(LatLng latLng, String userid, String username, String phoneNumber) {
        Calendar currnetDateTime = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
        String currentTime = df.format(currnetDateTime.getTime());

        UpdateFirebaseLocation updateFirebaseLocation = new UpdateFirebaseLocation(context);
        updateFirebaseLocation.updateLocaiton(userid, new UserLocation(Double.toString(latLng.latitude), Double.toString(latLng.longitude), username, currentTime, phoneNumber));
    }
}

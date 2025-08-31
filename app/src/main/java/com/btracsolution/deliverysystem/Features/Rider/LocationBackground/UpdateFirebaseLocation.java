package com.btracsolution.deliverysystem.Features.Rider.LocationBackground;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by mahmudul.hasan on 10/10/2017.
 */

public class UpdateFirebaseLocation {

    Context context;

    public UpdateFirebaseLocation(Context context) {
        this.context = context;
    }

    public void updateLocaiton(String userName, UserLocation userLocation) {
        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference();
        DatabaseReference usersRef = myRef.child("live-location-info").child(userName);
        usersRef.setValue(userLocation);
    }
}

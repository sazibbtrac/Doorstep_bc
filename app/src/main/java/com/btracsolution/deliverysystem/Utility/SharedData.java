package com.btracsolution.deliverysystem.Utility;

import android.content.Context;
import android.content.SharedPreferences;

import com.btracsolution.deliverysystem.Model.CartItem;
import com.btracsolution.deliverysystem.Model.LoginModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by mahmudul.hasan on 1/2/2018.
 */

public class SharedData {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor edit;

    public SharedData(Context context) {
        sharedPreferences = context.getSharedPreferences("obscureShare", Context.MODE_PRIVATE);
        edit = sharedPreferences.edit();
    }

    public LoginModel getMyData() {
        if (sharedPreferences.getString("mydata", "none").contentEquals("none")) {
            return null;
        } else
            return new Gson().fromJson(sharedPreferences.getString("mydata", "none"), LoginModel.class);

    }

    public void setMyData(String data) {
        edit.putString("mydata", data);
        edit.commit();
    }

    public void unsetMyData() {
        edit.putString("mydata",  "none");
        edit.commit();
    }

    public void setDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


        System.out.println(dateFormat.format(System.currentTimeMillis())); //2016/11/16 12:08:43
        edit.putString("lastDateTime", dateFormat.format(System.currentTimeMillis()));
        edit.apply();
    }

    public String getLastUdpateTime() {
        return sharedPreferences.getString("lastDateTime", null);
    }

    public boolean isPermitToShowMessage(String message_code) {

        if (sharedPreferences.getBoolean(message_code, false)) {

            return false;

        } else {
            edit.putBoolean(message_code, true);
            edit.commit();
            return true;
        }
    }

    public void saveLastMessage(String message) {
        edit.putString("c_last_language", message);
        edit.commit();
    }
    public String getLastMesage() {
        return sharedPreferences.getString("c_last_language",null);

    }


    //Cart Item
    public ArrayList<CartItem> getCartData() {
        if (sharedPreferences.getString("cartData", "none").contentEquals("none")) {
            return null;
        } else {
            ArrayList<CartItem> cartList= (ArrayList<CartItem>) new Gson().fromJson(sharedPreferences.getString("cartData", "none"),
                    new TypeToken<ArrayList<CartItem>>() {}.getType());
            return cartList;
        }

    }

    public void setCartData(String data) {
        edit.putString("cartData", data);
        edit.commit();
    }

    public void unsetCartData() {
        edit.putString("cartData",  "none");
        edit.commit();
    }

}

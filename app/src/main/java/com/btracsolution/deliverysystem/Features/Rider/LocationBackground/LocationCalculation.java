package com.btracsolution.deliverysystem.Features.Rider.LocationBackground;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by mahmudul.hasan on 10/2/2017.
 */

public class LocationCalculation {

    public static boolean isPermitToUpdateLocaction(String DateTime) {
        long min = getMin(DateTime);
        Log.d("min_diff_current_time", getMin(DateTime) + " " + DateTime);
        if (min >= 1) {
            return true;
        } else
            return false;


    }

    public static long getMin(String dateTime) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date past = format.parse(dateTime);
            Date now = new Date();

         /*   System.out.println(TimeUnit.MILLISECONDS.toMillis(now.getTime() - past.getTime()) + " milliseconds ago");
            System.out.println(TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime()) + " minutes ago");
            System.out.println(TimeUnit.MILLISECONDS.toHours(now.getTime() - past.getTime()) + " hours ago");
            System.out.println(TimeUnit.MILLISECONDS.toDays(now.getTime() - past.getTime()) + " days ago");*/
            return TimeUnit.MILLISECONDS.toMinutes(now.getTime() - past.getTime());
        } catch (Exception j) {
            j.printStackTrace();
        }
        return 0;
    }
}

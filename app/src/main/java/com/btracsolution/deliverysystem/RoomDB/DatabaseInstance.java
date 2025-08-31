package com.btracsolution.deliverysystem.RoomDB;


import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.btracsolution.deliverysystem.RoomDB.DaoInterfaces.CartItemDao;


//@Database(entities = {CartItemEntity.class}, version = 1)
public abstract class DatabaseInstance extends RoomDatabase {


    private static DatabaseInstance db;

    private static String DB_NAME = "doorstep_room_db";

    public static DatabaseInstance getInstance(Context application) {
        if (null == db) {
            db = Room.databaseBuilder(application, DatabaseInstance.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return db;
    }

    public abstract CartItemDao cartItemDao();
}

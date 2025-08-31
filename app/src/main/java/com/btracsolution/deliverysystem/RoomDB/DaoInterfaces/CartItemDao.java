package com.btracsolution.deliverysystem.RoomDB.DaoInterfaces;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.btracsolution.deliverysystem.Model.CartItem;
import com.btracsolution.deliverysystem.RoomDB.CartItemEntity;

import java.util.List;

@Dao
public interface CartItemDao {

    @Query("SELECT * FROM CartItemEntity")
    List<CartItemEntity> getAllLocation();

    @Insert
    void insert(List<CartItemEntity> locationModel);

    @Insert
    void singleInsert(CartItemEntity locationModel);

    @Delete
    void delete(CartItemEntity locationModel);

    @Update
    void update(CartItemEntity locationModel);

    @Query("SELECT * FROM CartItemEntity WHERE id = :id")
    List<CartItemEntity> fetchlocationModelById(String id);


    @Query("DELETE FROM CartItemEntity")
    void clear();
}

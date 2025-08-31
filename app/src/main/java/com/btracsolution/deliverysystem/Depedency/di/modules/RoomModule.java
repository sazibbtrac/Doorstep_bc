package com.btracsolution.deliverysystem.Depedency.di.modules;

import android.app.Application;

import androidx.room.Room;

import com.btracsolution.deliverysystem.RoomDB.DaoInterfaces.CartItemDao;
import com.btracsolution.deliverysystem.RoomDB.DatabaseInstance;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private DatabaseInstance demoDatabase;

    public RoomModule(Application mApplication) {
        demoDatabase = Room.databaseBuilder(mApplication, DatabaseInstance.class, "demo-db").fallbackToDestructiveMigration().build();
    }

    @Singleton
    @Provides
    DatabaseInstance providesRoomDatabase() {
        return demoDatabase;
    }

    @Singleton
    @Provides
    CartItemDao providesProductDao(DatabaseInstance demoDatabase) {
        return demoDatabase.cartItemDao();
    }
//
//    @Singleton
//    @Provides
//    ProductRepository productRepository(ProductDao productDao) {
//        return new ProductDataSource(productDao);
//    }

}

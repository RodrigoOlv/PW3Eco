package com.example.ecoapp.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.ecoapp.entity.Product;

@Database(entities = {Product.class}, version = 2, exportSchema = true)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase appDatabase;

    public abstract ProductDAO createProductDAO();

    public static AppDatabase getInstance(Context context) {
        if(appDatabase == null) {
            appDatabase = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "driver_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return appDatabase;
    }
}

package com.gunawan.webaddressnotes.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.gunawan.webaddressnotes.model.WebAddress;

@Database(entities = {WebAddress.class}, version = 1)
public abstract class WebAddressDatabase extends RoomDatabase {
    public abstract WebAddressDAO webAddressDAO();
    private static WebAddressDatabase instance;

    public static WebAddressDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (WebAddressDatabase.class) {
                if (instance == null) {
                    instance =
                            Room.databaseBuilder(context.getApplicationContext(),
                                            WebAddressDatabase.class, "web_address_db")
                                    .fallbackToDestructiveMigration()
                                    .build();
                }
            }
        }

        return instance;
    }

    private static RoomDatabase.Callback roomCallback
            = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
        }
    };

}
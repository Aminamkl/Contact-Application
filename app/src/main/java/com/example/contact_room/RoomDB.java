package com.example.contact_room;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Contact.class}, version = 1,exportSchema = false)
public abstract class RoomDB extends RoomDatabase {
    public static RoomDB database;
    public static String DATADASE_NAME="Contact_db";
    public synchronized static RoomDB getInstance(Context context){
        if(database==null){
            database= Room.databaseBuilder(context.getApplicationContext(),RoomDB.class,DATADASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration().build();
        }
        return database;
    }
    public abstract MainDao mainDao();
}

package com.example.afe.task.model;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {Task.class, User.class, UserTaskJoin.class },version = 1,exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;
    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,"task_database").allowMainThreadQueries().build();
        }
        return instance;
    }


    public abstract TaskDao taskDao();
    public abstract UserDao userDao();
    public abstract UserTaskJoinDao userTaskJoinDao();



}

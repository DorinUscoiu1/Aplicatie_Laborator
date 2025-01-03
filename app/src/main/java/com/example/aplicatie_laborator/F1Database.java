package com.example.aplicatie_laborator;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.aplicatie_laborator.UserScore;

@Database(entities = {UserScore.class}, version = 1, exportSchema = false)
public abstract class F1Database extends RoomDatabase {

    private static F1Database INSTANCE;

    public abstract UserScoreDao userScoreDao();

    public static synchronized F1Database getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            F1Database.class, "f1_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }
}
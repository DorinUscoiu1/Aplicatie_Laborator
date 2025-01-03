package com.example.aplicatie_laborator;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Team.class}, version = 1)
public abstract class TeamDatabase extends RoomDatabase {
    public abstract TeamDao teamDao();
}
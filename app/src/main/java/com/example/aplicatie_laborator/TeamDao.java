package com.example.aplicatie_laborator;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TeamDao {

    @Insert
    void insert(Team team);

    @Insert
    void insert(List<Team> teams);

    @Query("SELECT * FROM teams")
    List<Team> getAllTeams();

    @Query("DELETE FROM teams")
    void deleteAll();
}

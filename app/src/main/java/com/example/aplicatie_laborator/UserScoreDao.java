package com.example.aplicatie_laborator;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.aplicatie_laborator.UserScore;

import java.util.List;

@Dao
public interface UserScoreDao {

    @Insert
    void insert(UserScore userScore);

    @Query("SELECT * FROM user_scores ORDER BY score DESC")
    List<UserScore> getAllScoresSorted();
}
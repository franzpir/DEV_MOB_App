package com.example.dev_mob_houet_piron.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dev_mob_houet_piron.model.Badge;

import java.util.List;

@Dao
public interface BadgeDao {

    @Query("SELECT * FROM badge")
    LiveData<List<Badge>> getBadges();

    @Query("SELECT * FROM badge where id = (:id)")
    LiveData<Badge> getBadge(String id);

    @Insert
    void insert(Badge badge);

}

package com.example.dev_mob_houet_piron.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.example.dev_mob_houet_piron.model.Place;

import java.util.List;

@Dao
public interface PlaceDao {

    @Query("SELECT * FROM place")
    LiveData<List<Place>> getPlaces();

    @Query("SELECT * FROM place WHERE id IN(:ids)")
    LiveData<List<Place>> getPlaces(List<String> ids);

    @Query("SELECT * FROM place WHERE id = (:id)")
    LiveData<Place> getPlace(String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Place place);

    @Update
    void update(Place place);

    @Query("DELETE FROM place WHERE name = (:name) AND description = (:description)")
    void clearForTests(String name, String description);

    @Query("SELECT * FROM place WHERE name = (:name) AND description = (:description)")
    LiveData<List<Place>> getPlacesForTest(String name, String description);
}

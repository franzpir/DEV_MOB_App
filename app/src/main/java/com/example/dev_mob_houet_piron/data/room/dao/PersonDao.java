package com.example.dev_mob_houet_piron.data.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.example.dev_mob_houet_piron.model.Person;

@Dao
public interface PersonDao {

    @Query("SELECT * FROM person where id = (:id)")
    LiveData<Person> getPerson(String id);

    @Insert
    void insert(Person person);

    @Update
    void update(Person person);

    @Query("SELECT EXISTS(SELECT * FROM person WHERE id = :id)")
    boolean exists(String id);
}

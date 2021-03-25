package com.example.dev_mob_houet_piron.data.interfaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.dev_mob_houet_piron.model.Person;
import com.example.dev_mob_houet_piron.model.Place;

import java.util.List;

public interface IPersonRepository {

    LiveData<Person> getPerson(String id);

    void updatePerson(final Person person);

    void insertPerson(final Person person);

    void addFoundCache(AppCompatActivity context, String personId, String cacheId);

}

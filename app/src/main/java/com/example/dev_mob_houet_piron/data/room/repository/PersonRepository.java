package com.example.dev_mob_houet_piron.data.room.repository;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.dev_mob_houet_piron.data.interfaces.IPersonRepository;
import com.example.dev_mob_houet_piron.data.interfaces.IPlaceRepository;
import com.example.dev_mob_houet_piron.data.room.GeoCachingDatabase;
import com.example.dev_mob_houet_piron.data.room.dao.PersonDao;
import com.example.dev_mob_houet_piron.model.Person;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PersonRepository implements IPersonRepository {

    private static PersonRepository instance;

    private final PersonDao personDao = GeoCachingDatabase.getInstance().personDao();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    private PersonRepository() {}

    @Override
    public LiveData<Person> getPerson(String id) {
        return personDao.getPerson(id);
    }

    @Override
    public void updatePerson(final Person person) {
        executor.execute(() -> personDao.update(person));
    }

    @Override
    public void insertPerson(final Person person) {
        executor.execute(() -> personDao.insert(person));
    }

    public static PersonRepository getInstance() {
        if(instance == null)
            instance = new PersonRepository();
        return instance;
    }

    @Override
    public void addFoundCache(AppCompatActivity context, String personId, String cacheId){
        LiveData<Person> person = personDao.getPerson(personId);
        person.observe(context, new Observer<Person>() {
            @Override
            public void onChanged(@Nullable Person givenPerson) {
                if(givenPerson != null) {
                    givenPerson.addFoundCache(cacheId);
                    updatePerson(givenPerson);
                    person.removeObserver(this);
                }
            }
        });
    }
}

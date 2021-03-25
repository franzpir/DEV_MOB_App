package com.example.dev_mob_houet_piron.data.room.repository;

import androidx.lifecycle.LiveData;

import com.example.dev_mob_houet_piron.data.interfaces.IBadgeRepository;
import com.example.dev_mob_houet_piron.data.interfaces.IPlaceRepository;
import com.example.dev_mob_houet_piron.data.room.GeoCachingDatabase;
import com.example.dev_mob_houet_piron.data.room.dao.PlaceDao;
import com.example.dev_mob_houet_piron.model.Place;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalPlaceRepository implements IPlaceRepository {

    private static LocalPlaceRepository instance;

    private final PlaceDao placeDao = GeoCachingDatabase.getInstance().placeDao();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private List<Place> places;

    private LocalPlaceRepository() {
        getPlaces().observeForever(places -> this.places = places);
    }

    public LiveData<List<Place>> getPlaces() {
        return placeDao.getPlaces();
    }

    public LiveData<List<Place>> getPlaces(List<String> places) {
        return placeDao.getPlaces(places);
    }

    public List<Place> getPlacesSync() {
        return this.places;
    }

    public LiveData<Place> getPlace(String id) {
        return placeDao.getPlace(id);
    }

    public void add(final Place place) {
        executor.execute(() -> placeDao.insert(place));
    }

    public void addAll(final List<Place> places) {
        for(Place place : places){
            add(place);
        }
    }

    public static LocalPlaceRepository getInstance() {
        if(instance == null)
            instance = new LocalPlaceRepository();
        return instance;
    }

    //------ ONLY FOR TESTING PURPOSE ------

    public LiveData<List<Place>> getPlacesForTest(String name, String description) {
        return placeDao.getPlacesForTest(name, description);
    }

    public void clearForTests(String testName, String testDescription) {
        executor.execute(() -> placeDao.clearForTests(testName, testDescription));
    }
}

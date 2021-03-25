package com.example.dev_mob_houet_piron.data.interfaces;

import androidx.lifecycle.LiveData;

import com.example.dev_mob_houet_piron.model.Badge;
import com.example.dev_mob_houet_piron.model.Place;

import java.util.List;

public interface IPlaceRepository {

    LiveData<List<Place>> getPlaces();

    LiveData<List<Place>> getPlaces(List<String> places);

    List<Place> getPlacesSync();

    LiveData<Place> getPlace(String id);

    void add(final Place place);

    void addAll(final List<Place> places);
}

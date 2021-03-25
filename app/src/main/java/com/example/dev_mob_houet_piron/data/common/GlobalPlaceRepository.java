package com.example.dev_mob_houet_piron.data.common;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.dev_mob_houet_piron.data.firebase.FirebasePlaceRepository;
import com.example.dev_mob_houet_piron.data.room.repository.LocalPlaceRepository;
import com.example.dev_mob_houet_piron.model.Place;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GlobalPlaceRepository {

    private static GlobalPlaceRepository instance;

    /**
     * Retourne toutes les places (Firebase et Room) sous forme de set observable d'objets "Place"
     * @return Set observable contenant toutes les places
     */
    public LiveData<Set<Place>> getPlaces() {
        MutableLiveData<Set<Place>> observablePlaces = new MutableLiveData<>();
        observablePlaces.setValue(new HashSet<>());
        initFireBasePlaceObserver(observablePlaces);
        initLocalPlaceObserver(observablePlaces);
        return observablePlaces;
    }

    /**
     * Retourne les objets "Place" ayant les ids précisés en paramètre contenus dans Room et Firebase sous forme de set observable
     * @param placesIds les ids des places qu'on souhaite obtenir
     * @return Set observable contenant les places ayant les ids indiqués en paramètre
     */
    public LiveData<Set<Place>> getPlaces(List<String> placesIds) {
        if(placesIds == null || placesIds.size() == 0)
            return new MutableLiveData<>();
        MutableLiveData<Set<Place>> observablePlacesForIds = new MutableLiveData<>();
        observablePlacesForIds.setValue(new HashSet<>());
        initFireBasePlaceObserver(observablePlacesForIds, placesIds);
        initLocalPlaceObserver(observablePlacesForIds, placesIds);
        return observablePlacesForIds;
    }

    /**
     * Retourne un objet observable de type "place" ayant l'id précisé en paramètre,
     * Si l'id commence par "FB", alors l'objet sera cherché dans Firebase, Room sinon.
     * @param id l'id de l'objet place que l'on souhaite charger
     * @return un objet place observable
     */
    public LiveData<Place> getPlace(String id) {
        if(id.startsWith("FB"))
            return FirebasePlaceRepository.getInstance().getPlace(id);
        else
            return LocalPlaceRepository.getInstance().getPlace(id);
    }

    /**
     * Retourne l'instance du GlobalPlaceRepository
     * @return l'instance du GlobalPlaceRepository
     */
    public static GlobalPlaceRepository getInstance() {
        if(instance == null)
            instance = new GlobalPlaceRepository();
        return instance;
    }

    private void initFireBasePlaceObserver(MutableLiveData<Set<Place>> observablePlaces) {
        FirebasePlaceRepository.getInstance().getPlaces().observeForever(place -> {
            Set<Place> places = observablePlaces.getValue();
            places.addAll(place);
            observablePlaces.postValue(places);
        });
    }

    private void initLocalPlaceObserver(MutableLiveData<Set<Place>> observablePlaces) {
        LocalPlaceRepository.getInstance().getPlaces().observeForever(place -> {
            Set<Place> places = observablePlaces.getValue();
            places.addAll(place);
            observablePlaces.postValue(places);
        });
    }

    private void initFireBasePlaceObserver(MutableLiveData<Set<Place>> observablePlacesForIds, List<String> placesIds) {
        FirebasePlaceRepository.getInstance().getPlaces(placesIds).observeForever(place -> {
            Set<Place> places = observablePlacesForIds.getValue();
            places.addAll(place);
            observablePlacesForIds.postValue(places);
        });
    }

    private void initLocalPlaceObserver(MutableLiveData<Set<Place>> observablePlacesForIds, List<String> placesIds) {
        LocalPlaceRepository.getInstance().getPlaces(placesIds).observeForever(place -> {
            Set<Place> places = observablePlacesForIds.getValue();
            places.addAll(place);
            observablePlacesForIds.postValue(places);
        });
    }
}

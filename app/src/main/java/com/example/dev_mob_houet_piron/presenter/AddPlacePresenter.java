package com.example.dev_mob_houet_piron.presenter;

import com.example.dev_mob_houet_piron.data.interfaces.IPlaceRepository;
import com.example.dev_mob_houet_piron.data.room.repository.LocalPlaceRepository;
import com.example.dev_mob_houet_piron.model.Place;

public class AddPlacePresenter {

    IPlaceRepository placeRepository;

    public AddPlacePresenter(){
        this.placeRepository = LocalPlaceRepository.getInstance();
    }

    public AddPlacePresenter(IPlaceRepository placeRepository){
        this.placeRepository = placeRepository;
    }

    public void addPlace(double longitude, double latitude, String name, String description) {
        Place place = new Place(longitude, latitude, name, description, "unofficial");
        placeRepository.add(place);
    }
}

package com.example.dev_mob_houet_piron.presenter;

import com.example.dev_mob_houet_piron.data.common.GlobalPlaceRepository;
import com.example.dev_mob_houet_piron.data.room.repository.PersonRepository;
import com.example.dev_mob_houet_piron.model.Place;
import com.example.dev_mob_houet_piron.view.interfaces.IDisplayProfileInfo;
import com.example.dev_mob_houet_piron.view.interfaces.IPlaceItem;

import java.util.ArrayList;
import java.util.List;

public class ProfilePresenter implements IProvidePlaceListData {

    private List<Place> loadedPlaces = new ArrayList<>();
    private IDisplayProfileInfo profileDisplayer;

    public ProfilePresenter(IDisplayProfileInfo profileDisplayer) {
        this.profileDisplayer = profileDisplayer;
    }

    public void loadPerson() {
        PersonRepository.getInstance().getPerson("user1").observeForever((person) -> {
            if (person != null){
                loadFoundPlaces(person.getFoundCaches());
                this.profileDisplayer.showProfile(person.getName(), person.getFoundCaches().size(), person.getRegisterDate());
            }
        });
    }

    @Override
    public void onBindPlaceRowViewAtPosition(IPlaceItem cacheItem, int position) {
        GlobalPlaceRepository.getInstance().getPlace(loadedPlaces.get(position).getId()).observeForever((place) -> {
            cacheItem.setTitle(place.getName());
            cacheItem.setId(place.getId());
        });
    }

    @Override
    public int getPlaceListSize() {
        return loadedPlaces.size();
    }

    private void loadFoundPlaces(List<String> foundCaches) {
        GlobalPlaceRepository.getInstance().getPlaces(foundCaches).observeForever((places) -> {
            this.loadedPlaces = new ArrayList<>(places);
            profileDisplayer.loadView();
        });
    }
}

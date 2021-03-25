package com.example.dev_mob_houet_piron.presenter;

import com.example.dev_mob_houet_piron.data.common.GlobalPlaceRepository;
import com.example.dev_mob_houet_piron.model.Place;
import com.example.dev_mob_houet_piron.view.interfaces.IDisplayPlaceList;
import com.example.dev_mob_houet_piron.view.interfaces.IPlaceItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PlaceListPresenter implements IProvidePlaceListData {

    private IDisplayPlaceList placeDisplayer;

    private Set<String> selectedPlaces = new HashSet<>();
    private List<Place> places;

    public PlaceListPresenter(IDisplayPlaceList placeDisplayer) {
        this.placeDisplayer = placeDisplayer;
    }

    public void loadPlaces() {
        GlobalPlaceRepository.getInstance().getPlaces().observeForever(places -> {
            PlaceListPresenter.this.places = new ArrayList<>(places);
            placeDisplayer.loadView();
        });
    }

    public void addSelectedPlace(String id) {
        selectedPlaces.add(id);
    }

    public void removeSelectedPlace(String id) {
        selectedPlaces.remove(id);
    }

    public List<String> getSelectedPlaces() {
        return new ArrayList<>(selectedPlaces);
    }

    @Override
    public void onBindPlaceRowViewAtPosition(IPlaceItem placeItem, int position) {
        initPlaceObserver(placeItem, position);
    }

    @Override
    public int getPlaceListSize() {
        return places.size();
    }

    private void initPlaceObserver(IPlaceItem placeItem, int position) {
        GlobalPlaceRepository.getInstance().getPlace(places.get(position).getId()).observeForever(place -> {
            placeItem.setTitle(place.getName());
            placeItem.setId(place.getId());
        });
    }
}

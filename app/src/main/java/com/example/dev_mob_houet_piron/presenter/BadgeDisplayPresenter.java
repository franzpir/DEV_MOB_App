package com.example.dev_mob_houet_piron.presenter;

import android.util.Log;

import com.example.dev_mob_houet_piron.data.common.GlobalPlaceRepository;
import com.example.dev_mob_houet_piron.data.firebase.FirebaseBadgeRepository;
import com.example.dev_mob_houet_piron.data.room.repository.LocalBadgeRepository;
import com.example.dev_mob_houet_piron.model.Badge;
import com.example.dev_mob_houet_piron.model.Place;
import com.example.dev_mob_houet_piron.view.interfaces.IDisplayBadge;
import com.example.dev_mob_houet_piron.view.interfaces.IPlaceItem;

import java.util.ArrayList;
import java.util.List;

public class BadgeDisplayPresenter implements IProvidePlaceListData {

    private IDisplayBadge badgeDisplayer;
    private List<Place> places;
    private Badge badge;

    public BadgeDisplayPresenter(IDisplayBadge badgeDisplayer, String id) {
        this.badgeDisplayer = badgeDisplayer;
        loadBadge(id);
    }

    public void loadBadge(String id) {
        if(id.startsWith("FB"))
            loadFireBaseBadge(id);
        else
            loadLocalBadge(id);
    }

    private void loadBadgePlaces() {
        GlobalPlaceRepository.getInstance().getPlaces(this.badge.getCachesIds()).observeForever(places -> {
            BadgeDisplayPresenter.this.places = new ArrayList<>(places);
            badgeDisplayer.loadView();
        });
    }

    private void loadFireBaseBadge(String id) {
        FirebaseBadgeRepository.getInstance().getBadge(id).observeForever(badge -> {
            this.badge = badge;
            loadBadgePlaces();
            badgeDisplayer.showBadge(badge.getTitle(), badge.getDescription());
        });
    }

    private void loadLocalBadge(String id) {
        LocalBadgeRepository.getInstance().getBadge(id).observeForever(badge -> {
            this.badge = badge;
            loadBadgePlaces();
            badgeDisplayer.showBadge(badge.getTitle(), badge.getDescription());
        });
    }

    @Override
    public void onBindPlaceRowViewAtPosition(IPlaceItem placeItem, int position) {
        initPlaceObserver(placeItem, position);
    }

    private void initPlaceObserver(IPlaceItem placeItem, int position) {
        GlobalPlaceRepository.getInstance().getPlace(places.get(position).getId()).observeForever(place -> {
            placeItem.setTitle(place.getName());
            placeItem.setId(place.getId());
            badgeDisplayer.addMarker(place.getLatitude(), place.getLongitude());
        });
    }

    @Override
    public int getPlaceListSize() {
        return this.places.size();
    }
}

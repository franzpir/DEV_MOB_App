package com.example.dev_mob_houet_piron.presenter;

import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dev_mob_houet_piron.data.firebase.FirebasePlaceRepository;
import com.example.dev_mob_houet_piron.data.room.repository.LocalPlaceRepository;
import com.example.dev_mob_houet_piron.data.room.repository.PersonRepository;
import com.example.dev_mob_houet_piron.model.LocatorUtil;
import com.example.dev_mob_houet_piron.model.Place;
import com.example.dev_mob_houet_piron.view.interfaces.IDisplayCache;
import com.google.android.gms.maps.model.LatLng;

public class CacheDisplayPresenter extends CachePresenter {

    private final IDisplayCache cacheDisplayer;
    private final Context context;
    private Place place;

    public CacheDisplayPresenter(Context context, String cacheId){
        this.cacheDisplayer = (IDisplayCache) context;
        this.context = context;
        loadPlace(cacheId);
    }

    private void setCompletitionAvailability() {
        PersonRepository.getInstance().getPerson("user1").observeForever(person -> {
            if(person.hasFound(place.getId()))
                cacheDisplayer.setCompletitionAvailability(0);
            else if(!LocatorUtil.UserIsAtRange(context, new LatLng(place.getLatitude(), place.getLongitude()), 50))
                cacheDisplayer.setCompletitionAvailability(1);
            else
                cacheDisplayer.setCompletitionAvailability(2);
            });
    }

    @Override
    public void loadPlace(String cacheId) {
        super.loadPlace(cacheId);
    }

    @Override
    public void loadRoomPlace(String cacheId) {
        LocalPlaceRepository.getInstance().getPlace(cacheId).observeForever(place -> {
            CacheDisplayPresenter.this.place = place;
            cacheDisplayer.showCache(place.getName(), place.getDescription(), place.getLatitude(), place.getLongitude(), place.getType());
            setCompletitionAvailability();
        });
    }

    @Override
    public void loadFirebasePlace(String cacheId) {
        FirebasePlaceRepository.getInstance().getPlace(cacheId).observeForever(place -> {
            CacheDisplayPresenter.this.place = place;
            cacheDisplayer.showCache(place.getName(), place.getDescription(), place.getLatitude(), place.getLongitude(), place.getType());
            setCompletitionAvailability();
        });
    }

    public void setPlaceFound() {
        PersonRepository.getInstance().addFoundCache((AppCompatActivity) context, "user1", place.getId());
    }
}

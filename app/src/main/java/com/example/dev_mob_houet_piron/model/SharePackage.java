package com.example.dev_mob_houet_piron.model;

import java.util.List;

public class SharePackage {

    private final List<Place> places;
    private final List<Badge> badges;

    public SharePackage(List<Place> places, List<Badge> badges){
        this.places = places;
        this.badges = badges;
    }

    public List<Place> getPlaces(){
        return this.places;
    }

    public List<Badge> getBadges(){
        return this.badges;
    }
}

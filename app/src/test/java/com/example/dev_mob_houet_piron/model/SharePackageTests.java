package com.example.dev_mob_houet_piron.model;

import com.example.dev_mob_houet_piron.model.Badge;
import com.example.dev_mob_houet_piron.model.Place;
import com.example.dev_mob_houet_piron.model.SharePackage;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SharePackageTests {

    private List<Badge> badges;
    private List<Place> places;

    private SharePackage sharePackage;

    @Before
    public void initialize() {
        badges = new ArrayList<>();
        places = new ArrayList<>();

        badges.add(new Badge("7 merveilles de Liège", "Les plus beaux coins de Liège"));
        badges.add(new Badge("", ""));

        places.add(new Place(5, 6, "Le Perron", "Fontaine", "official"));
        places.add(new Place(5.1, 6.1, "", "", ""));

        sharePackage = new SharePackage(places, badges);
    }

    @Test
    public void testGetPlaces() {
        assertNotNull(sharePackage.getPlaces());
        assertArrayEquals(places.toArray(), sharePackage.getPlaces().toArray());
    }

    @Test
    public void getBadges() {
        assertNotNull(sharePackage.getBadges());
        assertArrayEquals(badges.toArray(), sharePackage.getBadges().toArray());
    }
}

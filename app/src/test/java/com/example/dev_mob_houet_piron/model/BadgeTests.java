package com.example.dev_mob_houet_piron.model;

import com.example.dev_mob_houet_piron.model.Badge;
import com.example.dev_mob_houet_piron.model.Place;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BadgeTests {

    private Badge normalBadge, badgeWithNoContent;
    private List<String> places, emptyPlaces;

    @Before
    public void initializeBadge() {
        normalBadge = new Badge("7 merveilles de Liège", "Les plus beaux coins de Liège");
        badgeWithNoContent = new Badge("", "");

        emptyPlaces = new ArrayList<>();
        places = new ArrayList<>();
        places.add("place1ID");
        places.add("place2ID");

        normalBadge.setCachesIds(places);
        badgeWithNoContent.setCachesIds(emptyPlaces);
    }

    @Test
    public void getBadgeId() {
        assertNotNull(normalBadge.getId());
        assertNotNull(badgeWithNoContent.getId());
        assertEquals(36, normalBadge.getId().length());
        assertEquals(36, badgeWithNoContent.getId().length());

        normalBadge.setId("badgeId");
        assertEquals("badgeId", normalBadge.getId());
    }

    @Test
    public void getBadgeName() {
        assertEquals("7 merveilles de Liège", normalBadge.getTitle());
        assertEquals("", badgeWithNoContent.getTitle());

        normalBadge.setTitle("Les 7 merveilles de Litch");
        assertEquals("Les 7 merveilles de Litch", normalBadge.getTitle());
    }

    @Test
    public void getBadgeDescription() {
        assertEquals("Les plus beaux coins de Liège", normalBadge.getDescription());
        assertEquals("", badgeWithNoContent.getDescription());

        normalBadge.setDescription("Les 7 plus beaux coins de Litch");
        assertEquals("Les 7 plus beaux coins de Litch", normalBadge.getDescription());
    }

    @Test
    public void getListOfFoundedPlaces() {
        assertArrayEquals(places.toArray(), normalBadge.getCachesIds().toArray());
        assertArrayEquals(emptyPlaces.toArray(), badgeWithNoContent.getCachesIds().toArray());
    }

    @Test
    public void getSizeOfFoundedCaches() {
        assertEquals(places.size(), normalBadge.getNbrOfCacheToFind());
        assertEquals(emptyPlaces.size(), badgeWithNoContent.getNbrOfCacheToFind());
    }

}

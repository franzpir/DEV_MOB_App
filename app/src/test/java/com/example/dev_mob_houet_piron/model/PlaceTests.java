package com.example.dev_mob_houet_piron.model;

import com.example.dev_mob_houet_piron.model.Place;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlaceTests {

    private Place normalPlace, placeWithNoContent;

    @Before
    public void initialize() {
        normalPlace = new Place(5, 6, "Le Perron", "Fontaine", "official");
        placeWithNoContent = new Place(5.1, 6.1, "", "", "");
    }

    @Test
    public void testPlacesId() {
        assertNotNull(normalPlace.getId());
        assertNotNull(placeWithNoContent.getId());
        assertEquals(36, normalPlace.getId().length());
        assertEquals(36, placeWithNoContent.getId().length());

        normalPlace.setId("badgeId");
        assertEquals("badgeId", normalPlace.getId());
    }

    @Test
    public void testLatitude() {
        assertNotNull(normalPlace.getLatitude());
        assertNotNull(placeWithNoContent.getLatitude());
        assertEquals(6, normalPlace.getLatitude(), 0.0);
        assertEquals(6.1, placeWithNoContent.getLatitude(), 0.0);

        normalPlace.setLatitude(123);
        assertEquals(123, normalPlace.getLatitude(), 0.0);
    }

    @Test
    public void testLongitude() {
        assertNotNull(normalPlace.getLongitude());
        assertNotNull(placeWithNoContent.getLongitude());
        assertEquals(5, normalPlace.getLongitude(), 0.0);
        assertEquals(5.1, placeWithNoContent.getLongitude(), 0.0);

        normalPlace.setLongitude(123);
        assertEquals(123, normalPlace.getLongitude(), 0.0);
    }

    @Test
    public void testName() {
        assertNotNull(normalPlace.getName());
        assertNotNull(placeWithNoContent.getName());
        assertEquals("Le Perron", normalPlace.getName());
        assertEquals("", placeWithNoContent.getName());

        normalPlace.setName("La Boverie");
        assertEquals("La Boverie", normalPlace.getName());
    }

    @Test
    public void testDescription() {
        assertNotNull(normalPlace.getDescription());
        assertNotNull(placeWithNoContent.getDescription());
        assertEquals("Fontaine", normalPlace.getDescription());
        assertEquals("", placeWithNoContent.getDescription());

        normalPlace.setDescription("Parc");
        assertEquals("Parc", normalPlace.getDescription());
    }

    @Test
    public void testType() {
        assertNotNull(normalPlace.getType());
        assertNotNull(placeWithNoContent.getType());
        assertEquals("official", normalPlace.getType());
        assertEquals("", placeWithNoContent.getType());

        normalPlace.setType("unofficial");
        assertEquals("unofficial", normalPlace.getType());
    }


}

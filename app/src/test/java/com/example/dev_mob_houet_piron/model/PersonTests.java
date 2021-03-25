package com.example.dev_mob_houet_piron.model;

import com.example.dev_mob_houet_piron.model.Person;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class PersonTests {

    private Person normalPerson, personWithNoContent;
    private List<String> places, emptyPlaces;

    @Before
    public void initialize() {
        normalPerson = new Person();
        personWithNoContent = new Person();

        places = new ArrayList<>();
        places.add("place1Id");
        places.add("place2Id");
        emptyPlaces = new ArrayList<>();
    }

    @Test
    public void initializeAndTestPersonId() {
        normalPerson.setId("badgeId");
        personWithNoContent.setId("");

        assertEquals("badgeId", normalPerson.getId());
        assertEquals("", personWithNoContent.getId());
    }

    @Test
    public void initializeAndTestPersonName() {
        normalPerson.setName("Piron");
        personWithNoContent.setName("");

        assertEquals("Piron", normalPerson.getName());
        assertEquals("", personWithNoContent.getName());
    }

    @Test
    public void initializeAndTestPersonForeName() {
        normalPerson.setName("François");
        personWithNoContent.setName("");

        assertEquals("François", normalPerson.getName());
        assertEquals("", personWithNoContent.getName());
    }

    @Test
    public void testPersonFoundedCaches() {
        normalPerson.setFoundCaches(places);
        personWithNoContent.setFoundCaches(emptyPlaces);

        assertArrayEquals(places.toArray(), normalPerson.getFoundCaches().toArray());
        assertArrayEquals(emptyPlaces.toArray(), personWithNoContent.getFoundCaches().toArray());
    }

    @Test
    public void testIfCachesAreFoundedOrNot() {
        normalPerson.setFoundCaches(places);
        personWithNoContent.setFoundCaches(emptyPlaces);

        assertTrue(normalPerson.hasFound("place1Id"));
        assertFalse(normalPerson.hasFound("azerty"));
        assertFalse(personWithNoContent.hasFound("place1Id"));

        normalPerson.addFoundCache("place3Id");
        assertTrue(normalPerson.hasFound("place3Id"));
        assertFalse(normalPerson.hasFound("azerty"));
        assertFalse(personWithNoContent.hasFound("place3Id"));
    }
}

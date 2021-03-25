package com.example.dev_mob_houet_piron.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class Place {

    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private double longitude;
    private double latitude;
    private String type;
    private String description;

    //Constructeur vide obligatoire pour firebase
    public Place() {} ;

    public Place(double longitude, double latitude, String name, String description, String type){
        this.id = UUID.randomUUID().toString();
        this.longitude = longitude;
        this.latitude = latitude;
        this.description = description;
        this.name = name;
        this.type = type;
    }

    public String getId() {
        return this.id;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getType() {
        return this.type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setType(String type) {
        this.type = (type.contentEquals("official"))? type : "unofficial";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Place)) {
            return false;
        }
        Place toCompare = (Place)o;

        return this.getId().contentEquals(toCompare.getId());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }
}

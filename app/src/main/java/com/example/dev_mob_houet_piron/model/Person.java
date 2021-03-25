package com.example.dev_mob_houet_piron.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Person {

    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private List<String> foundCaches = new ArrayList<>();
    private Date registerDate = new Date();

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public List<String> getFoundCaches() {
        return this.foundCaches;
    }

    public Date getRegisterDate() {
        return this.registerDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFoundCaches(List<String> foundCaches){
        this.foundCaches = foundCaches;
    }

    public void setRegisterDate(Date date) { this.registerDate = date; }

    public boolean hasFound(String id){
        return foundCaches.contains(id);
    }

    public void addFoundCache(String id){
        this.foundCaches.add(id);
    }
}

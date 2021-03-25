package com.example.dev_mob_houet_piron.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
public class Badge {
    @PrimaryKey
    @NonNull
    private String id;
    private String title;
    private String description;
    private List<String> cachesIds = new ArrayList<>();

    //Constructeur vide obligatoire pour firebase
    public Badge() {} ;

    public Badge(String title, String description){
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription(){
        return this.description;
    }

    public List<String> getCachesIds(){
        return this.cachesIds;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setCachesIds(List<String> cachesIds) {
        this.cachesIds = cachesIds;
    }

    public int getNbrOfCacheToFind() {
        return this.cachesIds.size();
    }
}

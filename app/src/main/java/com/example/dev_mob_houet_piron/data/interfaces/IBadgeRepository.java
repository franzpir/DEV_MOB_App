package com.example.dev_mob_houet_piron.data.interfaces;

import androidx.lifecycle.LiveData;

import com.example.dev_mob_houet_piron.model.Badge;

import java.util.List;

public interface IBadgeRepository {

    List<Badge> getBadgesSync();

    LiveData<List<Badge>> getBadges();

    LiveData<Badge> getBadge(String id);

    void add(final Badge badge);

    void addAll(final List<Badge> badges);
}

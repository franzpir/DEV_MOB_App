package com.example.dev_mob_houet_piron.presenter;

import com.example.dev_mob_houet_piron.data.interfaces.IBadgeRepository;
import com.example.dev_mob_houet_piron.data.room.repository.LocalBadgeRepository;
import com.example.dev_mob_houet_piron.data.room.repository.LocalPlaceRepository;
import com.example.dev_mob_houet_piron.model.Badge;
import com.example.dev_mob_houet_piron.view.AddCacheFragment;

import java.util.List;

public class AddBadgePresenter {

    IBadgeRepository badgeRepository;

    public AddBadgePresenter(){
        this.badgeRepository = LocalBadgeRepository.getInstance();
    }

    public AddBadgePresenter(IBadgeRepository badgeRepository){
        this.badgeRepository = badgeRepository;
    }

    public void addBadge(String title, String description, List<String> cachesId) {
        Badge badge = new Badge(title, description);
        badge.setCachesIds(cachesId);
        badgeRepository.add(badge);
    }
}

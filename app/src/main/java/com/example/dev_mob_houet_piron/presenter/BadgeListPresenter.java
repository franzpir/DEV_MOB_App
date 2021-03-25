package com.example.dev_mob_houet_piron.presenter;

import com.example.dev_mob_houet_piron.data.firebase.FirebaseBadgeRepository;
import com.example.dev_mob_houet_piron.data.room.repository.LocalBadgeRepository;
import com.example.dev_mob_houet_piron.data.room.repository.PersonRepository;
import com.example.dev_mob_houet_piron.model.Badge;
import com.example.dev_mob_houet_piron.view.interfaces.IBadgeItem;
import com.example.dev_mob_houet_piron.view.interfaces.IDisplayBadgeList;

import java.util.ArrayList;
import java.util.List;

public class BadgeListPresenter {

    private IDisplayBadgeList badgeDisplayer;

    private List<Badge> unOfficialBadges = new ArrayList<>();
    private List<Badge> officialBadges = new ArrayList<>();
    private List<String> foundCaches = new ArrayList<>();

    public BadgeListPresenter(IDisplayBadgeList badgeDisplayer) {
        this.badgeDisplayer = badgeDisplayer;
    }

    public String bindOfficialBadgeToItem(IBadgeItem badgeItem, int position) {
        FirebaseBadgeRepository.getInstance().getBadge(officialBadges.get(position).getId()).observeForever(badge -> {
            badgeItem.setTitle(badge.getTitle());
            badgeItem.setDescription(badge.getDescription());
            badgeItem.setNbrExpected(badge.getNbrOfCacheToFind());
            badgeItem.setNbrFound(this.howMuchFoundIn(badge.getCachesIds()));
        });
        return officialBadges.get(position).getId();
    }

    public String bindUnOfficialBadgeToItem(IBadgeItem badgeItem, int position) {
        LocalBadgeRepository.getInstance().getBadge(unOfficialBadges.get(position).getId()).observeForever(badge -> {
            badgeItem.setTitle(badge.getTitle());
            badgeItem.setDescription(badge.getDescription());
            badgeItem.setNbrExpected(badge.getNbrOfCacheToFind());
            badgeItem.setNbrFound(this.howMuchFoundIn(badge.getCachesIds()));
        });
        return unOfficialBadges.get(position).getId();
    }

    public int getOfficialBadgesSize() {
        return officialBadges.size();
    }

    public int getUnOfficialBadgesSize() {
        return unOfficialBadges.size();
    }

    public void loadBadges() {
        FirebaseBadgeRepository.getInstance().getBadges().observeForever(badges -> {
            BadgeListPresenter.this.officialBadges = badges;
            badgeDisplayer.loadView();
        });

        LocalBadgeRepository.getInstance().getBadges().observeForever(badges -> {
            BadgeListPresenter.this.unOfficialBadges = badges;
            badgeDisplayer.loadView();
        });

        PersonRepository.getInstance().getPerson("user1").observeForever(person -> {
            if(person != null)
                BadgeListPresenter.this.foundCaches = person.getFoundCaches();
        });
    }

    private int howMuchFoundIn(List<String> cachesIds) {
        int sum = 0;
        if(cachesIds.size() > 0 && foundCaches.size() > 0)
            for(String foundCacheId: this.foundCaches){
                for(String cacheId : cachesIds) {
                    if(cacheId.contentEquals(foundCacheId))
                        sum++;
                }
            }
        return sum;
    }

}

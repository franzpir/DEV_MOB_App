package com.example.dev_mob_houet_piron.data.room.repository;

import androidx.lifecycle.LiveData;

import com.example.dev_mob_houet_piron.data.interfaces.IBadgeRepository;
import com.example.dev_mob_houet_piron.data.room.GeoCachingDatabase;
import com.example.dev_mob_houet_piron.data.room.dao.BadgeDao;
import com.example.dev_mob_houet_piron.model.Badge;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalBadgeRepository implements IBadgeRepository  {

    private static LocalBadgeRepository instance;

    private final BadgeDao badgeDao = GeoCachingDatabase.getInstance().badgeDao();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private List<Badge> badges;

    private LocalBadgeRepository() { getBadges().observeForever(badges -> this.badges = badges);}

    @Override
    public List<Badge> getBadgesSync() {
        return this.badges;
    }

    @Override
    public LiveData<List<Badge>> getBadges() {
        return badgeDao.getBadges();
    }

    @Override
    public LiveData<Badge> getBadge(String id) {
        return badgeDao.getBadge(id);
    }

    @Override
    public void add(final Badge badge) {
        executor.execute(() -> badgeDao.insert(badge));
    }

    @Override
    public void addAll(final List<Badge> badges) {
        for(Badge badge : badges){
            add(badge);
        }
    }

    public static LocalBadgeRepository getInstance() {
        if(instance == null)
            instance = new LocalBadgeRepository();
        return instance;
    }
}

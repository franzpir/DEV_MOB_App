package com.example.dev_mob_houet_piron.presenter;

public abstract class CachePresenter {

    public void loadPlace(String cacheId) {
        if(cacheId.startsWith("FB"))
            loadFirebasePlace(cacheId);
        else
            loadRoomPlace(cacheId);
    }

    public void loadRoomPlace(String cacheId) {

    }

    public void loadFirebasePlace(String cacheId) {

    }

}

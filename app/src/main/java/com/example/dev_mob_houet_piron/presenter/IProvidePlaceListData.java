package com.example.dev_mob_houet_piron.presenter;

import com.example.dev_mob_houet_piron.view.interfaces.IPlaceItem;

public interface IProvidePlaceListData {
    void onBindPlaceRowViewAtPosition(IPlaceItem cacheItem, int position);

    int getPlaceListSize();
}

package com.example.dev_mob_houet_piron.data.room;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import com.example.dev_mob_houet_piron.data.room.dao.BadgeDao;
import com.example.dev_mob_houet_piron.data.room.dao.PersonDao;
import com.example.dev_mob_houet_piron.data.room.dao.PlaceDao;
import com.example.dev_mob_houet_piron.model.Badge;
import com.example.dev_mob_houet_piron.model.Person;
import com.example.dev_mob_houet_piron.model.Place;

@Database(entities = {Badge.class, Place.class, Person.class}, version = 10, exportSchema = false)
@TypeConverters({GeoCachingTypeConverters.class})
public abstract class GeoCachingDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "geo_caching_database";
    private static GeoCachingDatabase instance;

    public abstract BadgeDao badgeDao();
    public abstract PlaceDao placeDao();
    public abstract PersonDao personDao();

    public static void initDatabase(Context context) {
        if(instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), GeoCachingDatabase.class, DATABASE_NAME).build();
    }

    public static GeoCachingDatabase getInstance() {
        if(instance == null)
            throw new IllegalStateException("Database must first be initialized");
        return  instance;
    }

    public static void disconnectDatabase() {
        instance = null;
    }

}

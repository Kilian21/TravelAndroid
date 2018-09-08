package com.example.markusbink.travelapp.Routeplaner;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface EntityOneDao {

    @Insert
    void insertPartsOfRouteOne(RoutePlaner_EntityOne routePlanerEntityOne);

    @Query("SELECT * FROM routeOne")
    RoutePlaner_EntityOne[] selectAllItems();

    @Query("DELETE FROM routeOne")
    void deleteAllItems();

}

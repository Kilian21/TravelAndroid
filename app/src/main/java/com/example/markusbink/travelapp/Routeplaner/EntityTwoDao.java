package com.example.markusbink.travelapp.Routeplaner;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface EntityTwoDao {

    @Insert
    void insertPartsOfRouteTwo(RoutePlaner_EntityTwo routePlanerEntityTwo);

    @Query("SELECT * FROM routeTwo")
    RoutePlaner_EntityTwo[] selectAllPartsFromRouteTwo();

    @Query("DELETE FROM routeTwo")
    void deleteAllPartsFromRouteTwo();

}
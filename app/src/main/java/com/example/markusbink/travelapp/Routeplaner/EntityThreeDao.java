package com.example.markusbink.travelapp.Routeplaner;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

@Dao
public interface EntityThreeDao {

    @Insert
    void insertPartsOfRouteThree(RoutePlaner_EntityThree routePlanerEntityThree);

    @Query("SELECT * FROM routeThree")
    RoutePlaner_EntityThree[] selectAllPartsFromRouteThree();

    @Query("DELETE FROM routeThree")
    void deleteAllPartsFromRouteThree();
}


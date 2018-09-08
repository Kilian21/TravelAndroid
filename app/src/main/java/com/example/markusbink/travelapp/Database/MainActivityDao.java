package com.example.markusbink.travelapp.Database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.markusbink.travelapp.MainActivity.MainActivity_SingleItem;

@Dao
public interface MainActivityDao {

    @Insert
    void insertItem(MainActivity_SingleItem item);


    @Query("DELETE FROM single_vacations")
    void deleteAllItems();





}

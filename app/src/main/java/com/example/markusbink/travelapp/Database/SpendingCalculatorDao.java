package com.example.markusbink.travelapp.Database;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.markusbink.travelapp.SpendingCalculator.SpendingCalculator_SingleItem;

@Dao
public interface SpendingCalculatorDao {


    @Insert
    void insertItem(SpendingCalculator_SingleItem item);

    @Query("DELETE FROM spendingCalculator WHERE itemId = :id")
    void deleteSpendingItem(int id);


    @Query("SELECT * FROM spendingCalculator")
    SpendingCalculator_SingleItem[] selectAllSpendingItems();

    @Query("DELETE FROM spendingCalculator")
    void deleteAllSpendingItems();


}

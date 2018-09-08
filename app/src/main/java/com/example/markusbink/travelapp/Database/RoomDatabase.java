package com.example.markusbink.travelapp.Database;

import android.arch.persistence.room.Database;

import com.example.markusbink.travelapp.MainActivity.MainActivity_SingleItem;
import com.example.markusbink.travelapp.PackingList.PackingList_SingleItem;
import com.example.markusbink.travelapp.SpendingCalculator.SpendingCalculator_SingleItem;


@Database(entities = {MainActivity_SingleItem.class, PackingList_SingleItem.class, SpendingCalculator_SingleItem.class}, version = 3, exportSchema = false)

public abstract class RoomDatabase extends android.arch.persistence.room.RoomDatabase {

    public abstract MainActivityDao mainActivityInterface();

    public abstract PackingListDao packingListInterface();

    public abstract SpendingCalculatorDao spendingCalculatorInterface();


}

package com.example.markusbink.travelapp.Routeplaner;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "routeTwo")
public class RoutePlaner_EntityTwo {

    @NonNull
    @PrimaryKey(autoGenerate = true)

    private int id2;

    private String destination2;

    private String transportation2;


    @NonNull
    public int getId2() { return id2; }

    public void setId2(@NonNull int id2) { this.id2 = id2; }


    public String getDestination2() { return destination2; }

    public void setDestination2(String destination2) { this.destination2 = destination2; }


    public String getTransportation2() { return transportation2; }

    public void setTransportation2(String transportation2) { this.transportation2 = transportation2; }

    public RoutePlaner_EntityTwo(){}

}

package com.example.markusbink.travelapp.Routeplaner;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "routeOne")
public class RoutePlaner_EntityOne {

    @NonNull
    @PrimaryKey(autoGenerate = true)

    private int id;

    private String destination;

    private String transportation;


    @NonNull
    public int getId() { return id; }

    public void setId(@NonNull int id) { this.id = id; }


    public String getDestination() { return destination; }

    public void setDestination(String destination) { this.destination = destination; }


    public String getTransportation() { return transportation; }

    public void setTransportation(String transportation) { this.transportation = transportation; }

    public RoutePlaner_EntityOne(){}
}

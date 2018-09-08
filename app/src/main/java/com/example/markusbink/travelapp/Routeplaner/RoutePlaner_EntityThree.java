package com.example.markusbink.travelapp.Routeplaner;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "routeThree")
public class RoutePlaner_EntityThree {

    @NonNull
    @PrimaryKey(autoGenerate = true)

    private int id3;

    private String destination3;

    private String transportation3;


    @NonNull
    public int getId3() { return id3; }

    public void setId3(@NonNull int id3) { this.id3 = id3; }


    public String getDestination3() { return destination3; }

    public void setDestination3(String destination3) { this.destination3 = destination3; }


    public String getTransportation3() { return transportation3; }

    public void setTransportation3(String transportation3) { this.transportation3 = transportation3; }

    public RoutePlaner_EntityThree(){}


}
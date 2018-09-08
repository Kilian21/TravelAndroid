package com.example.markusbink.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.markusbink.travelapp.MainActivity.MainActivity;
import com.example.markusbink.travelapp.PackingList.PackingList;
import com.example.markusbink.travelapp.Routeplaner.FirstscreenRouteplaner;
import com.example.markusbink.travelapp.SpendingCalculator.SpendingCalculator;
import com.example.markusbink.travelapp.Weather.Weather;

public class ActionBarActivity extends AppCompatActivity {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionbar);
    }

    //INFLATE THE MENU WITH LAYOUT

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //CLICKEVENTS FOR THE MAIN MENU-ITEMS

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case R.id.start_activity:
                Intent startIntent = new Intent(this, MainActivity.class);
                startActivity(startIntent);
                return true;

            case R.id.packinglist_activity:
                Intent packlisteIntent = new Intent(this, PackingList.class);
                startActivity(packlisteIntent);
                return true;

            case R.id.spendingcalculator_activity:
                Intent kostenrechner = new Intent(this, SpendingCalculator.class);
                startActivity(kostenrechner);
                return true;

            case R.id.route_activity:
                Intent route = new Intent(this, FirstscreenRouteplaner.class);
                startActivity(route);
                return true;

            case R.id.weather_activity:
                Intent weather = new Intent(this, Weather.class);
                startActivity(weather);
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

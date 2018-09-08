package com.example.markusbink.travelapp.Routeplaner;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

import com.example.markusbink.travelapp.ActionBarActivity;
import com.example.markusbink.travelapp.R;


public class SecondscreenRouteplaner extends ActionBarActivity {


        TextView routeIdentity, firstDestination;
        EditText enterNewDestination;
        Spinner transportation;
        Button addButton;

        RecyclerView recyclerView;
        RecyclerView.Adapter recyclerViewAdapter;
        RecyclerView.LayoutManager recyclerViewLayoutManager;


        private RoutePlaner_EntityOne[] routeOneList;
        private RoutePlaner_EntityTwo[] routeTwoList;
        private RoutePlaner_EntityThree[] routeThreeList;


        private ArrayList<RoutePlaner_EntityOne> routePlanerEntityOne;
        private ArrayList<RoutePlaner_EntityTwo> routePlanerEntityTwo;
        private ArrayList<RoutePlaner_EntityThree> routePlanerEntityThree;
        private AppDatabase db;

        public int extra1;
        public int extra2;
        public int extra3;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_routeplaner_secondscreen);

            Bundle extras = getIntent().getExtras();

            if (extras != null){

                Intent switchToRouteOne = getIntent();
                extra1 = switchToRouteOne.getIntExtra(FirstscreenRouteplaner.KEY_ROUTE_ONE, 0);

                Intent switchToRouteTwo = getIntent();
                extra2 = switchToRouteTwo.getIntExtra(FirstscreenRouteplaner.KEY_ROUTE_TWO, 0);

                Intent switchToRouteThree = getIntent();
                extra3 = switchToRouteThree.getIntExtra(FirstscreenRouteplaner.KEY_ROUTE_THREE, 0);
            }


            initDB();
            initUI();
            initRecyclerView();
            initListeners();
        }

        private void initDB() {
            db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "saveRoutes").fallbackToDestructiveMigration().build();
        }

        /*
        Referenziert die Komponenten des UI und die ArrayList
         */
        private void initUI() {

            routeIdentity = findViewById(R.id.identifyRoute);
            setRouteIdentity();

            firstDestination = findViewById(R.id.destination_first);

            enterNewDestination = findViewById(R.id.enter_destination);

            transportation = findViewById(R.id.transportation_dropdown);

            addButton = findViewById(R.id.add_button);

            routePlanerEntityOne = new ArrayList<>();
            routePlanerEntityTwo = new ArrayList<>();
            routePlanerEntityThree = new ArrayList<>();

            initSavedItems();
        }


        private void initSavedItems() {

            new Thread(new Runnable() {
                @Override
                public void run() {

                    if (extra1 == 1 && extra2 == 0 && extra3 == 0 ){

                        routeOneList = db.routeOneInterface().selectAllItems();

                        for(RoutePlaner_EntityOne item1 : routeOneList) {
                            addItemToRouteOne(item1);
                            recyclerViewAdapter.notifyDataSetChanged();
                        }

                    }else if (extra2 == 2 && extra1 == 0 && extra3 == 0){

                        routeTwoList = db.routeTwoInterface().selectAllPartsFromRouteTwo();

                        for (RoutePlaner_EntityTwo item2 : routeTwoList){
                            addItemToRouteTwo(item2);
                            recyclerViewAdapter.notifyDataSetChanged();
                        }

                    }else if (extra3 == 3 && extra1 == 0 && extra2 == 0){

                        routeThreeList = db.routeThreeInterface().selectAllPartsFromRouteThree();

                        for (RoutePlaner_EntityThree item3 : routeThreeList){
                            addItemToRouteThree(item3);
                            recyclerViewAdapter.notifyDataSetChanged();
                        }

                    }
                }
            }).start();
        }

        private void addItemToRouteOne(RoutePlaner_EntityOne item1) {
            routePlanerEntityOne.add(item1);

        }

        private void addItemToRouteTwo(RoutePlaner_EntityTwo item2) {
            routePlanerEntityTwo.add(item2);

        }

        private void addItemToRouteThree(RoutePlaner_EntityThree item3){
            routePlanerEntityThree.add(item3);
        }

        /*
        Zeigt an, welche Route gerade bearbeitet wird
         */
        private void setRouteIdentity(){

            if (extra1 == 1 && extra2 == 0 && extra3 == 0 ){
                routeIdentity.setText(R.string.route_1);
            }else if (extra2 == 2 && extra1 == 0 && extra3 == 0){
                routeIdentity.setText(R.string.route_2);
            }else if (extra3 == 3 && extra1 == 0 && extra2 == 0){
                routeIdentity.setText(R.string.route_3);
            }
        }

        /*
        Erstellt den Listener für den Button um die Liste um ein Item zu ergänzen
         */
        private void initListeners() {

            addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addToRoute();
                }
            });

        }

        /*
        Fügt der ArrayList und der Datenbank ein weiteres Ziel+Verkehrsmittel - Paar hinzu
         */
        private void addToRoute(){

            final String destinationName = enterNewDestination.getText().toString();
            final String transportationName = transportation.getSelectedItem().toString();

            if (enterNewDestination.getText().length() !=0){

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        if (extra1 == 1 && extra2 == 0 && extra3 == 0 ){

                            final RoutePlaner_EntityOne part1 = new RoutePlaner_EntityOne();
                            part1.setDestination(destinationName);
                            part1.setTransportation(transportationName);

                            db.routeOneInterface().insertPartsOfRouteOne(part1);
                            addItemToRouteOne(part1);




                        }else if (extra2 == 2 && extra1 == 0 && extra3 == 0){

                            final RoutePlaner_EntityTwo part2 = new RoutePlaner_EntityTwo();
                            part2.setDestination2(destinationName);
                            part2.setTransportation2(transportationName);

                            db.routeTwoInterface().insertPartsOfRouteTwo(part2);
                            addItemToRouteTwo(part2);


                        }else if (extra3 == 3 && extra1 == 0 && extra2 == 0){

                            final RoutePlaner_EntityThree part3 = new RoutePlaner_EntityThree();
                            part3.setDestination3(destinationName);
                            part3.setTransportation3(transportationName);

                            db.routeThreeInterface().insertPartsOfRouteThree(part3);
                            addItemToRouteThree(part3);

                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if (extra1 == 1 && extra2 == 0 && extra3 == 0 ){

                                    recyclerView.smoothScrollToPosition(routePlanerEntityOne.size());

                                }else if (extra2 == 2 && extra1 == 0 && extra3 == 0){

                                    recyclerView.smoothScrollToPosition(routePlanerEntityTwo.size());

                                }else if (extra3 == 3 && extra1 == 0 && extra2 == 0){

                                    recyclerView.smoothScrollToPosition(routePlanerEntityThree.size());

                                }



                                enterNewDestination.setText("");
                                transportation.setSelection(0);
                            }
                        });



                    }
                }).start();

            } else if (enterNewDestination.getText().length() == 0){

                Toast toast = Toast.makeText(this, "Kein Ziel eingegeben!", Toast.LENGTH_SHORT);
                toast.show();
            }
        }

        /*
        Erstellt den RecyclerView (Listenansicht).
         */
        private void initRecyclerView() {

            recyclerView = findViewById(R.id.RecyclerView);
            recyclerViewLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(recyclerViewLayoutManager);

            //Erstellt und steckt den Adapter an

            recyclerViewAdapter = new RvAdapter(routePlanerEntityOne, routePlanerEntityTwo, routePlanerEntityThree, extra1, extra2, extra3);

            recyclerView.setAdapter(recyclerViewAdapter);
        }

        /*
        Erstellt die Menüpunkte "löschen" und "speichern"
         */
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            getMenuInflater().inflate(R.menu.routeplaner_menu, menu);
            return true;
        }

        /*
        Legt die Aktionen für die Menüpunkte fest
         */
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            deleteAllItems();

            return true;
        }




        private void deleteAllItems() {

            new Thread(new Runnable() {
                @Override
                public void run() {

                    if (extra1 == 1 && extra2 == 0 && extra3 == 0 ){

                        db.routeOneInterface().deleteAllItems();


                    }else if (extra2 == 2 && extra1 == 0 && extra3 == 0){

                        db.routeTwoInterface().deleteAllPartsFromRouteTwo();

                    }else if (extra3 == 3 && extra1 == 0 && extra2 == 0){

                        db.routeThreeInterface().deleteAllPartsFromRouteThree();

                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (extra1 == 1 && extra2 == 0 && extra3 == 0 ){

                                if(routePlanerEntityOne.size() != 0) {
                                    routePlanerEntityOne.clear();
                                    recyclerViewAdapter.notifyDataSetChanged();
                                }

                            }else if (extra2 == 2 && extra1 == 0 && extra3 == 0){

                                if (routePlanerEntityTwo.size() != 0){
                                    routePlanerEntityTwo.clear();
                                    recyclerViewAdapter.notifyDataSetChanged();
                                }

                            }else if (extra3 == 3 && extra1 == 0 && extra2 == 0){

                                if (routePlanerEntityThree.size() != 0){
                                    routePlanerEntityThree.clear();
                                    recyclerViewAdapter.notifyDataSetChanged();
                                }

                            }

                        }
                    });

                }
            }).start();
        }
    }

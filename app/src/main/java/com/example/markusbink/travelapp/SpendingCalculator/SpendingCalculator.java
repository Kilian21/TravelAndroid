package com.example.markusbink.travelapp.SpendingCalculator;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markusbink.travelapp.ActionBarActivity;
import com.example.markusbink.travelapp.Constants;
import com.example.markusbink.travelapp.Database.RoomDatabase;
import com.example.markusbink.travelapp.R;

import java.util.ArrayList;

public class SpendingCalculator extends ActionBarActivity {

    private static final String TAG = "SpendingCalculator";

    private EditText editTextLabel, editTextPrice;
    private TextView textViewTotal;
    private Button buttonAdd;
    private ListView listView;
    private ArrayList<SpendingCalculator_SingleItem> arrayList = new ArrayList<>();
    private SpendingCalculator_SingleItem[] spendingList;
    private SpendingCalculator_Adapter arrayAdapter;
    private RoomDatabase db;
    private String budget = "250";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spendingcalculator);
        initDB();

        Log.d(TAG, "kostenrechner geöffnet");
        initUi();
        initActionBar();
        initListeners();
    }

    private RoomDatabase initDB() {
        db = Room.databaseBuilder(SpendingCalculator.this, RoomDatabase.class, Constants.DATABASENAME).fallbackToDestructiveMigration().build();
        return db;
    }

    private void initListeners() {

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    addSingleSpendingToListView();

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                removeSingleSpendingFromListView(position);
                return true;
            }
        });

    }

    private void increaseTotalValue(double amount) {

        double totalPrice = Double.parseDouble(textViewTotal.getText().toString());
        totalPrice += amount;

        try {
            textViewTotal.setText(String.valueOf(totalPrice));

        }
        catch(Exception e) {
            Log.e(TAG, "Increase Total Value Exception", e);
        }


    }


    private void decreaseTotalValue(double amount) {

        double totalPrice = Double.parseDouble(textViewTotal.getText().toString());


        if(totalPrice > 0) {
            totalPrice -= amount;
            Toast.makeText(getApplicationContext(), totalPrice + "übrig", Toast.LENGTH_SHORT).show();

            try {
                textViewTotal.setText(String.valueOf(totalPrice));

            }
            catch(Exception e) {
                Log.e(TAG, "Decrease Total Value Exception", e);
            }

        } else {
            Toast.makeText(getApplicationContext(), "Dein Budget ist verbraucht.", Toast.LENGTH_SHORT).show();
        }




    }

    //Add a single Element to UI and DB

    private void addSingleSpendingToListView() {

        final String labelItem = editTextLabel.getText().toString();


        //Replaces comma as decimal separator with a dot
        final String priceItem = editTextPrice.getText().toString().replace(',', '.');

        if(!labelItem.equals("") && !priceItem.equals("")) {

            // Thread starts here
            new Thread(new Runnable() {
                @Override
                public void run() {


                    final SpendingCalculator_SingleItem spendingItem = new SpendingCalculator_SingleItem(labelItem, priceItem);

                    // Sets the ID of the newest element in the list
                    try {

                        int lastListItemIndex = arrayList.size() - 1;
                        int lastListItemId = arrayList.get(lastListItemIndex).getItemId() + 1;
                        spendingItem.setItemId(lastListItemId);

                    }
                    catch(Exception e) {
                        Log.e(TAG, "Fatal Exception", e);
                    }

                    db.spendingCalculatorInterface().insertItem(spendingItem);


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            addItemToList(spendingItem);
                            decreaseTotalValue(Double.parseDouble(priceItem));
                            editTextLabel.setText("");
                            editTextPrice.setText("");

                        }
                    });
                }


            }).start();
            //Thread ends here

        } else {
            Toast.makeText(getApplicationContext(), "Bitte Art und Wert eingeben.", Toast.LENGTH_SHORT).show();
        }



    }

    // Remove a single item from database and list
    private void removeSingleSpendingFromListView(final int position) {

        SpendingCalculator_SingleItem singleItem = (SpendingCalculator_SingleItem)listView.getItemAtPosition(position);
        final int listViewPosition = singleItem.getItemId();
        final String listViewName = singleItem.getName();

        final double itemPrice = Double.parseDouble(singleItem.getPrice());



        new Thread(new Runnable() {
            @Override
            public void run() {


                db.spendingCalculatorInterface().deleteSpendingItem(listViewPosition);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        removeSingleSpending(position);
                        increaseTotalValue(itemPrice);

                        Toast.makeText(getApplicationContext(), "Eintrag wurde entfernt.", Toast.LENGTH_SHORT).show();




                    }
                });
            }
        }).start();
    }


    // Add a Single Item to the List and Notify the DataSet
    private void addItemToList(SpendingCalculator_SingleItem itemName) {
        arrayList.add(itemName);
        arrayAdapter.notifyDataSetChanged();
    }


    // Remove a Single Item to the List and Notify the DataSet
    private void removeSingleSpending(int position) {
        arrayList.remove(position);
        arrayAdapter.notifyDataSetChanged();
    }

    // Deletes all items in DB and List
    private void deleteAllListItems() {

        new Thread(new Runnable() {
            @Override
            public void run() {


                db.spendingCalculatorInterface().deleteAllSpendingItems();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(arrayList.size() != 0) {

                            arrayList.clear();
                            arrayAdapter.notifyDataSetChanged();

                            textViewTotal.setText(budget);


                            Toast.makeText(getApplicationContext(), "Alle Einträge wurden entfernt.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Keine Einträge gefunden.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
        }).start();



    }


    //Initializes every UI component
    private void initUi(){
        editTextLabel = (EditText)findViewById(R.id.editText_label);
        editTextPrice = (EditText)findViewById(R.id.editText_price);
        textViewTotal = (TextView)findViewById(R.id.textView_total);
        textViewTotal.setText(budget);
        buttonAdd = (Button)findViewById(R.id.button_add_price);
        listView = (ListView)findViewById(R.id.listView_prices);
        arrayAdapter = new SpendingCalculator_Adapter(SpendingCalculator.this, R.layout.singleitem_spendingcalculator, arrayList);
        listView.setAdapter(arrayAdapter);
        initSavedItems();

    }

    // Populates the UI with saved data from Database
    private void initSavedItems() {

        new Thread(new Runnable() {
            @Override
            public void run() {

                spendingList = db.spendingCalculatorInterface().selectAllSpendingItems();
                for(SpendingCalculator_SingleItem singleItem : spendingList) {
                    addItemToList(singleItem);
                }

                Log.d(TAG, "run: init saved items");

                double spendingAmount = 0;
                double currentTotal = Double.parseDouble(textViewTotal.getText().toString());

                for(SpendingCalculator_SingleItem singleItem : spendingList) {

                    spendingAmount += Double.parseDouble(singleItem.getPrice());

                }

                String newTotal = String.valueOf(currentTotal - spendingAmount);
                textViewTotal.setText(newTotal);


            }
        }).start();


    }

    //Change Title
    private void initActionBar() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Kostenrechner");
        }
    }

    //Set Delete-Icon and OnClickListener for it
    //Hide PackingList menu item
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.spendingcalculator_activity).setVisible(false);
        menu.findItem(R.id.deleteItems).setVisible(true).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                deleteAllListItems();

                return false;
            }
        });
        return true;
    }

}



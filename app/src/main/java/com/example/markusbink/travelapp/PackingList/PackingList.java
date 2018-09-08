package com.example.markusbink.travelapp.PackingList;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.markusbink.travelapp.ActionBarActivity;
import com.example.markusbink.travelapp.Constants;
import com.example.markusbink.travelapp.Database.RoomDatabase;
import com.example.markusbink.travelapp.R;

import java.util.ArrayList;

public class PackingList extends ActionBarActivity {

    private static final String TAG = "PackingList";

    private EditText editTextLuggage;
    private Button buttonLuggage;
    private ListView listViewLuggage;
    private ArrayList<String> luggageList = new ArrayList<>();
    private PackingList_SingleItem[] packingList;
    private ArrayAdapter<String> luggageAdapter;
    private RoomDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packinglist);
        initDB();
        initUi();
        initActionBar();
        initListeners();

    }

    private RoomDatabase initDB() {

        db = Room.databaseBuilder(PackingList.this, RoomDatabase.class, Constants.DATABASENAME).fallbackToDestructiveMigration().build();
        return db;
    }

    private void initListeners() {

        buttonLuggage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addNewItemToListView();

            }
        });

        listViewLuggage.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, View view, final int position, long id) {

                removeItemFromListView(position);
                return true;
            }
        });


    }

    private void addNewItemToListView() {

        final String luggageItem = editTextLuggage.getText().toString();


        if(!luggageItem.equals("")) {
        new Thread(new Runnable() {
            @Override
            public void run() {


                    PackingList_SingleItem packingItem = new PackingList_SingleItem();
                    packingItem.setItemName(luggageItem);

                    db.packingListInterface().insertItem(packingItem);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            addItemToList(luggageItem);
                            editTextLuggage.setText("");

                        }
                    });
                }

        }).start();

        } else {
            Toast.makeText(getApplicationContext(), "Bitte Textfeld ausfüllen.", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeItemFromListView(final int position) {
        final String itemName =  listViewLuggage.getItemAtPosition(position).toString();

        new Thread(new Runnable() {
            @Override
            public void run() {

                db.packingListInterface().deleteItem(itemName);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        luggageList.remove(position);
                        luggageAdapter.notifyDataSetChanged();

                        Toast.makeText(getApplicationContext(), "Eintrag wurde entfernt", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }).start();
    }


    // Remove all Items from database and list

    public void deleteAllIListItems() {

        new Thread(new Runnable() {
            @Override
            public void run() {


                db.packingListInterface().deleteAllItems();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if(luggageList.size() != 0) {

                            luggageList.clear();
                            luggageAdapter.notifyDataSetChanged();

                            Toast.makeText(getApplicationContext(), "Alle Einträge wurden entfernt", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Keine Einträge gefunden.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });


            }
        }).start();

    }


    private void initSavedItems() {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    packingList = db.packingListInterface().selectAllPackingItems();

                        for(PackingList_SingleItem item : packingList) {
                            String itemName = item.getItemName();
                            addItemToList(itemName);
                        }

                    }
            }).start();

    }






    private void addItemToList(String itemName) {
        luggageList.add(itemName);
        luggageAdapter.notifyDataSetChanged();
    }

    private void initActionBar() {

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Packliste");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    private void initUi() {
        editTextLuggage = findViewById(R.id.editText_luggage);
        buttonLuggage = findViewById(R.id.button_add_luggage);
        listViewLuggage = findViewById(R.id.listView_luggage);
        luggageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,luggageList);
        listViewLuggage.setAdapter(luggageAdapter);
        initSavedItems();
    }


    //SET DELETE-ICON AND ONCLICKLISTENER FOR IT

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.packinglist_activity).setVisible(false);
        menu.findItem(R.id.deleteItems).setVisible(true).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                deleteAllIListItems();
                return false;
            }
        });



        return true;



    }
}

package com.example.markusbink.travelapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.markusbink.travelapp.MainActivity.MainActivity;


public class SingleVacationActivity extends ActionBarActivity {

    TextView textViewVacationName, textViewStartDate, textViewEndDate;
    Button buttonBackButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singlevacation);

        initUi();
        initActionBar();
        initExtras();
        initListeners();

    }


    private void initUi() {
        textViewVacationName = findViewById(R.id.textview_vacation_name);
        textViewStartDate =  findViewById(R.id.textview_start_date);
        textViewEndDate =  findViewById(R.id.textview_end_date);
        buttonBackButton = findViewById(R.id.button_back_startactivity);
    }

    private void initActionBar() {
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initExtras() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            String vacationName = extras.getString(Constants.VACATIONNAME);
            String startDate = extras.getString(Constants.STARTDATE);
            String endDate = extras.getString(Constants.ENDDATE);

            textViewVacationName.setText(vacationName);
            textViewStartDate.setText(startDate);
            textViewEndDate.setText(endDate);
        }
    }

    private void initListeners() {
        buttonBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SingleVacationActivity.this, MainActivity.class);
                startActivity(i);
            }


        });
    }





}

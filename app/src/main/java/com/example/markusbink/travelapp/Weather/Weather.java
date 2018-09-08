package com.example.markusbink.travelapp.Weather;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.markusbink.travelapp.ActionBarActivity;
import com.example.markusbink.travelapp.R;
import com.facebook.stetho.Stetho;

public class Weather extends ActionBarActivity {

    TextView textViewTemp, textViewWind, textViewCloud, textViewPressure;
Weather_SingleItem weather = new Weather_SingleItem();

WeatherHttpClient weatherHttpClient = new WeatherHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        initUi();
        initString();
        renderWeatherData("Moscow,RU");
    }

    private void renderWeatherData(String city) {

        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(new String[]{city + "&units=metric"});

    }

    private void initString() {
        String city = weatherHttpClient.getWeatherData("Moscow,RU");
        Log.d("TAG", "city_string: " +city);
    }

    //AsyncTask


    private class WeatherTask extends AsyncTask<String, Void, Weather_SingleItem> {

        @Override
        protected Weather_SingleItem doInBackground(String... params) {

            String data = new WeatherHttpClient().getWeatherData(params[0]);

            weather = JSONWeatherParser.getWeather(data);

            Log.v("Data: ",  weather.place.getCity());

            return weather;
        }


        @Override
        protected void onPostExecute(Weather_SingleItem weather) {
            super.onPostExecute(weather);
        }



    }



    private void initUi() {
        textViewTemp = findViewById(R.id.textview_temperature);
        textViewWind = findViewById(R.id.textview_wind);
        textViewCloud = findViewById(R.id.textview_cloud);
        textViewPressure = findViewById(R.id.textview_pressure);


    }



}



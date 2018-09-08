package com.example.markusbink.travelapp.Weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class WeatherHttpClient {

    public String getWeatherData(String place) {
        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) (new URL(java.net.URLEncoder.encode(Utils.BASE_URL, "UTF-8") + place + "&APPID=08b0554d25e979ac4f422baf20a69306")).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();

            //Read response

            StringBuilder stringBuilder = new StringBuilder();
            inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;

            while((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + "\r\n");
            }

            inputStream.close();
            connection.disconnect();

            return stringBuilder.toString();


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}




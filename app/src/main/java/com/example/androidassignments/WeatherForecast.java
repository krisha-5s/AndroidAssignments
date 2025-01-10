package com.example.androidassignments;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherForecast extends AppCompatActivity {

    private static final String BASE_API_URL = "https://api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = "4da5d88427e5eb4855dde6cd3821c8fc";

    private String selectedCity;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        selectedCity = getIntent().getStringExtra("selectedCity");
        if (selectedCity == null || selectedCity.isEmpty()) {
            selectedCity = "Ottawa";
        }
        Log.i("WeatherForecast", "Starting weather forecast for city: " + selectedCity);
        new ForecastQuery().execute();
    }

    private class ForecastQuery extends AsyncTask<Void, Integer, Bitmap> {
        private String minTemp;
        private String maxTemp;
        private String currentTemp;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE); // Show progress bar
            Log.i("WeatherForecast", "Fetching weather data...");
        }

        @Override
        protected Bitmap doInBackground(Void... params) {
            Bitmap icon = null;
            try {
                String apiUrl = BASE_API_URL + "?q=" + selectedCity + ",ca&APPID=" + API_KEY + "&mode=xml&units=metric";
                Log.i("WeatherForecast", "API URL: " + apiUrl);

                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream inputStream = connection.getInputStream();
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(inputStream, null);

                while (parser.next() != XmlPullParser.END_DOCUMENT) {
                    if (parser.getEventType() == XmlPullParser.START_TAG) {
                        String tagName = parser.getName();
                        if ("temperature".equals(tagName)) {
                            publishProgress(25);
                            currentTemp = parser.getAttributeValue(null, "value");
                            minTemp = parser.getAttributeValue(null, "min");
                            maxTemp = parser.getAttributeValue(null, "max");
                            Log.i("WeatherForecast", "Temperature fetched: Current=" + currentTemp + "°C, Min=" + minTemp + "°C, Max=" + maxTemp + "°C");
                            publishProgress(50);
                        } else if ("weather".equals(tagName)) {
                            String iconName = parser.getAttributeValue(null, "icon");
                            Log.i("WeatherForecast", "Weather icon name: " + iconName);
                            icon = fetchWeatherIcon(iconName);
                            publishProgress(75);
                        }
                    }
                }
            } catch (IOException | XmlPullParserException e) {
                Log.e("WeatherForecast", "Error fetching weather data", e);
            }
            publishProgress(100);
            return icon;
        }

        private Bitmap fetchWeatherIcon(String iconName) {
            if (iconName == null) return null;

            String iconUrl = "https://openweathermap.org/img/w/" + iconName + ".png";
            Bitmap icon = null;

            String imageFileName = iconName + ".png";
            File file = getBaseContext().getFileStreamPath(imageFileName);
            boolean fileExists = file.exists();

            Log.i("WeatherForecast", "Looking for image locally: " + imageFileName);
            if (fileExists) {
                Log.i("WeatherForecast", "Image found locally.");
                try (FileInputStream fis = openFileInput(imageFileName)) {
                    icon = BitmapFactory.decodeStream(fis);
                } catch (IOException e) {
                    Log.e("WeatherForecast", "Error reading local image file", e);
                }
            } else {
                Log.i("WeatherForecast", "Downloading image: " + iconUrl);
                try {
                    URL url = new URL(iconUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.connect();
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        icon = BitmapFactory.decodeStream(connection.getInputStream());
                        // Save the downloaded image locally
                        try (FileOutputStream fos = openFileOutput(imageFileName, Context.MODE_PRIVATE)) {
                            icon.compress(Bitmap.CompressFormat.PNG, 80, fos);
                            Log.i("WeatherForecast", "Image downloaded and saved locally.");
                        }
                    }
                } catch (IOException e) {
                    Log.e("WeatherForecast", "Error downloading weather icon", e);
                }
            }
            return icon;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(values[0]);
                Log.i("WeatherForecast", "Progress updated: " + values[0] + "%");
            }
        }

        @Override
        protected void onPostExecute(Bitmap icon) {
            TextView currentTempView = findViewById(R.id.currentTempTextView);
            TextView minTempView = findViewById(R.id.minTempTextView);
            TextView maxTempView = findViewById(R.id.maxTempTextView);
            ImageView weatherIconView = findViewById(R.id.weatherImageView);

            if (currentTemp != null) currentTempView.setText(getText(R.string.Current_Temperature) +" "+ currentTemp + "°C");
            if (minTemp != null) minTempView.setText(getText(R.string.Minimum_Temperature) +" " + minTemp + "°C");
            if (maxTemp != null) maxTempView.setText(getText(R.string.Maximum_Temperature) +" " + maxTemp + "°C");
            if (icon != null) weatherIconView.setImageBitmap(icon);

            progressBar.setVisibility(View.INVISIBLE);
            Log.i("WeatherForecast", "Weather data display complete.");
        }
    }
}

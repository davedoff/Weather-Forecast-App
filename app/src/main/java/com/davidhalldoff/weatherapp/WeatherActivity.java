package com.davidhalldoff.weatherapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.davidhalldoff.weatherapp.api.WeatherData;
import com.davidhalldoff.weatherapp.api.WeatherDataCallback;
import com.davidhalldoff.weatherapp.io.FileIO;
import com.davidhalldoff.weatherapp.model.User;
import com.davidhalldoff.weatherapp.model.WeatherForecast;
import com.davidhalldoff.weatherapp.view.WeatherAdapter;

import java.time.LocalDateTime;

public class WeatherActivity extends BaseActivity implements WeatherDataCallback {

    private RecyclerView rvWeather;
    private AppCompatTextView tvApprovedTime, tvLocation;
    private AppCompatEditText etSearch;
    private AppCompatButton bSearch, ivFavoritesIcon;
    private User user;
    private SharedPreferences sp;
    private Integer updateIntervalMinutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout content = findViewById(R.id.content_container);
        getLayoutInflater().inflate(R.layout.activity_weather, content);

        navigationView.setCheckedItem(R.id.nav_weather);

        tvApprovedTime = findViewById(R.id.tv_approvedTime);
        tvLocation = findViewById(R.id.tv_location);
        etSearch = findViewById(R.id.et_search);
        bSearch = findViewById(R.id.b_search);
        ivFavoritesIcon = findViewById(R.id.iv_favorites);


        User loadedUser = FileIO.loadUser(this);
        if (loadedUser != null) {
            User.setInstance(loadedUser);
        }
        user = User.getInstance();

        for (WeatherForecast weatherForecast : user.getWeatherForecasts()) {
            Log.e("onCreate", weatherForecast.getLocation());
        }


        bSearch.setOnClickListener(this::onSearch);
        ivFavoritesIcon.setOnClickListener(this::onFavoritesClick);

        rvWeather = findViewById(R.id.rv_weather);
        rvWeather.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

    }

    @Override
    protected void onStart() {
        super.onStart();
        sp = this.getSharedPreferences("userSettings", Context.MODE_PRIVATE);
        updateIntervalMinutes = sp.getInt("updateIntervalSettings", 120);
        Log.e("updateIntervalMinutes", Integer.toString(updateIntervalMinutes));

        Log.e("WeatherActivity", "onStart");
        WeatherForecast weatherForecast = WeatherForecast.getInstance();
        if (getIntent().hasExtra("weatherForecast")) {
            WeatherForecast selectedWeatherForecast = (WeatherForecast) getIntent().getSerializableExtra("weatherForecast");
            WeatherForecast.setInstance(selectedWeatherForecast);
            if (selectedWeatherForecast.getApprovedTime().isBefore(LocalDateTime.now().minusMinutes(updateIntervalMinutes))) {
                if (networkIsConnected()) {
                    Toast.makeText(getApplicationContext(), "Retrieving new weather data (1)", Toast.LENGTH_SHORT).show();
                    WeatherData.retrieveWeatherForecast(this, this, selectedWeatherForecast.getLocation());
                } else {
                    Toast.makeText(this, "No internet connection, displayed data might be old (1)", Toast.LENGTH_SHORT).show();
                    initWeatherUI(WeatherForecast.setInstance(selectedWeatherForecast));
                }
            } else {
                initWeatherUI(WeatherForecast.setInstance(selectedWeatherForecast));
            }
        } else {
            if (weatherForecast.getWeatherList().size() > 0) {
                if (weatherForecast.getApprovedTime().isBefore(LocalDateTime.now().minusMinutes(updateIntervalMinutes))) {
                    if (networkIsConnected()) {
                        Toast.makeText(getApplicationContext(), "Retrieving new weather data (2)", Toast.LENGTH_SHORT).show();
                        WeatherData.retrieveWeatherForecast(this, this, weatherForecast.getLocation());
                    } else {
                        Toast.makeText(this, "No internet connection, displayed data might be old (2)", Toast.LENGTH_SHORT).show();
                        initWeatherUI(weatherForecast);
                    }
                } else {
                    initWeatherUI(weatherForecast);
                }
            }
        }
    }

    private void onFavoritesClick(View view) {
        WeatherForecast weatherForecast = WeatherForecast.getInstance();

        if (user.getFavoriteLocations().contains(weatherForecast.getLocation())) {
            user.removeFavoriteLocation(weatherForecast);
        } else {
            user.addFavoriteLocation(weatherForecast);
        }

        setFavoritesIconUI();
        for (String location : user.getFavoriteLocations()) {
            Log.e("onFavoritesClick", location);
        }

        FileIO.saveUser(user, getApplicationContext());
    }

    private void setFavoritesIconUI() {
        WeatherForecast weatherForecast = WeatherForecast.getInstance();
        if (user.getFavoriteLocations().contains(weatherForecast.getLocation())) {
            ivFavoritesIcon.setBackgroundResource(R.drawable.ic_baseline_star_24);
        } else {
            ivFavoritesIcon.setBackgroundResource(R.drawable.ic_baseline_star_border_24);
        }
    }

    private void onSearch(View view) {
        String location = etSearch.getText().toString();
        if (networkIsConnected()) {
            WeatherData.retrieveWeatherForecast(this, this, location);
        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onWeatherDataRetrieved(WeatherForecast weatherForecast) {
        initWeatherUI(weatherForecast);
    }

    @Override
    public void onWeatherDataError(String errorMessage) {
        etSearch.setError(errorMessage);
    }

    private void initWeatherUI(WeatherForecast weatherForecast) {
        WeatherAdapter weatherAdapter = new WeatherAdapter(weatherForecast.getWeatherList(), this);
        rvWeather.setAdapter(weatherAdapter);

        String approvedTimeStr = weatherForecast.getApprovedTime().toString();
        approvedTimeStr = approvedTimeStr.replace("T", " ");
        tvApprovedTime.setText("Last update: " + approvedTimeStr);
        tvLocation.setText(weatherForecast.getLocation());
        ivFavoritesIcon.setVisibility(View.VISIBLE);
        setFavoritesIconUI();
    }

    private boolean networkIsConnected() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork == null || !activeNetwork.isConnectedOrConnecting()) {
            return false;
        }

        return true;
    }
}
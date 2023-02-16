package com.davidhalldoff.weatherapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.davidhalldoff.weatherapp.io.FileIO;
import com.davidhalldoff.weatherapp.model.User;
import com.davidhalldoff.weatherapp.view.FavoritesAdapter;
import com.davidhalldoff.weatherapp.view.WeatherAdapter;

public class FavoritesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout content = findViewById(R.id.content_container);
        getLayoutInflater().inflate(R.layout.activity_favorites, content);

        navigationView.setCheckedItem(R.id.nav_favorites);

        RecyclerView rvFavorites = findViewById(R.id.rv_favoriteLocation);
        rvFavorites.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        User user;

        if (FileIO.loadUser(this) != null) {
            user = FileIO.loadUser(this);
        } else {
            user = User.getInstance();
            FileIO.saveUser(user, this);
        }

        for (String location : user.getFavoriteLocations()) {
            Log.e("onCreate", location);
        }

        FavoritesAdapter favoritesAdapter = new FavoritesAdapter(user.getFavoriteLocations(), this);
        rvFavorites.setAdapter(favoritesAdapter);
    }
}
package com.davidhalldoff.weatherapp.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private static User instance;
    private ArrayList<String> favoriteLocations;
    private ArrayList<WeatherForecast> weatherForecasts;

    private User() {
        favoriteLocations = new ArrayList<>();
        weatherForecasts = new ArrayList<>();
    }

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    public static User newInstance() {
        instance = new User();
        return instance;
    }

    public static User setInstance(User user) {
        if (instance == null) {
            instance = user;
        }
        return instance;
    }

    public ArrayList<String> getFavoriteLocations() {
        return favoriteLocations;
    }

    public void setFavoriteLocations(ArrayList<String> favoriteLocations) {
        this.favoriteLocations = favoriteLocations;
    }

    public void addFavoriteLocation(WeatherForecast weatherForecast) {
        favoriteLocations.add(weatherForecast.getLocation());
        weatherForecasts.add(weatherForecast);
    }

    public void removeFavoriteLocation(WeatherForecast weatherForecast) {
        favoriteLocations.remove(weatherForecast.getLocation());
        weatherForecasts.remove(weatherForecast);
    }

    public void removeFavoriteLocation(String location) {
        favoriteLocations.remove(location);
        weatherForecasts.removeIf(weatherForecast -> weatherForecast.getLocation().equals(location));
    }

    public ArrayList<WeatherForecast> getWeatherForecasts() {
        return weatherForecasts;
    }

    public void setWeatherForecasts(ArrayList<WeatherForecast> weatherForecasts) {
        this.weatherForecasts = weatherForecasts;
    }
}

package com.davidhalldoff.weatherapp.api;

import com.davidhalldoff.weatherapp.model.WeatherForecast;

public interface WeatherDataCallback {
    void onWeatherDataRetrieved(WeatherForecast weatherForecast);
    void onWeatherDataError(String errorMessage);
}

package com.davidhalldoff.weatherapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class WeatherForecast implements Serializable {
    private static WeatherForecast instance;
    private LocalDateTime approvedTime;
    private Double longitude;
    private Double latitude;
    private ArrayList<Weather> weatherList;
    private String location;

    private WeatherForecast() {
        approvedTime = null;
        longitude = 0.0;
        latitude = 0.0;
        weatherList = new ArrayList<>();
        location = null;
    }

    public static WeatherForecast getInstance() {
        if (instance == null) {
            instance = new WeatherForecast();
        }
        return instance;
    }

    public static WeatherForecast newInstance() {
        instance = new WeatherForecast();
        return instance;
    }

    public static WeatherForecast setInstance(WeatherForecast weatherForecast) {
        instance = weatherForecast;
        return instance;
    }

    public LocalDateTime getApprovedTime() {
        return approvedTime;
    }

    public void setApprovedTime(LocalDateTime approvedTime) {
        this.approvedTime = approvedTime;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public ArrayList<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(ArrayList<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public static class Weather implements Serializable {
        private LocalDateTime validTime;
        private LocalDate date;
        private Double cloudCoverage;
        private Double temperature;

        public Weather() {
            validTime = null;
            date = null;
            cloudCoverage = 0.0;
            temperature = 0.0;
        }

        public LocalDateTime getValidTime() {
            return validTime;
        }

        public void setValidTime(LocalDateTime validTime) {
            this.validTime = validTime;
        }

        public LocalDate getDate() {
            return date;
        }

        public void setDate(LocalDate date) {
            this.date = date;
        }

        public Double getCloudCoverage() {
            return cloudCoverage;
        }

        public void setCloudCoverage(Double cloudCoverage) {
            this.cloudCoverage = cloudCoverage;
        }

        public Double getTemperature() {
            return temperature;
        }

        public void setTemperature(Double temperature) {
            this.temperature = temperature;
        }
    }
}

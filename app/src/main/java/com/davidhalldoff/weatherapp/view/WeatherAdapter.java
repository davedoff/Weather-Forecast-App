package com.davidhalldoff.weatherapp.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.davidhalldoff.weatherapp.R;
import com.davidhalldoff.weatherapp.model.WeatherForecast;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

    private ArrayList<WeatherForecast.Weather> weatherList;
    private Context context;

    public WeatherAdapter(ArrayList<WeatherForecast.Weather> weatherList, Context context) {
        this.weatherList = weatherList;
        this.context = context;
    }

    @NonNull
    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_days_weather, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherAdapter.ViewHolder holder, int position) {
        LocalDate dateToDisplay = weatherList.get(0).getDate();
        dateToDisplay = dateToDisplay.plusDays(position);
        ArrayList<WeatherForecast.Weather> weatherForDay = new ArrayList<>();
        for(WeatherForecast.Weather weather : weatherList) {
            if (weather.getDate().equals(dateToDisplay)) {
                weatherForDay.add(weather);
            }
        }
        HourlyWeatherAdapter hourlyWeatherAdapter = new HourlyWeatherAdapter(weatherForDay, context);
        holder.rvHourlyWeather.setAdapter(hourlyWeatherAdapter);
        Log.e("onBindViewHolder", Integer.toString(weatherList.size()));
    }

    @Override
    public int getItemCount() {
        HashSet<LocalDate> uniqueDates = new HashSet<>();
        for (WeatherForecast.Weather weather : weatherList) {
            uniqueDates.add(weather.getDate());
        }
        return uniqueDates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView rvHourlyWeather;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rvHourlyWeather = itemView.findViewById(R.id.rv_days_weather);
            rvHourlyWeather.setLayoutManager(new LinearLayoutManager(itemView.getContext(), LinearLayoutManager.HORIZONTAL, false));
        }
    }
}

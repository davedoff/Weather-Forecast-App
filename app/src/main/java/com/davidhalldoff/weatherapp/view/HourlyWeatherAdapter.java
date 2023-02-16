package com.davidhalldoff.weatherapp.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.davidhalldoff.weatherapp.R;
import com.davidhalldoff.weatherapp.model.WeatherForecast;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder> {

    private ArrayList<WeatherForecast.Weather> weatherList;
    private Context context;

    public HourlyWeatherAdapter(ArrayList<WeatherForecast.Weather> weatherList, Context context) {
        this.weatherList = weatherList;
        this.context = context;
    }

    @NonNull
    @Override
    public HourlyWeatherAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_weather_item, parent, false);
        return new HourlyWeatherAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HourlyWeatherAdapter.ViewHolder holder, int position) {
        WeatherForecast.Weather currentWeather = weatherList.get(position);
        Double cloudCoverage = currentWeather.getCloudCoverage();
        LocalDateTime validTime = currentWeather.getValidTime();
        String validTimeStr = validTime.toString();
        validTimeStr = validTimeStr.replace("T", " ");
        String temperatureStr = currentWeather.getTemperature().toString();

        holder.tvValidTime.setText(validTimeStr);
        holder.ivCloudCoverage.setImageDrawable(ContextCompat.getDrawable(context, CloudCoverageImages.getCloudCoverageImg(cloudCoverage, validTime)));
        holder.tvTemperature.setText(temperatureStr+"\u00B0"+"C");
    }

    @Override
    public int getItemCount() {
        return weatherList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvValidTime, tvTemperature;
        private AppCompatImageView ivCloudCoverage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvValidTime = itemView.findViewById(R.id.tv_validTime);
            tvTemperature = itemView.findViewById(R.id.tv_temperature);
            ivCloudCoverage = itemView.findViewById(R.id.iv_cloudCoverage);
        }
    }
}

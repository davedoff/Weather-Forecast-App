package com.davidhalldoff.weatherapp.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.davidhalldoff.weatherapp.FavoritesActivity;
import com.davidhalldoff.weatherapp.R;
import com.davidhalldoff.weatherapp.WeatherActivity;
import com.davidhalldoff.weatherapp.io.FileIO;
import com.davidhalldoff.weatherapp.model.User;
import com.davidhalldoff.weatherapp.model.WeatherForecast;

import java.util.ArrayList;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.ViewHolder> {
    private ArrayList<String> locations;
    private Context context;

    public FavoritesAdapter(ArrayList<String> locations, Context context) {
        this.locations = locations;
        this.context = context;
    }

    @NonNull
    @Override
    public FavoritesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_favorites_item, parent, false);
        return new FavoritesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String location = locations.get(position);
        holder.tvFavoriteLocation.setText(location);

        holder.bDeleteLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("onClick", "DeleteLocation");
                User user = User.getInstance();
                user.removeFavoriteLocation(locations.get(position));
                locations.remove(position);
                FileIO.saveUser(user, context);

                notifyItemRemoved(position);
            }
        });

        holder.cvFavoriteLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedLocation = locations.get(position);
                WeatherForecast selectedWeatherForecast = null;
                User user = User.getInstance();

                for (WeatherForecast weatherForecast : user.getWeatherForecasts()) {
                    if (weatherForecast.getLocation().equals(selectedLocation)) {
                        selectedWeatherForecast = weatherForecast;
                        Log.e("onClick", selectedWeatherForecast.getLocation());
                        break;
                    }
                }

                if (selectedWeatherForecast != null) {
                    Intent intent = new Intent(v.getContext(), WeatherActivity.class);
                    intent.putExtra("weatherForecast", selectedWeatherForecast);
                    v.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private AppCompatTextView tvFavoriteLocation;
        private AppCompatButton bDeleteLocation;
        private CardView cvFavoriteLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvFavoriteLocation = itemView.findViewById(R.id.tv_favoriteLocation);
            bDeleteLocation = itemView.findViewById(R.id.b_deleteLocation);
            cvFavoriteLocation = itemView.findViewById(R.id.cv_favoriteLocation);
        }
    }
}

package com.davidhalldoff.weatherapp.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.davidhalldoff.weatherapp.model.WeatherForecast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class WeatherData {

    public static void retrieveWeatherForecast(Context context, final WeatherDataCallback callback, String location) {
        RequestQueue queue = Volley.newRequestQueue(context);

        //String locationUrl = "https://maceo.sth.kth.se/weather/search?location=" + location;
        String locationUrl = "https://www.smhi.se/wpt-a/backend_solr/autocomplete/search/{" + location + "}";

        JsonArrayRequest locationJsonArrayRequest = new JsonArrayRequest(Request.Method.GET, locationUrl, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response == null || response.length() == 0) {
                    callback.onWeatherDataError("Location not found");
                    return;
                }

                JSONObject firstPlace = response.optJSONObject(0);
                double longitude = firstPlace.optDouble("lon");
                double latitude = firstPlace.optDouble("lat");

                //String formattedUrl = "https://maceo.sth.kth.se/weather/forecast?lonLat=lon/" + longitude + "/lat/" + latitude;
                String weatherUrl = "https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/%s/lat/%s/data.json";
                String formattedUrl = String.format(weatherUrl, String.format("%.3f", longitude), String.format("%.3f", latitude));
                formattedUrl = formattedUrl.replace(",", ".");

                Log.e("Location", location);

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, formattedUrl, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            if (response == null || response.length() == 0) {
                                callback.onWeatherDataError("Found no weather data for the location");
                                return;
                            }

                            WeatherForecast weatherForecast = WeatherForecast.newInstance();

                            // set location
                            weatherForecast.setLocation(location);
                            Log.e("Location", location);

                            // set approvedTime
                            String approvedTimeStr = response.optString("approvedTime");
                            approvedTimeStr = approvedTimeStr.replace("T", " ");
                            approvedTimeStr = approvedTimeStr.replace("Z", "");
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                            LocalDateTime approvedTime = LocalDateTime.parse(approvedTimeStr, formatter);
                            weatherForecast.setApprovedTime(approvedTime);

                            // set longitude and latitude
                            JSONObject geometry = response.optJSONObject("geometry");
                            JSONArray coordinatesArray = geometry.optJSONArray("coordinates");
                            JSONArray longLatArray = coordinatesArray.optJSONArray(0);
                            double longitude = longLatArray.optDouble(0);
                            double latitude = longLatArray.optDouble(1);
                            weatherForecast.setLongitude(longitude);
                            weatherForecast.setLatitude(latitude);

                            // set weatherList
                            JSONArray timeSeries = response.optJSONArray("timeSeries");
                            ArrayList<WeatherForecast.Weather> weatherList = new ArrayList<>();
                            for (int i = 0; i < timeSeries.length(); i++) {
                                JSONObject obj = timeSeries.optJSONObject(i);
                                JSONArray parameters = obj.optJSONArray("parameters");
                                WeatherForecast.Weather weather = new WeatherForecast.Weather();

                                for (int j = 0; j < parameters.length(); j++) {
                                    JSONObject parameter = parameters.optJSONObject(j);
                                    if (parameter.optString("name").equals("t")) {
                                        JSONArray valuesArray = parameter.optJSONArray("values");
                                        double t = valuesArray.optDouble(0);
                                        weather.setTemperature(t);
                                    }
                                    if (parameter.optString("name").equals("tcc_mean")) {
                                        JSONArray valuesArray = parameter.optJSONArray("values");
                                        double tcc_mean = valuesArray.optDouble(0);
                                        weather.setCloudCoverage(tcc_mean);
                                    }
                                }

                                // set validTime
                                String validTimeStr = obj.optString("validTime");
                                validTimeStr = validTimeStr.replace("T", " ");
                                validTimeStr = validTimeStr.replace("Z", "");
                                LocalDateTime validTime = LocalDateTime.parse(validTimeStr, formatter);
                                weather.setValidTime(validTime);
                                weather.setDate(validTime.toLocalDate());
                                weatherList.add(weather);
                            }
                            weatherForecast.setWeatherList(weatherList);
                            callback.onWeatherDataRetrieved(weatherForecast);
                            Log.e("approvedTime", weatherForecast.getApprovedTime().toString());
                            Log.e("validTime", weatherForecast.getWeatherList().get(1).getValidTime().toString());
                            Log.e("longitude", weatherForecast.getLongitude().toString());
                            Log.e("latitude", weatherForecast.getLatitude().toString());
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error
                        }
                    });
                queue.add(jsonObjectRequest);
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
            }
        });
        queue.add(locationJsonArrayRequest);
    }
}

package com.davidhalldoff.weatherapp;

import static java.lang.Integer.valueOf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinnerUpdateInterval;
    private AppCompatButton bSave, bToggleTheme;
    private SharedPreferences sp;
    private Integer updateIntervalMinutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout content = findViewById(R.id.content_container);
        getLayoutInflater().inflate(R.layout.activity_settings, content);

        navigationView.setCheckedItem(R.id.nav_settings);

        bSave = findViewById(R.id.b_save);
        bSave.setOnClickListener(this::onSave);

        bToggleTheme = findViewById(R.id.b_toggleTheme);
        bToggleTheme.setOnClickListener(this::onToggleTheme);

        spinnerUpdateInterval = findViewById(R.id.spin_updateInterval);
        ArrayAdapter<CharSequence> adapterUpdateInterval = ArrayAdapter.createFromResource(this, R.array.updateInterval, R.layout.spinner_item);
        adapterUpdateInterval.setDropDownViewResource(R.layout.spinner_dropdown_item);
        spinnerUpdateInterval.setAdapter(adapterUpdateInterval);
        spinnerUpdateInterval.setOnItemSelectedListener(this);

        sp = this.getSharedPreferences("userSettings", Context.MODE_PRIVATE);
        setSpinnerUpdateIntervalSettings();

    }

    private void onToggleTheme(View view) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
    }

    private void setSpinnerUpdateIntervalSettings() {
        switch (sp.getInt("updateIntervalSettings", 0)) {
            case 10:
                spinnerUpdateInterval.setSelection(0);
                break;
            case 30:
                spinnerUpdateInterval.setSelection(1);
                break;
            case 60:
                spinnerUpdateInterval.setSelection(2);
                break;
            case 120:
                spinnerUpdateInterval.setSelection(3);
                break;
            default:
                break;
        }
    }

    private void onSave(View view) {
        try {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.putInt("updateIntervalSettings", updateIntervalMinutes);
        editor.apply();
        Toast.makeText(this, "Saved settings", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e("Save settings error:", e.getMessage());
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String choice = parent.getItemAtPosition(position).toString();
        updateIntervalMinutes = valueOf(choice);
        Log.e("OnSelected", Integer.toString(updateIntervalMinutes));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
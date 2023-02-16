package com.davidhalldoff.weatherapp.view;

import com.davidhalldoff.weatherapp.R;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class CloudCoverageImages {
    public static int getCloudCoverageImg(double cloudCoverage, LocalDateTime validTime) {
        int drawable = 0;
        LocalDateTime morningTime = validTime.with(LocalTime.of(6,0));
        LocalDateTime nightTime =  validTime.with(LocalTime.of(18,0));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        if (validTime.isBefore(nightTime) && validTime.isAfter(morningTime)) {
            switch ((int) cloudCoverage) {
                case 0:
                    drawable = R.drawable.day_1;
                    break;
                case 1:
                    drawable = R.drawable.day_2;
                    break;
                case 2:
                    drawable = R.drawable.day_3;
                    break;
                case 3:
                    drawable = R.drawable.day_4;
                    break;
                case 4:
                    drawable = R.drawable.day_5;
                    break;
                case 5:
                    drawable = R.drawable.day_6;
                    break;
                case 6:
                    drawable = R.drawable.day_7;
                    break;
                case 7:
                    drawable = R.drawable.day_8;
                    break;
                case 8:
                    drawable = R.drawable.day_9;
                    break;
                case 9:
                    drawable = R.drawable.day_10;
                    break;
                case 10:
                    drawable = R.drawable.day_11;
                    break;
                case 11:
                    drawable = R.drawable.day_12;
                    break;
                case 12:
                    drawable = R.drawable.day_13;
                    break;
                case 13:
                    drawable = R.drawable.day_14;
                    break;
                case 14:
                    drawable = R.drawable.day_15;
                    break;
                case 15:
                    drawable = R.drawable.day_16;
                    break;
                case 16:
                    drawable = R.drawable.day_17;
                    break;
                case 17:
                    drawable = R.drawable.day_18;
                    break;
                case 18:
                    drawable = R.drawable.day_19;
                    break;
                case 19:
                    drawable = R.drawable.day_20;
                    break;
                case 20:
                    drawable = R.drawable.day_21;
                    break;
                case 21:
                    drawable = R.drawable.day_22;
                    break;
                case 22:
                    drawable = R.drawable.day_23;
                    break;
                case 23:
                    drawable = R.drawable.day_24;
                    break;
                case 24:
                    drawable = R.drawable.day_25;
                    break;
                case 25:
                    drawable = R.drawable.day_26;
                    break;
                case 26:
                    drawable = R.drawable.day_27;
                    break;
            }
        } else {
            //Log.e("DayOrNight", "nightTime");

            switch ((int) cloudCoverage) {
                case 0:
                    drawable = R.drawable.night_1;
                    break;
                case 1:
                    drawable = R.drawable.night_2;
                    break;
                case 2:
                    drawable = R.drawable.night_3;
                    break;
                case 3:
                    drawable = R.drawable.night_4;
                    break;
                case 4:
                    drawable = R.drawable.night_5;
                    break;
                case 5:
                    drawable = R.drawable.night_6;
                    break;
                case 6:
                    drawable = R.drawable.night_7;
                    break;
                case 7:
                    drawable = R.drawable.night_8;
                    break;
                case 8:
                    drawable = R.drawable.night_9;
                    break;
                case 9:
                    drawable = R.drawable.night_10;
                    break;
                case 10:
                    drawable = R.drawable.night_11;
                    break;
                case 11:
                    drawable = R.drawable.night_12;
                    break;
                case 12:
                    drawable = R.drawable.night_13;
                    break;
                case 13:
                    drawable = R.drawable.night_14;
                    break;
                case 14:
                    drawable = R.drawable.night_15;
                    break;
                case 15:
                    drawable = R.drawable.night_16;
                    break;
                case 16:
                    drawable = R.drawable.night_17;
                    break;
                case 17:
                    drawable = R.drawable.night_18;
                    break;
                case 18:
                    drawable = R.drawable.night_19;
                    break;
                case 19:
                    drawable = R.drawable.night_20;
                    break;
                case 20:
                    drawable = R.drawable.night_21;
                    break;
                case 21:
                    drawable = R.drawable.night_22;
                    break;
                case 22:
                    drawable = R.drawable.night_23;
                    break;
                case 23:
                    drawable = R.drawable.night_24;
                    break;
                case 24:
                    drawable = R.drawable.night_25;
                    break;
                case 25:
                    drawable = R.drawable.night_26;
                    break;
                case 26:
                    drawable = R.drawable.night_27;
                    break;
            }
        }
        return drawable;
    }
}

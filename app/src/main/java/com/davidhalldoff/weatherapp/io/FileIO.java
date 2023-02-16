package com.davidhalldoff.weatherapp.io;

import android.content.Context;
import android.util.Log;

import com.davidhalldoff.weatherapp.model.User;
import com.davidhalldoff.weatherapp.model.WeatherForecast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileIO {
    public static void saveUser(User user, Context context) {
        try {
            String fileName = "User.ser";
            FileOutputStream fileOut = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(user);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static User loadUser(Context context) {
        try {
            String fileName = "User.ser";
            FileInputStream fileIn = context.openFileInput(fileName);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            User user = (User) in.readObject();
            in.close();
            fileIn.close();
            return user;
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
        }
        return null;
    }
}

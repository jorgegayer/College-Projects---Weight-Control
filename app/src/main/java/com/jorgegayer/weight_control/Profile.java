package com.jorgegayer.weight_control;

import static android.content.Intent.getIntent;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.ContactsContract;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Profile {
    private ProfileData profileInfo;
    public SQLiteDatabase db;

    public Profile() {
        profileInfo = new ProfileData();
        db = MainActivity.db;
    }

    boolean checkProfile() {
        return false;
    }

    public ProfileData get() {
        ProfileData userProfile = new ProfileData();

        try {
            Cursor query = db.rawQuery("SELECT * FROM Profile",null);
            int nameIndex = query.getColumnIndex("name");
            int weightIndex = query.getColumnIndex("weight");
            int heightIndex = query.getColumnIndex("height");
            int goalIndex = query.getColumnIndex("goal");

            query.moveToFirst();
            if (query == null) return null;

            userProfile.name = query.getString(nameIndex);
            userProfile.weight = Float.parseFloat(query.getString(weightIndex).toString());
            userProfile.height = Float.parseFloat(query.getString(heightIndex).toString());
            userProfile.weightGoal = Float.parseFloat(query.getString(goalIndex).toString());
            userProfile.togo = userProfile.weightGoal - userProfile.weight;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userProfile;
    }

    public boolean set(ProfileData userProfile) {
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS Profile(userid INTEGER PRIMARY KEY, name VARCHAR, weight DOUBLE, height DOUBLE, goal DOUBLE)");
            db.execSQL("delete from Profile");
            String sql;
            sql = "INSERT INTO Profile (name, weight, height, goal) VALUES ('" + userProfile.name + "'," + userProfile.weight + "," + userProfile.height + "," + userProfile.weightGoal + " )";
            db.execSQL(sql);
            Weight weight = new Weight();
            WeightData myData = new WeightData();
            Calendar cal = Calendar.getInstance();
            Date todaysdate = cal.getTime();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            myData.date =  dateFormat.format(todaysdate);
            myData.weight = userProfile.weight;
            weight.set(myData);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void update(double weight) {
        try {
            String sql;
            sql = "UPDATE Profile SET weight = " + weight;
            db.execSQL(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
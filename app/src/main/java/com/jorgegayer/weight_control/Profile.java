package com.jorgegayer.weight_control;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;

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
            int togoIndex = query.getColumnIndex("togo");

            query.moveToFirst();
            if (query == null) return null;

            userProfile.name = query.getString(nameIndex);
            userProfile.weight = Float.parseFloat(query.getString(weightIndex).toString());
            userProfile.height = Float.parseFloat(query.getString(heightIndex).toString());
            userProfile.weightGoal = Float.parseFloat(query.getString(togoIndex).toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userProfile;
    }

    public boolean set(ProfileData userProfile) {
        try {

            db.execSQL("CREATE TABLE IF NOT EXISTS Profile(name VARCHAR, weight DOUBLE, height DOUBLE, togo DOUBLE)");
            db.execSQL("delete from Profile");
            String sql;
            sql = "INSERT INTO Profile (name, weight, height, togo) VALUES ('" + userProfile.name + "'," + userProfile.weight + "," + userProfile.height + "," + userProfile.weightGoal + " )";
            db.execSQL(sql);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
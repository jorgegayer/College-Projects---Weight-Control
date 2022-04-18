package com.jorgegayer.weight_control;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;


public class Weight {
    public SQLiteDatabase db;
    public Weight() {
        db = MainActivity.db;
    }
    public static String calculatedBMI = "0";
    LinkedList<WeightData> getAll() {
        LinkedList<WeightData> localHistory = new LinkedList<>();
        try {
            @SuppressLint("Recycle") Cursor query = db.rawQuery("SELECT * FROM Weight order by Date desc",null);
            int weightIndex = query.getColumnIndex("weight");
            int dateIndex = query.getColumnIndex("date");
            int bmiIndex = query.getColumnIndex("bmi");

            query.moveToFirst();
            if (query == null) return null;
            for (int count = 1; count<= query.getCount(); count++) {
                WeightData weightData = new WeightData();
                weightData.weight =query.getFloat(weightIndex);
                weightData.date =  query.getString(dateIndex);
                weightData.bmi =  round(query.getFloat(bmiIndex));
                localHistory.add(weightData);
                query.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return localHistory;
    }


    void set(WeightData myData) {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.context);
        String url = "https://body-mass-index-bmi-calculator.p.rapidapi.com/metric?weight=" + myData.weight +"&height=" + MainActivity.profile.height/100;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,
                null, response -> {
                    try {
                        calculatedBMI = response.getString ("bmi");
                        try {
                            db.execSQL("CREATE TABLE IF NOT EXISTS Weight(weightid INTEGER PRIMARY KEY, weight DOUBLE, date DATETIME, bmi VARCHAR)");
                            String sql;
                            sql = "INSERT INTO Weight (weight, date, bmi) VALUES (" + myData.weight + ",'" + myData.date + "','" + calculatedBMI + "')";
                            db.execSQL(sql);
                            Profile profile = new Profile();
                            profile.update(myData.weight);
                            Toast.makeText(MainActivity.context, "New weight saved successfully!", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Log.i("myResponse", calculatedBMI);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    Log.i("myResponse", response.toString());
                }, error -> {

                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("X-RapidAPI-Host", "body-mass-index-bmi-calculator.p.rapidapi.com");
                headers.put("X-RapidAPI-Key", "cbbb1effdbmshff313e7063b8f75p115187jsncadf1dd4abc5");
                return headers;
            }
        };
        queue.add(req);
    }

    void delete(String date) {

    }

    public static String round(double value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return Float.toString((float) tmp / factor);
    }

}

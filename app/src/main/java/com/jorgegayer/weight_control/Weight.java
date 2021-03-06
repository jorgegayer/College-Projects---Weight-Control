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
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
//Class that handles the object Weight
public class Weight {
    public SQLiteDatabase db;
    public Weight() {
        db = MainActivity.db;
    }
    public static String calculatedBMI = "0";

    //Function that brings all the weight history ordered by date desc
    LinkedList<WeightData> getAll() {
        LinkedList<WeightData> localHistory = new LinkedList<>();
        try {
            @SuppressLint("Recycle") Cursor query = db.rawQuery("SELECT * FROM Weight ORDER BY Date DESC",null);
            int weightIndex = query.getColumnIndex("weight");
            int dateIndex = query.getColumnIndex("date");
            int bmiIndex = query.getColumnIndex("bmi");

            query.moveToFirst();
            if(query == null) return null;
            for(int count = 1; count<= query.getCount(); count++) {
                WeightData weightData = new WeightData();
                weightData.weight =query.getFloat(weightIndex);
                weightData.date = query.getString(dateIndex);
                weightData.bmi = round(query.getFloat(bmiIndex));
                localHistory.add(weightData);
                query.moveToNext();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localHistory;
    }

    //Function that sets the weight for the date
    void set(WeightData myData) {
        RequestQueue queue = Volley.newRequestQueue(MainActivity.context);
        //insert the url to calculate the bmi
        String url = "https://body-mass-index-bmi-calculator.p.rapidapi.com/metric?weight=" + myData.weight + "&height=" + MainActivity.profile.height / 100;
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,
                null, response -> {
                    try {
                        calculatedBMI = response.getString ("bmi");
                        MainActivity.bmi = calculatedBMI;
                        try {
                            //create weight table id does not exist
                            db.execSQL("CREATE TABLE IF NOT EXISTS Weight(weightid INTEGER PRIMARY KEY, weight DOUBLE, date DATETIME, bmi VARCHAR)");
                            String sql;
                            boolean weightExistedBefore = false;
                            //check if there was a weight for this date before
                            if(checkWeightPerDate(myData.date)) {
                                //update the information
                                sql = "UPDATE Weight SET weight=" + myData.weight + ",  bmi='" + calculatedBMI + "' WHERE date='" + myData.date + "'";
                                weightExistedBefore = true;
                            } else {
                                //insert a new weight if not found previous information for the current date
                                sql = "INSERT INTO Weight (weight, date, bmi) VALUES (" + myData.weight + ",'" + myData.date + "','" + calculatedBMI + "')";
                                weightExistedBefore = false;
                            }
                            db.execSQL(sql);
                            Profile profile = new Profile();
                            //update profile with the updated information
                            profile.update(myData.weight);
                            if(weightExistedBefore) {
                                Toast.makeText(MainActivity.context, "You've already entered a weight for this date. Weight updated.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(MainActivity.context, "New weight saved successfully!", Toast.LENGTH_LONG).show();
                            }
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
            //insert the parameters needed to run the API
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

    //check if there is a weight set for an specific date
    public boolean checkWeightPerDate (String date) {
        WeightData localWeight = new WeightData();
        try {
            @SuppressLint("Recycle") Cursor query = db.rawQuery("SELECT * FROM Weight WHERE date='" + date + "' ORDER BY Date DESC",null);

            if (query.moveToFirst()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    //round a double value with 2 decimal places
    public static String round(double value) {
        long factor = (long) Math.pow(10, 2);
        value = value * factor;
        long tmp = Math.round(value);
        return Float.toString((float) tmp / factor);
    }
}
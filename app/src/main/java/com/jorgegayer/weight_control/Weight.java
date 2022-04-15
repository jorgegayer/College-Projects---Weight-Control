package com.jorgegayer.weight_control;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Weight {
    private ArrayList<WeightData> weightHistory;
    public SQLiteDatabase db;
    public Weight() {
        db = MainActivity.db;
    }

    LinkedList<WeightData> getAll() {
        LinkedList<WeightData> localHistory = new LinkedList<>();
        try {
            Cursor query = db.rawQuery("SELECT * FROM Weight",null);
            int weightIndex = query.getColumnIndex("weight");
            int dateIndex = query.getColumnIndex("date");
            int bmiIndex = query.getColumnIndex("bmi");

            query.moveToFirst();
            if (query == null) return null;
            for (int count = 1; count<= query.getCount(); count++) {
                WeightData weightData = new WeightData();
                weightData.weight =query.getFloat(weightIndex);
                weightData.date =  query.getString(dateIndex);
                weightData.bmi =  query.getFloat(bmiIndex);
                localHistory.add(weightData);
                query.moveToNext();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return localHistory;
    }

    WeightData get(String date) {
        WeightData myData = new WeightData();
        return myData;
    }

    void set(WeightData myData) {
        float bmi = caculateBMI();
        try {
            db.execSQL("CREATE TABLE IF NOT EXISTS Weight(weightid INTEGER PRIMARY KEY, weight DOUBLE, date DATETIME, bmi DOUBLE)");
            String sql;
            sql = "INSERT INTO Weight (weight, date, bmi) VALUES (" + myData.weight + ",'" + myData.date + "'," + bmi + " )";
            db.execSQL(sql);
            Profile profile = new Profile();
            profile.update(myData.weight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void delete(String date) {

    }

    private float caculateBMI () {
        float localBMI = 0;

//        String url = "https://body-mass-index-bmi-calculator.p.rapidapi.com/metric?weight=150&height=1.83";
//        StringRequest stringRequest;
//        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, url,
//                null, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//                Toast.makeText(MainActivity.context, response.toString(), Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("X-RapidAPI-Host", "body-mass-index-bmi-calculator.p.rapidapi.com");
//                headers.put("X-RapidAPI-Key", "cbbb1effdbmshff313e7063b8f75p115187jsncadf1dd4abc5");
//                return headers;
//            }
//        };

        return localBMI;
    }

    private float caculateToGo () {
        float localToGo = 0;
        return localToGo;
    }
}

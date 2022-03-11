package com.jorgegayer.weight_control;

import java.util.ArrayList;

public class Weight {
    private ArrayList<WeightData> weightHistory;

    public Weight() {

    }

    ArrayList<WeightData> getAll() {
        ArrayList<WeightData> localHistory = new ArrayList<WeightData>();
        return localHistory;
    }

    WeightData get(String date) {
        WeightData myData = new WeightData();
        return myData;
    }

    void set(WeightData myData) {

    }

    void delete(String date) {

    }

    private float caculateBMI () {
        float localBMI = 0;
        return localBMI;
    }

    private float caculateToGo () {
        float localToGo = 0;
        return localToGo;
    }
}

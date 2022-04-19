package com.jorgegayer.weight_control;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    //public static SQLiteDatabase db;
    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static ProfileData profile;
    public static SQLiteDatabase db;
    private TextView txtCurrentWeight;
    private TextView txtGoal;
    private TextView txtToGo;

    Profile userProfile = new Profile();

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        mRecyclerView = findViewById(R.id.recyclerview);
        createDatabase();
        userProfile.db = db;
        profile = userProfile.get();
        txtCurrentWeight = findViewById(R.id.txtCurrentWeightHistory);
        txtGoal= findViewById(R.id.txtWeightGoalHistory);
        txtToGo= findViewById(R.id.txtToGoHistory);


        if (profile.name == null) {
            startActivity(new Intent(MainActivity.this, ProfilePage.class));
            populatePanel();
        } else {
            populateWeight();
            populatePanel();
        }
    }

    private void populatePanel() {
        profile = userProfile.get();
        txtCurrentWeight.setText(Float.toString(MainActivity.profile.weight));
        txtGoal.setText(Float.toString(MainActivity.profile.weightGoal));
        txtToGo.setText(Float.toString(MainActivity.profile.togo));
    }


    public void populateWeight() {
        Weight weight = new Weight();
        LinkedList<WeightData> mWeightList = weight.getAll();
        // Get a handle to the RecyclerView
        if (mWeightList.size() != 0) {
            // Create an adapter and supply the data to be displayed
            WeightListAdapter mAdapter = new WeightListAdapter(this, mWeightList);
            // Connect the adapter with the RecyclerView
            mRecyclerView.setAdapter(mAdapter);
//                // Give the RecyclerView a default layout manager
            mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

    public void createDatabase() {
        try {
            db = this.openOrCreateDatabase("WeightControl", MODE_PRIVATE, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addWeight(View view) {
        Intent intent = new Intent(MainActivity.this, AddWeight.class);
        intent.putExtra("goal", profile.weightGoal);
        intent.putExtra("weight", profile.weightGoal);
        intent.putExtra("togo", profile.togo);
         startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Include the profile menu option on the Main screen
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Call the profile page activity when clicking on the menu option
        startActivity(new Intent(MainActivity.this, ProfilePage.class));
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        new Handler().postDelayed(() -> {
            populatePanel();
            populateWeight();
        }, 2000);

    }
}
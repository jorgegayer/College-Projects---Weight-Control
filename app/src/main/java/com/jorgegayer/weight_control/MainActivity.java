package com.jorgegayer.weight_control;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    //public static SQLiteDatabase db;
    public static Context context;
    public static ProfileData profile;
    public static SQLiteDatabase db;
    Profile userProfile = new Profile();

    private LinkedList<WeightData> mWeightList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private WeightListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        mRecyclerView = findViewById(R.id.recyclerview);
        createDatabase();
        userProfile.db = db;
        profile = userProfile.get();

        if (profile.name == null) {
            startActivity(new Intent(MainActivity.this, ProfilePage.class));
        } else {
            populateWeight();
        }
    }

    public void populateWeight() {
        Weight weight = new Weight();
        mWeightList = weight.getAll();
        // Get a handle to the RecyclerView
        if (mWeightList.size() != 0) {
            // Create an adapter and supply the data to be displayed
            mAdapter = new WeightListAdapter(this, mWeightList);
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
    // Temporary use
    public void callProfile(View view) {
        startActivity(new Intent(MainActivity.this, ProfilePage.class));
    }

    public void addWeight(View view) {
        Intent intent = new Intent(MainActivity.this, AddWeight.class);
        intent.putExtra("goal", profile.weightGoal);
        intent.putExtra("weight", profile.weightGoal);
        intent.putExtra("togo", profile.togo);
         startActivity(intent);
        populateWeight();
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
        populateWeight();
    }
}
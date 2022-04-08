package com.jorgegayer.weight_control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //public static SQLiteDatabase db;
    public static Context context;
    public static ProfileData profile;
    public static SQLiteDatabase db;
    Profile userProfile = new Profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        createDatabase();
        userProfile.db = db;
        profile = userProfile.get();

        if (profile.name == null) {
            startActivity(new Intent(MainActivity.this, ProfilePage.class));
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
    }
}
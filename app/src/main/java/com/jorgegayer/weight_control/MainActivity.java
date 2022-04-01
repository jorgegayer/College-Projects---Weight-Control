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
    public static SQLiteDatabase db;
    public static Context context;
    public static ProfileData profile;

    protected void createDatabase() {
        try {
            db = this.openOrCreateDatabase("WeightControl", MODE_PRIVATE, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        db.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();

        createDatabase();

        profile = checkProfile();
        if (profile != null) {
            startActivity(new Intent(MainActivity.this, ProfilePage.class));
        }
    }

    private ProfileData checkProfile() {
        ProfileData userProfile = new ProfileData();

        try {
            db = getApplicationContext().openOrCreateDatabase("WeightControl", MODE_PRIVATE, null);
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
        db.close();

        return userProfile;
    }

    // Temporary use
    public void callProfile(View view) {
        startActivity(new Intent(MainActivity.this, ProfilePage.class));
    }

    public void addWeight(View view) {
        startActivity(new Intent(MainActivity.this, AddWeight.class));
    }
}
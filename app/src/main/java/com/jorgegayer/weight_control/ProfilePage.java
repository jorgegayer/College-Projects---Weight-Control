package com.jorgegayer.weight_control;

import androidx.appcompat.app.AppCompatActivity;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfilePage extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextWeight;
    private EditText editTextHeight;
    private EditText editTextGoal;
    private Button saveProfile;
    private SQLiteDatabase db;
    ProfileData userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        editTextName = findViewById(R.id.editTextName);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextGoal = findViewById(R.id.editTextGoal);
        saveProfile = findViewById(R.id.btnSaveProfile);

        if (savedInstanceState != null) {
            editTextName.setText(savedInstanceState.getString("editTextName"));
            editTextWeight.setText(savedInstanceState.getString("editTextWeight"));
            editTextHeight.setText(savedInstanceState.getString("editTextHeight"));
            editTextGoal.setText(savedInstanceState.getString("editTextGoal"));
        } else {
            editTextName.setText(MainActivity.profile.name);
            editTextWeight.setText(Float.toString(MainActivity.profile.weight));
            editTextHeight.setText(Float.toString(MainActivity.profile.height));
            editTextGoal.setText(Float.toString(MainActivity.profile.weightGoal));
        }
    }

    private boolean validateFields() {
        if(editTextName.length() == 0 || editTextWeight.length() == 0 || editTextHeight.length() == 0 || editTextGoal.length() == 0) {
            Toast.makeText(this, R.string.completeFields, Toast.LENGTH_LONG).show();
            return false;
        }
        if ( Float.parseFloat(editTextWeight.getText().toString()) < 20 || Float.parseFloat(editTextWeight.getText().toString()) > 1000) {
            Toast.makeText(this, R.string.validateWeight, Toast.LENGTH_LONG).show();
            return false;
        }

        if ( Float.parseFloat(editTextGoal.getText().toString()) < 20 || Float.parseFloat(editTextGoal.getText().toString()) > 1000) {
            Toast.makeText(this, R.string.validateGoal, Toast.LENGTH_LONG).show();
            return false;
        }
        if ( Float.parseFloat(editTextHeight.getText().toString()) < 20 || Float.parseFloat(editTextHeight.getText().toString()) > 300) {
            Toast.makeText(this, R.string.validateHeight, Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    public void saveProfile(View view) {
            String name;
            float weight;
            float height;
            float goal;

            if (validateFields()) {
                userProfile = new ProfileData();
                name = editTextName.getText().toString();
                weight = Float.parseFloat(editTextWeight.getText().toString());
                height = Float.parseFloat(editTextHeight.getText().toString());
                goal = Float.parseFloat(editTextGoal.getText().toString());

                userProfile.name = name;
                userProfile.weight = weight;
                userProfile.height = height;
                userProfile.weightGoal = goal;

                // Save the information into the database
                boolean savedProfile = saveProfile(userProfile);
                Toast.makeText(this, R.string.profileUpdated, Toast.LENGTH_LONG).show();
                // Return to Home Page
                finish();
            }
        }

        private boolean saveProfile(ProfileData userProfile) {
            try {
                db = MainActivity.db;
                db = MainActivity.context.openOrCreateDatabase("WeightControl", MainActivity.context.MODE_PRIVATE, null);
                db.execSQL("CREATE TABLE IF NOT EXISTS Profile(name VARCHAR, weight DOUBLE, height DOUBLE, togo DOUBLE)");
                String sql;
                if (userProfile.name == "") {
                     sql = "INSERT INTO Profile (name, weight, height, togo) VALUES ('" + userProfile.name + "'," + userProfile.weight + "," + userProfile.height + "," + userProfile.weightGoal + " )";
                } else {
                     sql = "UPDATE Profile set name='" + userProfile.name + "', weight=" +userProfile.weight + ", height=" +userProfile.height + ", togo=" +userProfile.weightGoal +  "";
                }
                db.execSQL(sql);
            return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putString("editTextName", editTextName.getText().toString());
            outState.putString("editTextHeight", editTextHeight.getText().toString());
            outState.putString("editTextWeight", editTextWeight.getText().toString());
            outState.putString("editTextGoal", editTextGoal.getText().toString());
        }
    }
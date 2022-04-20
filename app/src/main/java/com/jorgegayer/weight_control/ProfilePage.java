package com.jorgegayer.weight_control;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Objects;

public class ProfilePage extends AppCompatActivity {
    private EditText editTextName;
    private EditText editTextWeight;
    private EditText editTextHeight;
    private EditText editTextGoal;
    Profile userProfile;
    ProfileData userData;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        userProfile = new Profile();
        editTextName = findViewById(R.id.editTextName);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextGoal = findViewById(R.id.editTextGoal);

        if(savedInstanceState != null) {
            editTextName.setText(savedInstanceState.getString("editTextName"));
            editTextWeight.setText(savedInstanceState.getString("editTextWeight"));
            editTextHeight.setText(savedInstanceState.getString("editTextHeight"));
            editTextGoal.setText(savedInstanceState.getString("editTextGoal"));
            Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        } else {
            userData = userProfile.get();
            if(userData.name != null) {
                editTextName.setText(userData.name);
                editTextWeight.setText(Float.toString(userData.weight));
                editTextHeight.setText(Float.toString(userData.height));
                editTextGoal.setText(Float.toString(userData.weightGoal));
                Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    private boolean validateFields() {
        if(editTextName.length() == 0 || editTextWeight.length() == 0 || editTextHeight.length() == 0 || editTextGoal.length() == 0) {
            Toast.makeText(this, R.string.completeFields, Toast.LENGTH_LONG).show();
            return false;
        }
        if( Float.parseFloat(editTextWeight.getText().toString()) < 20 || Float.parseFloat(editTextWeight.getText().toString()) > 1000) {
            Toast.makeText(this, R.string.validateWeight, Toast.LENGTH_LONG).show();
            return false;
        }

        if( Float.parseFloat(editTextGoal.getText().toString()) < 20 || Float.parseFloat(editTextGoal.getText().toString()) > 1000) {
            Toast.makeText(this, R.string.validateGoal, Toast.LENGTH_LONG).show();
            return false;
        }
        if( Float.parseFloat(editTextHeight.getText().toString()) < 20 || Float.parseFloat(editTextHeight.getText().toString()) > 300) {
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
                ProfileData profileData = new ProfileData();
                name = editTextName.getText().toString();
                weight = Float.parseFloat(editTextWeight.getText().toString());
                height = Float.parseFloat(editTextHeight.getText().toString());
                goal = Float.parseFloat(editTextGoal.getText().toString());

                profileData.name = name;
                profileData.weight = weight;
                profileData.height = height;
                profileData.weightGoal = goal;
                profileData.togo = goal-weight;

                // Save the information into the database
                userProfile = new Profile();
                userProfile.set(profileData);
                Toast.makeText(this, R.string.profileUpdated, Toast.LENGTH_LONG).show();
                MainActivity.profile = profileData;
                // Return to Home Page
                finish();
            }
        }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
        public void onSaveInstanceState(@NonNull Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putString("editTextName", editTextName.getText().toString());
            outState.putString("editTextHeight", editTextHeight.getText().toString());
            outState.putString("editTextWeight", editTextWeight.getText().toString());
            outState.putString("editTextGoal", editTextGoal.getText().toString());
        }
    }
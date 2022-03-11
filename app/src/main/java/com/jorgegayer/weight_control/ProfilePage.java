package com.jorgegayer.weight_control;

import androidx.appcompat.app.AppCompatActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        editTextName = findViewById(R.id.editTextName);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextGoal = findViewById(R.id.editTextGoal);
        saveProfile = findViewById(R.id.btnSaveProfile);

        if (savedInstanceState !=null) {
            editTextName.setText(savedInstanceState.getString("editTextName"));
            editTextWeight.setText(savedInstanceState.getString("editTextWeight"));
            editTextHeight.setText(savedInstanceState.getString("editTextHeight"));
            editTextGoal.setText(savedInstanceState.getString("editTextGoal"));
        }

    }
    private boolean validateFields() {
        if(editTextName.length() == 0 || editTextWeight.length() == 0 || editTextHeight.length() == 0 || editTextGoal.length() == 0) {
            Toast.makeText(this, R.string.completeFields, Toast.LENGTH_LONG).show();
            return false;
        }
        if ( Float.parseFloat(editTextWeight.getText().toString()) < 20 || Float.parseFloat(editTextWeight.getText().toString()) >1000) {
            Toast.makeText(this, R.string.validateWeight, Toast.LENGTH_LONG).show();
            return false;
        }

        if ( Float.parseFloat(editTextGoal.getText().toString()) < 20 || Float.parseFloat(editTextGoal.getText().toString()) >1000) {
            Toast.makeText(this, R.string.validateTextGoal, Toast.LENGTH_LONG).show();
            return false;
        }
        if ( Float.parseFloat(editTextHeight.getText().toString()) <= 20 || Float.parseFloat(editTextHeight.getText().toString()) >300) {
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
                name = editTextName.getText().toString();
                weight = Float.parseFloat(editTextWeight.getText().toString());
                height = Float.parseFloat(editTextHeight.getText().toString());
                goal = Float.parseFloat(editTextGoal.getText().toString());
                //Save the information into the database
                Toast.makeText(this, R.string.profileUpdated, Toast.LENGTH_LONG).show();
                finish();
            }
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



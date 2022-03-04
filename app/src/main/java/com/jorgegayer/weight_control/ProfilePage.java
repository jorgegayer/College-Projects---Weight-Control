package com.jorgegayer.weight_control;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ProfilePage extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextWeight;
    private EditText editTextHeight;
    private EditText editTextGoal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        editTextName = findViewById(R.id.editTextName);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextGoal = findViewById(R.id.editTextGoal);
    }

    public void saveProfile(View view) {
        String name;
        float weight;
        float height;
        float goal;

        if(editTextName.length() == 0 || editTextWeight.length() == 0 || editTextHeight.length() == 0 || editTextGoal.length() == 0) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_LONG).show();
        }
        else {
            name = editTextName.getText().toString();
            weight = Float.parseFloat(editTextWeight.getText().toString());
            height = Float.parseFloat(editTextHeight.getText().toString());
            goal = Float.parseFloat(editTextGoal.getText().toString());
            //Save the information into the database
        }
    }
}
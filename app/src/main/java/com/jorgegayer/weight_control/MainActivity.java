package com.jorgegayer.weight_control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void callProfile(View view) {
        startActivity(new Intent(MainActivity.this, ProfilePage.class));

    }

    public void showDate(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    public void processDatePickerResult(int year, int month, int day) {
        String msg = Integer.toString(month) + "/" + Integer.toString(day) + "/" +
                Integer.toString(year) + ".";
        Toast.makeText(this, "Date: " + msg, Toast.LENGTH_SHORT).show();
    }
}
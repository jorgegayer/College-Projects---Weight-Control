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

    // Temporary use
    public void callProfile(View view) {
        startActivity(new Intent(MainActivity.this, ProfilePage.class));
    }


    public void addWeight(View view) {
        startActivity(new Intent(MainActivity.this, AddWeight.class));
    }
}
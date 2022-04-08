package com.jorgegayer.weight_control;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddWeight extends AppCompatActivity {
    private EditText txtWeight;
    private TextView txtGoal;
    private TextView txtToGo;
    private TextView lblDate;
    public ProfileData userProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weight);

        userProfile = MainActivity.profile;
        txtWeight = findViewById(R.id.txtWeight);
        lblDate = findViewById(R.id.lblDate);
        txtGoal = findViewById(R.id.txtGoal);
        txtToGo = findViewById(R.id.txtToGo);




        loadFields();
        if (savedInstanceState != null) {
            txtWeight.setText(savedInstanceState.getString("txtWeight"));
            lblDate.setText(savedInstanceState.getString("lblDate"));
            txtGoal.setText(savedInstanceState.getString("txtGoal"));
            txtToGo.setText(savedInstanceState.getString("txtToGo"));
        }

    }

    private void loadFields() {
        Calendar cal = Calendar.getInstance();
        Date todaysdate = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Bundle extras = getIntent().getExtras();
        lblDate.setText(dateFormat.format(todaysdate));
        txtWeight.setText(Double.toString(userProfile.weight));
        txtGoal.setText(Double.toString(userProfile.weightGoal));
        txtToGo.setText(Double.toString(userProfile.togo));
    }

    // Here now just to have part of the implementation done
    public void showDate(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    // Here now just to have part of the implementation done
    public void processDatePickerResult(int year, int month, int day) {
        String receivedDate = Integer.toString(day) + "/" + Integer.toString(month+1) + "/" + Integer.toString(year);
        lblDate.setText( receivedDate);
    }

    public void saveWeight(View view) {
        float weight;
        String date;

        try {
            if (validateFields()) {
                weight = Float.parseFloat(txtWeight.getText().toString());
                date = lblDate.getText().toString();
                // Save the information into the database
                // ...
                Toast.makeText(this, "New weight saved successfully!", Toast.LENGTH_LONG).show();
                // Return to Home Page
                finish();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private boolean validateFields() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        Date todaysdate = cal.getTime();
        Date receivedDate = dateFormat.parse(lblDate.getText().toString());

        if(txtWeight.length() == 0 ) {
            Toast.makeText(this, R.string.completeFields, Toast.LENGTH_LONG).show();
            return false;
        }

        if ( Float.parseFloat(txtWeight.getText().toString()) < 20 || Float.parseFloat(txtWeight.getText().toString()) > 1000) {
            Toast.makeText(this, R.string.validateWeight, Toast.LENGTH_LONG).show();
            return false;
        }

        if (receivedDate.after(todaysdate)) {
            Toast.makeText(this, "The date cannot be in the future!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("txtWeight", txtWeight.getText().toString());
        outState.putString("txtGoal", txtGoal.getText().toString());
        outState.putString("txtToGo", txtToGo.getText().toString());
        outState.putString("lblDate", lblDate.getText().toString());
    }

}
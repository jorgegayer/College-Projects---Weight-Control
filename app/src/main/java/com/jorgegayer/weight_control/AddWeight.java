package com.jorgegayer.weight_control;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
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
    private TextView txtCurrentWeight;
    private TextView txtGoal;
    private TextView txtToGo;
    private TextView lblDate;
    public ProfileData userProfile;
    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_weight);
        context = getApplicationContext();
        userProfile = MainActivity.profile;
        txtWeight = findViewById(R.id.txtWeight);
        lblDate = findViewById(R.id.lblDate);
        txtGoal = findViewById(R.id.txtGoal);
        txtToGo = findViewById(R.id.txtToGo);
        txtCurrentWeight =findViewById(R.id.txtCurrentWeightAdd);
        loadFields();
        if (savedInstanceState != null) {
            txtWeight.setText(savedInstanceState.getString("txtWeight"));
            lblDate.setText(savedInstanceState.getString("lblDate"));
            txtGoal.setText(savedInstanceState.getString("txtGoal"));
            txtToGo.setText(savedInstanceState.getString("txtToGo"));
            txtCurrentWeight.setText(savedInstanceState.getString("txtCurrentWeight"));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        txtCurrentWeight.setText( Double.toString(userProfile.weight));
    }

    // Here now just to have part of the implementation done
    public void showDate(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "DatePicker");
    }

    // Here now just to have part of the implementation done
    public void processDatePickerResult(int year, int month, int day) {
        String sDay = Integer.toString(day).length()==2? Integer.toString(day): "0" + Integer.toString(day);
        String sMonth = Integer.toString(month+1).length()==2? Integer.toString(month+1): "0" + Integer.toString(month+1);
        String receivedDate = sDay + "/" + sMonth + "/" + Integer.toString(year);
        lblDate.setText( receivedDate);
    }

    public void saveWeight(View view) {
        WeightData weightData = new WeightData();
        Weight weight = new Weight();
        try {
            if (validateFields()) {
                weightData.weight = Float.parseFloat(txtWeight.getText().toString());
                weightData.date = lblDate.getText().toString();
                weightData.bmi = "0";
                weight.set(weightData);
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
        Weight weight = new Weight();

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
            outState.putString("txtCurrentWeight", txtCurrentWeight.getText().toString());
        }
    };



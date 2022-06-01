package com.example.carservice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UpdateCarActivity extends AppCompatActivity implements View.OnClickListener,DatePickerFragment.OnDateSetListener {
    View view;
    EditText kmDate, oilDate, beltDate, repairShopDate, tyresDate;
    EditText kmNew;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Locale locale;
    private DialogFragment datePicker;
    FirebaseDatabase database;
    Button buttonUpdate;
    String name,mOilDate,mRepairShopDate,mTimingBeltDate,mTyresDate,mKmNew;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_car);

        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            name= null;
        } else {
            name= extras.getString("name");
            mOilDate= extras.getString("oilDate");
            mRepairShopDate= extras.getString("repairShopDate");
            mTimingBeltDate= extras.getString("timingBeltDate");
            mTyresDate= extras.getString("tyresDate");
            mKmNew= extras.getString("kmNew");

        }



        buttonUpdate=findViewById(R.id.buttonUpdate);
        kmNew=findViewById(R.id.editTextKmUpdate);
        oilDate = findViewById(R.id.editTextOilUpdate);
        beltDate = findViewById(R.id.editTextTimingBeltUpdate);
        repairShopDate = findViewById(R.id.editTextRepairShopUpdate);
        tyresDate = findViewById(R.id.editTextTyresUpdate);



        oilDate.setText(mOilDate);
        beltDate.setText(mTimingBeltDate);
        repairShopDate.setText(mRepairShopDate);
        tyresDate.setText(mTyresDate);
        kmNew.setText(mKmNew);


        kmNew.setOnClickListener(this);
        oilDate.setOnClickListener(this);
        beltDate.setOnClickListener(this);
        repairShopDate.setOnClickListener(this);
        tyresDate.setOnClickListener(this);
        buttonUpdate.setOnClickListener(this);


        locale = getResources().getConfiguration().locale;
        datePicker = new DatePickerFragment();




    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.editTextOilUpdate:
                datePicker.show(getSupportFragmentManager(), "datePicker");
                break;
            case R.id.editTextRepairShopUpdate:
                datePicker.show(getSupportFragmentManager(), "datePicker");
                break;

            case R.id.editTextTimingBeltUpdate:
                datePicker.show(getSupportFragmentManager(), "datePicker");
                break;

            case R.id.editTextTyresUpdate:
                datePicker.show(getSupportFragmentManager(), "datePicker");
                break;

            case R.id.buttonUpdate:

                String Km=kmNew.getText().toString();
                String oil=oilDate.getText().toString();
                String timingBelt=beltDate.getText().toString();
                String repairShop=repairShopDate.getText().toString();
                String tyres=tyresDate.getText().toString();


                DatabaseReference car = database.getInstance("https://carservice-e5777-default-rtdb.europe-west1.firebasedatabase.app").getReference("users")
                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cars").child(name);

             Map<String,Object> map=new HashMap<>();
                map.put("kmNew",Km);
                map.put("oilDate",oil);
                map.put("repairShopDate",repairShop);
                map.put("timingBeltDate",timingBelt);
                map.put("tyresDate",tyres);
             car.updateChildren(map);
             finish();




        }

    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        final Calendar calendar = Calendar.getInstance(locale);
        final DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
        String date=dayOfMonth+"/"+month+"/" +year;

        if (getCurrentFocus() != null) {

            switch (getCurrentFocus().getId()) {

                case R.id.editTextOilUpdate:
                    oilDate.setText(date);
                    break;
                case R.id.editTextTimingBeltUpdate:
                    beltDate.setText(date);
                    break;
                case R.id.editTextRepairShopUpdate:
                    repairShopDate.setText(date);
                    break;
                case R.id.editTextTyresUpdate:
                    tyresDate.setText(date);
                    break;
            }
        }
    }





}
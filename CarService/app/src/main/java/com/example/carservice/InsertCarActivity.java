package com.example.carservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class InsertCarActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText carname,oil,timingbelt,repairshop,tyres,kmInitial;
    private Button insertbutton;
    private TextView returntoprofile,selectDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_car);


        kmInitial=findViewById(R.id.editTextTextCarKm2);
        carname=findViewById(R.id.editTextTextCarName);
        oil=findViewById(R.id.editTextTextOil);
        timingbelt=findViewById(R.id.editTextTextTimingBelt);
        repairshop=findViewById(R.id.editTextTextRepairShop);
        tyres=findViewById(R.id.editTextTextTyres);

        insertbutton=findViewById(R.id.buttonInsert);
        insertbutton.setOnClickListener(this);

        returntoprofile=findViewById(R.id.textViewReturnToProfile);
        returntoprofile.setOnClickListener(this);
        selectDate=findViewById(R.id.textViewSelectDate);
        selectDate.setOnClickListener(this);



        mDateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                Log.d("Calendar","onDataSet:dd/mm/yyyy: "+day+"/"+month+"/"+year+"/");
                Toast.makeText(InsertCarActivity.this, "Value is in datepicker", Toast.LENGTH_SHORT).show();

                String date=+day+"/"+month+"/"+year;
                selectDate.setText(date);
            }
        };



    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.textViewReturnToProfile:
                startActivity(new Intent(this,ProfileActivity.class));
                break;

            case R.id.buttonInsert:
                InsertCar();
                break;

            case R.id.textViewSelectDate:
                InsertDate();
                break;


        }
    }
    public void InsertCar(){
        String CarName=carname.getText().toString().trim();
        String Oil=oil.getText().toString().trim();
        String TimingBelt=timingbelt.getText().toString().trim();
        String RepairShop=repairshop.getText().toString().trim();
        String Tyres=tyres.getText().toString().trim();
        String kminitialDate=selectDate.getText().toString();
        String Km=kmInitial.getText().toString();
        final Calendar calendar = Calendar.getInstance(getResources().getConfiguration().locale);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        if(CarName.isEmpty())
        {   carname.setText("Unnamed");
            CarName=carname.getText().toString().trim();
        }
        if(Oil.isEmpty())
        {
            oil.setText("No information");
            Oil=oil.getText().toString().trim();
        }
        if(TimingBelt.isEmpty())
        {
            timingbelt.setText("No information");
            TimingBelt=timingbelt.getText().toString().trim();
        }
        if(RepairShop.isEmpty())
        {
             repairshop.setText("No information");
            RepairShop=repairshop.getText().toString().trim();
        }
        if(Tyres.isEmpty())
        {
            tyres.setText("No information");
            Tyres=tyres.getText().toString().trim();
        }

        Car car=new Car(CarName,Oil,TimingBelt,RepairShop,Tyres,calendar,kminitialDate,Km);

        FirebaseDatabase.getInstance("https://carservice-e5777-default-rtdb.europe-west1.firebasedatabase.app").getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cars").child(car.name).setValue(car).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful())
                    {
                        Toast.makeText(InsertCarActivity.this, "Value has successfully inserted", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(InsertCarActivity.this, "Failed Insert! Try again", Toast.LENGTH_SHORT).show();
                    }
                }
        });
    }

    public void InsertDate(){

        Calendar cal= Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog=new DatePickerDialog(InsertCarActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,mDateSetListener,year,month,day);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();


    }



}
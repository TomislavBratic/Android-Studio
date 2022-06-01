package com.example.carservice;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.OnItemClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;


public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyViewHolder>  {

    Context context;
    ArrayList<Car> list;
    Car car1;
    String dateDiff;
    private DatePickerDialog.OnDateSetListener mDateSetListener;




    public CarAdapter(Context context, ArrayList<Car> list) {
        this.context = context;
        this.list = list;
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView oil,timingBelt,repairShop,tyres,carName,kmNew,oilDay,timingBeltDay,repairShopDay,tyresDay,Datediff;
        Button EditButton,DeleteButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            kmNew=itemView.findViewById(R.id.textViewkmNew);
            oil=itemView.findViewById(R.id.textViewOil);
            timingBelt=itemView.findViewById(R.id.textViewTimingBelt);
            repairShop=itemView.findViewById(R.id.textViewRepairShop);
            tyres=itemView.findViewById(R.id.textViewTyres);
            oilDay=itemView.findViewById(R.id.textViewOilDay);
            timingBeltDay=itemView.findViewById(R.id.textViewTimingBeltDay);
            repairShopDay=itemView.findViewById(R.id.textViewRepairShopDay);
            tyresDay=itemView.findViewById(R.id.textViewTyresDay);
            Datediff=itemView.findViewById(R.id.textViewDateDiff);
            carName=itemView.findViewById(R.id.textViewCarName);



            EditButton=itemView.findViewById(R.id.buttonEditRecycleView);
            DeleteButton=itemView.findViewById(R.id.buttonDeleteRecycleView);
        }
    }


    @NonNull
    @Override
    public CarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.car_item,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.MyViewHolder holder, int position)  {

        Car car=list.get(position);
        holder.oil.setText(car.getOilDate());
        holder.timingBelt.setText(car.getTimingBeltDate());
        holder.repairShop.setText(car.getRepairShopDate());
        holder.tyres.setText(car.getTyresDate());
        holder.kmNew.setText(car.getKmNew());
        holder.carName.setText(car.getName());



        DatabaseReference ref = FirebaseDatabase.getInstance("https://carservice-e5777-default-rtdb.europe-west1.firebasedatabase.app").getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cars").child(car.getName());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                car1=snapshot.getValue(Car.class);

                String initalDate=car1.kmInitalDate;
                String oilDate=car1.oilDate;
                String repairShopDate=car1.repairShopDate;
                String timingBeltDate=car1.timingBeltDate;
                String tyresDate=car1.tyresDate;
                final Calendar calendar = Calendar.getInstance();


                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                month=month+1;

                String newDate=day +"/"+month+"/"+year;



                int initalkm=Integer.valueOf(car1.kmInitial);
                int newKm=Integer.valueOf(car1.kmNew);
                int Oil=Integer.valueOf(car1.oil);
                int repairShop=Integer.valueOf(car1.repairShop);
                int timingBelt=Integer.valueOf(car1.timingBelt);
                int tyres=Integer.valueOf(car1.tyres);
                int avgKm;



                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                long date;
                long Oildate1;
                long repairshopdate1;
                long timingBelt1;
                long tyresDate1;



                try {
                    Date date1 = sdf.parse(newDate);
                    Date date2=sdf.parse(initalDate);
                    Date dateOil=sdf.parse(oilDate);
                    Date dateRepairShop=sdf.parse(repairShopDate);
                    Date dateTimingBelt=sdf.parse(timingBeltDate);
                    Date dateTyres=sdf.parse(tyresDate);


                    Log.d("JELi","aaaaaaaaaa" + date1 + date2);
                    long diff = date1.getTime() - date2.getTime();
                    date= TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                    holder.kmNew.setText(String.valueOf(date));
                    Log.d("ASJASDJASDJASOD","JE li:"+date);
                    avgKm=(newKm-initalkm)/(int)date;
                    Log.d("ASJASDJASDJASOD","JeliAVg:"+avgKm);

                     diff = date1.getTime() - dateOil.getTime();
                     Oildate1=TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
                     int resultOil=(Oil-avgKm*(int)Oildate1)/avgKm;

                     if(resultOil<0)
                    holder.oilDay.setText(String.valueOf("late for:"+resultOil*(-1)+"days"));
                     else
                         holder.oilDay.setText(String.valueOf("change for:"+resultOil+"days"));


                    diff = date1.getTime() - dateRepairShop.getTime();
                    repairshopdate1=TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                    int resultRepairShop=(repairShop-avgKm*(int)repairshopdate1)/avgKm;
                    if(resultRepairShop<0)
                        holder.repairShopDay.setText(String.valueOf( "late for:"+resultRepairShop*(-1)+"days"));
                    else
                        holder.repairShopDay.setText(String.valueOf("change for:"+resultRepairShop+"days"));




                    diff = date1.getTime() - dateTimingBelt.getTime();
                    timingBelt1=TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                    int resultTimingBelt=(timingBelt-avgKm*(int)timingBelt1)/avgKm;

                    if(resultTimingBelt<0)
                        holder.timingBeltDay.setText(String.valueOf( "late for:"+resultTimingBelt*(-1)+"days"));
                    else
                        holder.timingBeltDay.setText(String.valueOf("change for:"+resultTimingBelt+"days"));



                    diff = date1.getTime() - dateTyres.getTime();
                    tyresDate1=TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

                    int resultTyres=(tyres-avgKm*(int)timingBelt1)/avgKm;

                    if(resultTyres<0)
                        holder.tyresDay.setText(String.valueOf( "late for:"+resultTyres*(-1)+"days"));
                    else
                        holder.tyresDay.setText(String.valueOf("change for:"+resultTyres+"days"));



                    holder.Datediff.setText(String.valueOf("avg per day:"+avgKm+"km"));

                    car1.Update= car1.Update+1;


                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        holder.EditButton.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,UpdateCarActivity.class);
                intent.putExtra("name",car.getName());
                intent.putExtra("oilDate",car.getOilDate());
                intent.putExtra("repairShopDate",car.getRepairShopDate());
                intent.putExtra("timingBeltDate",car.getTimingBeltDate());
                intent.putExtra("tyresDate",car.getTyresDate());
                intent.putExtra("kmNew",car.getKmNew());
                context.startActivity(intent);
                notifyDataSetChanged();


            }







        });


        holder.DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(context);
                builder.setTitle("Delete Panel");
                builder.setMessage("Delete?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance("https://carservice-e5777-default-rtdb.europe-west1.firebasedatabase.app").getReference("Cars")
                                .child(car.name).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}

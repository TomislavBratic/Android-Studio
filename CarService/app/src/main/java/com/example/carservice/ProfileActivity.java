package com.example.carservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity  {

    private Button logOut;

    RecyclerView recyclerView;
    DatabaseReference database;
    CarAdapter carAdapter;
    ArrayList<Car> list;
    FloatingActionButton insertActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        logOut=(Button)findViewById(R.id.buttonLogOut);

        recyclerView=findViewById(R.id.CarListRecyclerView);
        database= FirebaseDatabase.getInstance("https://carservice-e5777-default-rtdb.europe-west1.firebasedatabase.app").getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Cars");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProfileActivity.this));

        list=new ArrayList<>();
        carAdapter=new CarAdapter(ProfileActivity.this,list);
        recyclerView.setAdapter(carAdapter);
        insertActionButton=findViewById(R.id.floatingActionButtonInsert);




        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {   if(dataSnapshot.exists())
                {
                    Car car=dataSnapshot.getValue(Car.class);
                    list.add(car);
                    Log.d("dataSnapshot", "FOUND DATA");
                }

                else{
                    Log.d("dataSnapshot", "NO DATA");
                }
                }
                carAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();

                startActivity(new Intent(ProfileActivity.this,MainActivity.class));

            }
        });

        insertActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this,InsertCarActivity.class));
            }
        });
    }


}
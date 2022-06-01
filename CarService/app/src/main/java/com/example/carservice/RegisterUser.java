package com.example.carservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText Name,Age,Email,Password;
    private Button registerbutton;
    private TextView returntologin;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        mAuth=FirebaseAuth.getInstance();

        Name=findViewById(R.id.editTextTextPersonName);
        Age=findViewById(R.id.editTextTextAge);
        Email=findViewById(R.id.editTextTextEmailRegister);
        Password=findViewById(R.id.editTextTextPasswordRegister);

        registerbutton=findViewById(R.id.buttonRegister);
        registerbutton.setOnClickListener(this);
        progressBar=findViewById(R.id.progressBar);


        returntologin=findViewById(R.id.textViewReturnToLogin);
        returntologin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.textViewReturnToLogin:
                startActivity(new Intent(this,MainActivity.class));
                break;

            case R.id.buttonRegister:
                registerUser();
                break;

        }
    }

    public void registerUser(){
        String email=Email.getText().toString().trim();
        String password=Password.getText().toString().trim();
        String fullName=Name.getText().toString().trim();
        String age=Age.getText().toString().trim();

        if(fullName.isEmpty())
        {
            Name.setError("Name is Required");
            Name.requestFocus();
            return;
        }
        if(age.isEmpty())
        {
            Age.setError("Age is Required");
            Age.requestFocus();
            return;
        }
        if(email.isEmpty())
        {
            Email.setError("Email is Required");
            Email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            Email.setError("Provide Correct Email Address");
            Email.requestFocus();
            return;
        }

        if(password.isEmpty())
        {
            Password.setError("Password is Required");
            Password.requestFocus();
            return;
        }

        if(password.length()<6){
            Password.setError("Password needs to be at least 6 characters long");
            Password.requestFocus();
            return;
        }
    progressBar.setVisibility(View.VISIBLE);
    mAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        User user=new User(fullName,age,email);

                        FirebaseDatabase.getInstance("https://carservice-e5777-default-rtdb.europe-west1.firebasedatabase.app").getReference("users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(RegisterUser.this, "User has been registered", Toast.LENGTH_SHORT).show();
                                    }
                                else
                                    {
                                        Toast.makeText(RegisterUser.this, "Failed Registration! Try again", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                    }

                                }
                            }
                        );
                    }
                    else{
                        Toast.makeText(RegisterUser.this, "Failed to  register", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }

                }
            });


    }
}
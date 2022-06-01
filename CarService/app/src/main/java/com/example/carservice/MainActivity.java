package com.example.carservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register;
    private EditText emailLogIn,passwordLogIn;
    private Button logIn;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register=(TextView)findViewById(R.id.textViewRegister);
        register.setOnClickListener(this);

        emailLogIn=(EditText)findViewById(R.id.editTextTextEmailAddress);
        passwordLogIn=(EditText) findViewById(R.id.editTextTextPassword);

        logIn=(Button)findViewById(R.id.buttonLogin);
        logIn.setOnClickListener(this);

        mAuth=FirebaseAuth.getInstance();


    }

    @Override
    public void onClick(View view) {
            switch(view.getId()){
                case R.id.textViewRegister:
                    startActivity(new Intent(this,RegisterUser.class));
                    break;
                case R.id.buttonLogin:
                    userLogin();
                    break;

            }
    }
    public void userLogin(){
        String email=emailLogIn.getText().toString().trim();
        String password=passwordLogIn.getText().toString().trim();

        if(email.isEmpty())
        {
            emailLogIn.setError("email is Wrong.Try again!");
            emailLogIn.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            emailLogIn.setError("Provide Correct Email Address");
            emailLogIn.requestFocus();
            return;
        }
        if(password.isEmpty())
        {
            passwordLogIn.setError("password is Wrong.Try again!");
            passwordLogIn.requestFocus();
            return;
        }
        if(password.length()<6){
            passwordLogIn.setError("Password needs to be at least 6 characters long");
            passwordLogIn.requestFocus();
            return;
        }
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(MainActivity.this,ProfileActivity.class));
                }
                else{
                    Toast.makeText(MainActivity.this, "Failed to sign in.Please Check your Credentials!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }
}
package com.example.e_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView passTV;
    private TextView emailTV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();


        Button login = findViewById(R.id.login);
        Button addEmployee = findViewById(R.id.addEmployee);
        emailTV = findViewById(R.id.emailtv);
        passTV = findViewById(R.id.passtv);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTV.getText().toString();
                String password = passTV.getText().toString();
               // FirebaseAuth.getInstance().getCurrentUser().getEmail();
                if(!email.isEmpty() && !password.isEmpty()) {
                    loginAccount(email, password);
                }else{
                    Toast.makeText(LoginActivity.this, "Enter details", Toast.LENGTH_SHORT).show();
                }
            }
        });
        addEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTV.getText().toString();
                String password = passTV.getText().toString();
                if(!email.isEmpty() && !password.isEmpty()){
                    NewEmployee(email,password);
                }else{
                    Toast.makeText(LoginActivity.this, "Enter details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private void loginAccount(String email, String Password) {
        mAuth.signInWithEmailAndPassword(email, Password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    updateUI(user, "login");
                }
                else{
                    Toast.makeText(LoginActivity.this, "User Not Found", Toast.LENGTH_SHORT).show();
                    emailTV.setText("");
                    passTV.setText("");
                }
            }
        });
    }
    private void NewEmployee(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this,
                new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            mAuth.getCurrentUser().updateEmail(email);
                            updateUI(user,"createAccount");
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Invalid Email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    private void updateUI(FirebaseUser user, String status) {
        if(Objects.equals(status, "login")){if(user != null) {

            startActivity(new Intent(LoginActivity.this,Dashboard.class));
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
        }
       }
        else{

            startActivity(new Intent(LoginActivity.this,AddFaculty.class));
        }
    }

   @Override
   protected void onStart() {

        if(mAuth.getCurrentUser() != null){

            startActivity(new Intent(LoginActivity.this, Dashboard.class));
        }
        super.onStart();
    }
}

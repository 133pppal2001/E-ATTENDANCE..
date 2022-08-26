package com.example.e_attendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Dashboard extends AppCompatActivity {
    private  FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        String email=FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        Toast.makeText(this, email, Toast.LENGTH_SHORT).show();

        mAuth=FirebaseAuth.getInstance();

        Button prof=findViewById(R.id.profile);
        Button logout=findViewById(R.id.logout);
        Button attend=findViewById(R.id.btnattendence);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mAuth.signOut();
            finish();
            startActivity(new Intent(Dashboard.this,LoginActivity.class));
                Toast.makeText(Dashboard.this, "Logout Successful", Toast.LENGTH_SHORT).show();

            }
        });


        prof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Dashboard.this,Profile.class));
            }
        });
        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(Dashboard.this,MarkAttendance.class));
            }
        });


    }
}
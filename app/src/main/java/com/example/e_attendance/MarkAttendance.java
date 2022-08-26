package com.example.e_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MarkAttendance extends AppCompatActivity {
private FirebaseAuth mAuth;
private TextView Displaydate;
private Button checkin;
private TextView inputid;
private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_attendance);

        Displaydate = findViewById(R.id.displaydate);
        checkin=findViewById(R.id.checkin);
        inputid=findViewById(R.id.inputid);


        Calendar cal = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            cal = Calendar.getInstance();

            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            date = month + 1 + "-" + day + "-" + year;

        Displaydate.setText(date);
    }

        checkin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadattendance();
            }
        });
    }

    private void uploadattendance() {
        String id = inputid.getText().toString();
        id=id.replace("@","");
        String finalid=id.replace(".","");

        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Employee Details");//to get details of user
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String email= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail().toString();
        email=email.replace("@","");
        String finalEmail=email.replace(".","");
        final DatabaseReference dbref = database.getReference("Attendance");


        ref.child(finalEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (finalEmail.equals(finalid)){
                    dbref.child(date).child(finalEmail).setValue(snapshot.getValue()).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MarkAttendance.this, "Attendance Updated", Toast.LENGTH_SHORT).show();

                        }
                    });
            }
                else{
                    Toast.makeText(MarkAttendance.this, "Wrong email id", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
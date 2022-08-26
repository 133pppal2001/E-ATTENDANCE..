package com.example.e_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.e_attendance.databinding.ActivityAddFacultyBinding;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class AddFaculty extends AppCompatActivity {
    private ActivityAddFacultyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityAddFacultyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadValues();
            }
        });
    }
    private void uploadValues() {
        String Fname = binding.firstNameTV.getText().toString();
        String Lname = binding.lastNameTV.getText().toString();
        String address = binding.addressTV.getText().toString();
        String contactNo = binding.contactTV.getText().toString();


        crAccData  valUpload = new crAccData(Fname,Lname,address,contactNo);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).updatePhoneNumber((PhoneAuthCredential) binding.contactTV.getText());
      String email= Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail().toString();
        email=email.replace("@","");
        email=email.replace(".","");
        DatabaseReference dbref = database.getReference("Employee Details");
        dbref.child(email).setValue(valUpload).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddFaculty.this, "Sucessfully uploaded data", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(AddFaculty.this,Dashboard.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddFaculty.this,"Upload Failed: "+e,Toast.LENGTH_LONG).show();
                binding.firstNameTV.setText("");
                binding.lastNameTV.setText("");
                binding.contactTV.setText("");
                binding.addressTV.setText("");
            }
        });
    }
}




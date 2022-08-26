package com.example.e_attendance;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.e_attendance.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Profile extends AppCompatActivity {

    private ActivityProfileBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        displayProfile();
        binding.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateValues();
            }
        });
    }

    private void UpdateValues() {
        String Fname = binding.firstname.getText().toString();
        String Lname = binding.lastname.getText().toString();
        String address = binding.address.getText().toString();
        String contactNo = binding.contact.getText().toString();

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
                Toast.makeText(Profile.this, "Sucessfully Updated Data", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(Profile.this,Dashboard.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(Profile.this,"Update Failed: "+e,Toast.LENGTH_LONG).show();
                binding.firstname.setText("");
                binding.lastname.setText("");
                binding.contact.setText("");
                binding.address.setText("");
            }
        });
    }

    private void displayProfile() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
       // Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).updatePhoneNumber((PhoneAuthCredential) binding.contact.getText());
       String email = Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getEmail().toString();
        email=email.replace("@","");
        email=email.replace(".","");
        DatabaseReference dbref = database.getReference("Employee Details");
        dbref.child(email)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                       String addressFromDB= Objects.requireNonNull(snapshot.child("address").getValue()).toString();
                       String contactFromDB= Objects.requireNonNull(snapshot.child("contactNo").getValue()).toString();
                       String firstFromDB= Objects.requireNonNull(snapshot.child("firstName").getValue()).toString();
                       String lastFromDB= Objects.requireNonNull(snapshot.child("lastName").getValue()).toString();

                       binding.address.setText(addressFromDB);
                       binding.contact.setText(contactFromDB);
                       binding.firstname.setText(firstFromDB);
                       binding.lastname.setText(lastFromDB);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}
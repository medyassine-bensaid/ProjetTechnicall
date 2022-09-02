package com.example.projettechnicall;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.projettechnicall.databinding.FragmentTechnicianBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class TechnicianFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FragmentTechnicianBinding binding;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final FirebaseDatabase database2 = FirebaseDatabase.getInstance();
    private DatabaseReference DataRefU = database.getReference("Users");
    private DatabaseReference DataRefS = database2.getReference("Specialities");
    private StorageReference SRefO = FirebaseStorage.getInstance().getReference();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        binding = FragmentTechnicianBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        binding.profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(TechnicianFragment.this)
                        .navigate(R.id.action_technicianFragment_to_profileManagementFragment);
            }
        });

        binding.Services.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(TechnicianFragment.this)
                        .navigate(R.id.action_technicianFragment_to_mainServicesFragment);
            }
        });

        binding.rdvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(TechnicianFragment.this)
                        .navigate(R.id.action_technicianFragment_to_rendezVousFragment);
            }
        });

        DataRefU.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                binding.Login.setText(dataSnapshot.child(currentUser.getUid()).getValue(User.class).full_name);
                SRefO.child("ProfilePics/"+currentUser.getUid()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(getContext()).load(uri).transform(new CircleTransform()).into(binding.profilepicture);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        DataRefS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    binding.Speciality.setText(dataSnapshot
                            .child(currentUser
                                    .getUid())
                            .getValue().toString());
                } catch (Exception e) {}
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
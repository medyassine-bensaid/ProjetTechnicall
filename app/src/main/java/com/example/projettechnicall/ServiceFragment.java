package com.example.projettechnicall;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projettechnicall.databinding.FragmentSecondBinding;
import com.example.projettechnicall.databinding.FragmentServiceBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ServiceFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FragmentServiceBinding binding;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference DataRef = database.getReference("Specialities");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        binding = FragmentServiceBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users");
        mRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                binding.Login.setText("Welcome "
                        +dataSnapshot.child(currentUser.getUid()).getValue(User.class).full_name
                        +"!\nYou are a ..?");
                binding.PlombierChoice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> specialities = new HashMap<>();
                        specialities.put(currentUser.getUid(), "Plombier");
                        DataRef.updateChildren(specialities);
                        NavHostFragment.findNavController(ServiceFragment.this)
                                .navigate(R.id.action_serviceFragment_to_firstFragment);
                    }
                });
                binding.ElectricienChoice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> specialities = new HashMap<>();
                        specialities.put(currentUser.getUid(), "Electricien");
                        DataRef.updateChildren(specialities);
                        NavHostFragment.findNavController(ServiceFragment.this)
                                .navigate(R.id.action_serviceFragment_to_firstFragment);
                    }
                });
                binding.ClimaticienChoice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> specialities = new HashMap<>();
                        specialities.put(currentUser.getUid(), "Climaticien");
                        DataRef.updateChildren(specialities);
                        NavHostFragment.findNavController(ServiceFragment.this)
                                .navigate(R.id.action_serviceFragment_to_firstFragment);
                    }
                });
                binding.MecanicienChoice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> specialities = new HashMap<>();
                        specialities.put(currentUser.getUid(), "Mecanicien");
                        DataRef.updateChildren(specialities);
                        NavHostFragment.findNavController(ServiceFragment.this)
                                .navigate(R.id.action_serviceFragment_to_firstFragment);
                    }
                });
                binding.MaconChoice.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String, Object> specialities = new HashMap<>();
                        specialities.put(currentUser.getUid(), "Maçon");
                        DataRef.updateChildren(specialities);
                        NavHostFragment.findNavController(ServiceFragment.this)
                                .navigate(R.id.action_serviceFragment_to_firstFragment);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
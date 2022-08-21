package com.example.projettechnicall;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projettechnicall.databinding.FragmentProfileManagementBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class ProfileManagementFragment extends Fragment {


    private FirebaseAuth mAuth;
    private FragmentProfileManagementBinding binding;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference DataRef = database.getReference("Specialities");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_profile_management, container, false);
    }
    
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users");
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                EditText et1 = ((EditText) view.findViewById(R.id.NameField));
                EditText et2 = ((EditText) view.findViewById(R.id.EmailField));
                EditText et3 = ((EditText) view.findViewById(R.id.PhoneField));
                TextView tv = ((TextView) view.findViewById(R.id.Changepwd));




                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        NavHostFragment.findNavController(ProfileManagementFragment.this)
                                .navigate(R.id.action_profileManagementFragment_to_changePwdFragment);
                    }
                });
                et1.setText(snapshot.child(currentUser.getUid()).getValue(User.class).full_name);
                et2.setText(snapshot.child(currentUser.getUid()).getValue(User.class).email);
                et3.setText(snapshot.child(currentUser.getUid()).getValue(User.class).phone);

                ((Button) view.findViewById(R.id.button5)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        System.out.println(et2.getText().toString());
                        if (et2.getText().toString().isEmpty()) {
                            et2.setError("Email is required");
                            et2.requestFocus();
                        }

                        else if (!Patterns.EMAIL_ADDRESS.matcher(et2.getText().toString()).matches()) {
                            et2.setError("Please enter a valid email");
                            et2.requestFocus();
                        }

                        else if (et1.getText().toString().isEmpty()) {
                            et1.setError("NAME is required!");
                            et1.requestFocus();
                        }
                        else if (et3.getText().toString().isEmpty()) {
                            et3.setError("ph is required!");
                            et3.requestFocus();
                        }
                        else {
                            User U = snapshot.child(currentUser.getUid()).getValue(User.class);
                            Map<String, Object> mp = new HashMap<>();
                            mp.put(currentUser.getUid(), new User(
                                    et1.getText().toString(),
                                    et2.getText().toString(),
                                    et3.getText().toString(),
                                    U.type
                            ));
                            mRef.updateChildren(mp);
                            currentUser.updateEmail(et2.getText().toString());

                            Toast.makeText(getContext(), "Data Modified with Success!", Toast.LENGTH_LONG);

                            NavHostFragment.findNavController(ProfileManagementFragment.this)
                                    .navigate(R.id.action_profileManagementFragment_to_technicianFragment);
                        }

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println(error.getCode());
            }
        });
    }
}
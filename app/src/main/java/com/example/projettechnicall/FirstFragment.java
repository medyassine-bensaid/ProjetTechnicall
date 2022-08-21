package com.example.projettechnicall;


import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.projettechnicall.databinding.FragmentFirstBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private EditText editTextEmail , editTextPassword ;
    private Button login ;
    private Button SignUp ;
    private FirebaseAuth mAuth ;





    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        binding.editTextTextPassword3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (binding.editTextTextPassword3.getRight() - binding.editTextTextPassword3.getCompoundDrawables()[0].getBounds().width())) {
                        int I = binding.editTextTextPassword3.getInputType();
                        if (I==(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME)){
                            binding.editTextTextPassword3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            binding.editTextTextPassword3.setTypeface(ResourcesCompat.getFont(getContext(), R.font.archivo_black));
                        }
                        else if (I==(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                            binding.editTextTextPassword3.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
                            binding.editTextTextPassword3.setTypeface(ResourcesCompat.getFont(getContext(), R.font.archivo_black));
                        }
                        return true;
                    }
                }
                return false;
            }
        });
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_firstFragment_to_signUp);

            }
        });

        binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

    }

    private void userLogin() {
        String email = binding.editTextTextPersonName.getText().toString();

        String password = binding.editTextTextPassword3.getText().toString();

        if (email.isEmpty()){
            binding.editTextTextPersonName.setError("Email is required");
            binding.editTextTextPersonName.requestFocus();
            return;
        }

      //  if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
      //      binding.editTextTextPersonName.setError("Please enter a valid email");
      //      binding.editTextTextPersonName.requestFocus();
      //      return;
      //  }

        if (password.isEmpty()) {
            binding.editTextTextPassword3.setError("Password is required!");
            binding.editTextTextPassword3.requestFocus();
            return;
        }

        if (password.length() <6) {
            binding.editTextTextPassword3.setError("Min password length is 6 characters!");
            binding.editTextTextPassword3.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    DatabaseReference DatarefS = FirebaseDatabase.getInstance().getReference("Specialities");

                    DatarefS.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean b = snapshot.child(mAuth.getCurrentUser().getUid()).exists();
                            if (b) {
                                NavHostFragment.findNavController(FirstFragment.this)
                                        .navigate(R.id.action_firstFragment_to_technicianFragment);
                            }
                            else {
                                NavHostFragment.findNavController(FirstFragment.this)
                                        .navigate(R.id.action_firstFragment_to_serviceFragment);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            System.out.println("The read failed: " + error.getCode());
                        }
                    });
                }
                else
                    Toast.makeText(getActivity().getApplication(), "Email or password is invalid!" , Toast.LENGTH_LONG).show();
            };
        });


//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        binding = null;
//    }

}}
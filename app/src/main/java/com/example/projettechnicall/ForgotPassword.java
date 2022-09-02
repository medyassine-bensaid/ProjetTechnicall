package com.example.projettechnicall;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projettechnicall.databinding.FragmentForgotPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ForgotPassword extends Fragment {
    private FragmentForgotPasswordBinding binding ;
    private FirebaseAuth auth = FirebaseAuth.getInstance();



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((Button)view.findViewById(R.id.resetPassBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }

            private void resetPassword() {
                String email = ((EditText) getView().findViewById(R.id.emailResetPass)).getText().toString();

                if (email.isEmpty()){
                    ((EditText) view.findViewById(R.id.emailResetPass)).setError("Email is required");
                    view.findViewById(R.id.emailResetPass).requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    ((EditText) view.findViewById(R.id.emailResetPass)).setError("Please enter a valid email");
                    view.findViewById(R.id.emailResetPass).requestFocus();
                    return;
                }

                auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getContext(), "check your email to reset your password !", Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getContext(), "Try again !", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}
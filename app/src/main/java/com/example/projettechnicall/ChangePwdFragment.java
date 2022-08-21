package com.example.projettechnicall;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.projettechnicall.databinding.FragmentChangePwdBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthCredential;

public class ChangePwdFragment extends Fragment {
    private FragmentChangePwdBinding binding;
/*private EditText oldp , newp , confp ;
private Button confirm ;*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentChangePwdBinding.inflate(getLayoutInflater());


    }

    private void updatePassword(String oldp, String confp) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        OAuthCredential authCredential = (OAuthCredential) EmailAuthProvider.getCredential(user.getEmail(), oldp);
        user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                user.updatePassword(confp).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "password updated", Toast.LENGTH_SHORT).show();
                        NavHostFragment.findNavController(ChangePwdFragment.this)
                                .navigate(R.id.action_changePwdFragment_to_profileManagementFragment);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                View newview = getView();
                Toast.makeText(getContext(), "error occured!", Toast.LENGTH_SHORT).show();
                ((EditText) newview.findViewById(R.id.Oldpassfield)).setError("Verify your password");
                newview.findViewById(R.id.Oldpassfield).requestFocus();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_pwd, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//
//

        /*
        ((EditText)viewfindViewById(R.id.Oldpassfield)).setText("YAHYAW");
        ((EditText)view.findViewById(R.id.Oldpassfield)).setText("YAHYAWAFI");
        ((EditText)view.findViewById(R.id.Oldpassfield)).setText("YAHYAWAFI");
        */
//


        view.findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String oldp = ((EditText) getView().findViewById(R.id.Oldpassfield)).getText().toString();
                String newp = ((EditText) getView().findViewById(R.id.Newpassfield)).getText().toString();
                String confp = ((EditText) getView().findViewById(R.id.Confirmnewpassfield)).getText().toString();

                View newview = getView();
                if (oldp.isEmpty()) {
                    ((EditText) newview.findViewById(R.id.Oldpassfield)).setError("Password is required!");
                    newview.findViewById(R.id.Oldpassfield).requestFocus();
                    return;
                }

                if (oldp.length() < 6) {
                    ((EditText) newview.findViewById(R.id.Oldpassfield)).setError("Min password length is 6 characters!");
                    newview.findViewById(R.id.Oldpassfield).requestFocus();
                    return;
                }
                if (newp.isEmpty()) {
                    ((EditText) newview.findViewById(R.id.Newpassfield)).setError("Password is required!");
                    newview.findViewById(R.id.Newpassfield).requestFocus();
                    return;
                }

                if (newp.length() < 6) {
                    ((EditText) newview.findViewById(R.id.Newpassfield)).setError("Min password length is 6 characters!");
                    newview.findViewById(R.id.Newpassfield).requestFocus();
                    return;
                }
                if (confp.isEmpty()) {
                    ((EditText) newview.findViewById(R.id.Confirmnewpassfield)).setError("Password is required!");
                    newview.findViewById(R.id.Confirmnewpassfield).requestFocus();
                    return;
                }

                if (confp.length() < 6) {
                    ((EditText) newview.findViewById(R.id.Confirmnewpassfield)).setError("Min password length is 6 characters!");
                    newview.findViewById(R.id.Confirmnewpassfield).requestFocus();
                    return;
                }
                if (!confp.equals(newp)) {
                    ((EditText) newview.findViewById(R.id.Newpassfield)).setError("Password and confirm password doesn't match!");
                    ((EditText) newview.findViewById(R.id.Confirmnewpassfield)).setError("Password and confirm password doesn't match!");
                    newview.findViewById(R.id.Confirmnewpassfield).requestFocus();
                    return;
                }
                FirebaseAuth mAuth = FirebaseAuth.getInstance();
                mAuth.signInWithEmailAndPassword(mAuth.getCurrentUser().getEmail(), oldp).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        ((EditText) getView().findViewById(R.id.Oldpassfield)).setError("Verify your password");
                        getView().findViewById(R.id.Oldpassfield).requestFocus();
                    }
                }).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        System.out.println("Pass" + oldp);
                        System.out.println("Pass" + newp);
                        System.out.println("Pass" + confp);
                        mAuth.getCurrentUser().updatePassword(confp);
                        NavHostFragment.findNavController(ChangePwdFragment.this)
                                .navigate(R.id.action_changePwdFragment_to_profileManagementFragment);
                    }
                });
            }
        });
    }
}
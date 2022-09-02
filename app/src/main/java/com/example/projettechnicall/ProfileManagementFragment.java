package com.example.projettechnicall;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.provider.MediaStore;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projettechnicall.databinding.FragmentProfileManagementBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProfileManagementFragment extends Fragment {


    private FirebaseAuth mAuth;
    private FragmentProfileManagementBinding binding;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference DataRef = database.getReference("Specialities");
    private StorageReference SRefO = FirebaseStorage.getInstance().getReference();
    private Uri dataURI;
    private int i = 0;

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
                EditText et1 = view.findViewById(R.id.NameField);
                EditText et2 = view.findViewById(R.id.EmailField);
                EditText et3 = view.findViewById(R.id.PhoneField);
                TextView tv = view.findViewById(R.id.Changepwd);
                ImageView iv = view.findViewById(R.id.ProfilePic);



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
                SRefO.child("ProfilePics/"+currentUser.getUid()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(getContext())
                                .load(uri)
                                .transform(new CircleTransform())
                                .into(iv);
                        ImageButton imgb = getView().findViewById(R.id.deleteButton);
                        imgb.setVisibility(View.VISIBLE);
                        i = 1;
                        imgb.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                iv.setImageResource(R.drawable.img);
                                if (imgb.getVisibility()==View.VISIBLE) {
                                    imgb.setVisibility(View.GONE);
                                    i = 0;
                                }
                                else {
                                    imgb.setVisibility(View.VISIBLE);
                                    i = 1;
                                }
                            }
                        });

                    }
                });


                ((ImageView) view.findViewById(R.id.ProfilePic)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent iGallery = new Intent(Intent.ACTION_PICK);
                        iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(iGallery, 1000);
                    }
                });

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

                            if (i==0) {
                                Task<Void> T = addToStorage(dataURI);
                                T.addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(getContext(), "Data Modified with Success!", Toast.LENGTH_LONG);

                                        NavHostFragment.findNavController(ProfileManagementFragment.this)
                                                .navigate(R.id.action_profileManagementFragment_to_technicianFragment);
                                    }
                                });
                            }
                            else {
                                Task<UploadTask.TaskSnapshot> T = addToStorage(dataURI);
                                T.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                        Toast.makeText(getContext(), "Data Modified with Success!", Toast.LENGTH_LONG);

                                        NavHostFragment.findNavController(ProfileManagementFragment.this)
                                                .navigate(R.id.action_profileManagementFragment_to_technicianFragment);
                                    }
                                });
                            }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode==1000) && (resultCode==-1)) {
            ImageView iv = getView().findViewById(R.id.ProfilePic);
            dataURI = data.getData();
            Picasso.with(getContext())
                    .load(dataURI)
                    .transform(new CircleTransform())
                    .into(iv);
            ImageButton imgb = getView().findViewById(R.id.deleteButton);
            i = 1;
            imgb.setVisibility(View.VISIBLE);
        }
    }

    private <T> T addToStorage(Uri data) {
        String link = "ProfilePics/"+ mAuth.getCurrentUser().getUid()+".jpg";
        StorageReference SRef = SRefO.child(link);
        System.out.println(i);
        if (i==0) {
            return (T)SRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getContext(), "Image uploaded with success", Toast.LENGTH_LONG);
                }
            });
        }
        else {
            return (T) SRef.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(), "Image uploaded with success", Toast.LENGTH_LONG);
                }
            });
        }
    }
}
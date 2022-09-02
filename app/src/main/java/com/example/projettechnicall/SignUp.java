package com.example.projettechnicall;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.stream.Stream;


public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference;

    private EditText fullname;
    private EditText email;
    private EditText phone;
    private EditText password;
    private EditText confirmPass;
    private Button signup, login;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private User user;
    int i = 0;

    public SignUp() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();

        fullname = (EditText) findViewById(R.id.fullname);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        phone = (EditText) findViewById(R.id.editTextPhone2);
        password = (EditText) findViewById(R.id.editTextTextPassword4);
        confirmPass = (EditText) findViewById(R.id.editTextTextPassword5);
        signup = (Button) findViewById(R.id.button3);
        signup.setOnClickListener(this);
        login = (Button) findViewById(R.id.button4);
        login.setOnClickListener(this);
        User user = new User();
        // technician = findViewById(R.id.)


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button3:
                signUp();
                break;
            case R.id.button4:
                startActivity(new Intent(this, FirstFragment.class));
                break;
        }
    }

    private void signUp() {
        String full_name = fullname.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String Phone = phone.getText().toString().trim();
        String Password = password.getText().toString().trim();
        String cp = confirmPass.getText().toString().trim();
        //na9es radio group
        RadioGroup choix = findViewById(R.id.radioGroup);
        RadioButton bt = ((RadioButton) findViewById(choix.getCheckedRadioButtonId()));
        String type = bt == null ? "" : bt.getText().toString();


        if (full_name.isEmpty()) {
            fullname.setError("full name is required !");
            fullname.requestFocus();
            return;
        }
        if (Phone.isEmpty()) {
            phone.setError("phone number is required !");
            phone.requestFocus();
            return;
        }
        if (Email.isEmpty()) {
            email.setError("email is required !");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            email.setError("Please provide a valid email !");
            email.requestFocus();
            return;
        }
        if (Password.isEmpty()) {
            password.setError("password is required !");
            password.requestFocus();
            return;
        }
        if (Password.length() < 6) {
            password.setError("min password length should be 6 characters !");
            password.requestFocus();
            return;
        }
        if (cp.isEmpty()) {
            confirmPass.setError("please confirm password !");
            confirmPass.requestFocus();
            return;
        }
        if (!cp.equals(Password)) {
            confirmPass.setError("Password and confirm password doesn't match!");
            confirmPass.requestFocus();
            return;
        }
        if (cp.length() < 6) {
            confirmPass.setError("min password length should be 6 characters !");
            confirmPass.requestFocus();
            return;
        }
        if (choix.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a provided type!", Toast.LENGTH_LONG);
            choix.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {
                    User user = new User(Email, toTitle(full_name), Phone, type);

                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(SignUp.this, "User has been registred successfully !", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), FirstFragment.class));
                            } else
                                Toast.makeText(SignUp.this, "Registration Failed !", Toast.LENGTH_LONG).show();

                        }
                    });

                } else
                    Toast.makeText(SignUp.this, "Registration Failed !", Toast.LENGTH_LONG).show();
            }

        });
    }

    public static String toTitle(String str) {
        return  Stream.of(str.split(" "))
                .map(e->e.substring(0,1).toUpperCase()+e.substring(1))
                .reduce((a, b)->a+" "+b).get();
    }
}



//    public void checkButton(/*View view*/){
//        int radioId = radioGroup.getCheckedRadioButtonId();
//        radioButton = findViewById(radioId);
//        System.out.println("Hi!");
//        System.out.println(radioButton.getText());
        //Toast.makeText(this,"selected radio button " + radioButton.getText() , Toast.LENGTH_SHORT).show()
    //}
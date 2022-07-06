package com.adiljutt.inscurancecar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.email)
    EditText emailTV;
    @BindView(R.id.password)
    EditText passwordTV;
    @BindView(R.id.register)
    Button regBtn;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.username)
    EditText username;
    @BindView(R.id.confirm_password)
    EditText confirm_password;
    @BindView(R.id.login)
    Button login;


    FirebaseDatabase database;
    DatabaseReference databaseReference;

    FirebaseAuth mAuth;
    String current_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users");

        regBtn.setOnClickListener(this);
        login.setOnClickListener(this);

    }

    private void registerNewUser() {

        String email, password,usernames,confirm_passwords;
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();
        usernames = username.getText().toString();
        confirm_passwords = confirm_password.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailTV.setError("Enter Email");
            emailTV.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            passwordTV.setError("Enter Password");
            passwordTV.requestFocus();
        }else if (TextUtils.isEmpty(usernames)) {
            username.setError("Enter UserName");
            username.requestFocus();
        } else if (TextUtils.isEmpty(confirm_passwords)) {
            confirm_password.setError("Enter Confirm Password");
            confirm_password.requestFocus();
        } else if (!confirm_passwords.equals(password)) {
            confirm_password.setError("Password Not Match");
            confirm_password.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);

                                current_user = mAuth.getUid();

                                HashMap<String, Object> messageInfoMap = new HashMap<>();
                                messageInfoMap.put("user_id", current_user);
                                messageInfoMap.put("username",usernames );
                                messageInfoMap.put("password", password);
                                messageInfoMap.put("email", email);
                                databaseReference.child(current_user).updateChildren(messageInfoMap);


                                Intent intent = new Intent(RegisterActivity.this, CitiesActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(getApplicationContext(), "Registration failed! Please try again later", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register:
                registerNewUser();
                break;
            case R.id.login:
                BacktoLogin();
                break;
        }
    }

    private void BacktoLogin() {
        Intent intent1 = new Intent(RegisterActivity.this,LoginActivity.class);
        startActivity(intent1);
        finish();
    }
}

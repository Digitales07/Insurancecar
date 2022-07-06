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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.email)
    EditText emailTV;
    @BindView(R.id.password)
    EditText passwordTV;
    @BindView(R.id.login)
    Button loginBtn;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.forgot_password)
    TextView forgot_password;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null) {
            restrict_act();
        }

        loginBtn.setOnClickListener(this);
        forgot_password.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    void restrict_act() {
        startActivity(new Intent(LoginActivity.this, CitiesActivity.class));
        finish();
    }

    private void loginUserAccount() {

        String email, password;
        email = emailTV.getText().toString();
        password = passwordTV.getText().toString();

        if (TextUtils.isEmpty(email)) {
            emailTV.setError("Enter Email");
            emailTV.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            passwordTV.setError("Enter Password");
            passwordTV.requestFocus();
        } else {
            progressBar.setVisibility(View.VISIBLE);

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);

                                Intent intent = new Intent(LoginActivity.this, CitiesActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    });
        }

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login:
                loginUserAccount();
            break;
            case R.id.forgot_password:
                Intent intent = new Intent(LoginActivity.this,ForgotActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.register:
                Intent intent1 = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent1);
                finish();
                break;
        }
    }
}

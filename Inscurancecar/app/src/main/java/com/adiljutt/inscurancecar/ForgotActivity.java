package com.adiljutt.inscurancecar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.send)
    Button send;


    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();

        send.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send:
                 resetpassword();
                break;

        }
    }

    private void resetpassword() {

        String emails = email.getText().toString();

        mAuth.sendPasswordResetEmail(emails)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
//                            Log.d(TAG, "Email sent.");
                            Toast.makeText(getApplicationContext(), "Check Your Email...", Toast.LENGTH_SHORT).show();
                            Intent intent =  new Intent(ForgotActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent =  new Intent(ForgotActivity.this,LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

package com.adiljutt.inscurancecar;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adiljutt.inscurancecar.adapter.CitiesAdapter;
import com.adiljutt.inscurancecar.model.Cities;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CitiesActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseAuth mAuth;
    String current_user;

    @BindView(R.id.logout_customer)
    LinearLayout logout_customer;
    @BindView(R.id.addpart)
    LinearLayout addpart;
    @BindView(R.id.report)
    LinearLayout report;

    //Order by Search
    @BindView(R.id.order_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.retry)
    Button retry;
    @BindView(R.id.fl_errorView)
    FrameLayout flErrorView;
    @BindView(R.id.fl_recyclerView)
    FrameLayout flRecyclerView;
    //End

    //Firebase
    FirebaseDatabase db;
    DatabaseReference pharmacies,car_parts;


    //List of New Order By Search
    List<Cities> appointments;
    CitiesAdapter adapter;


    BottomSheetDialog bottomSheetDialog;
    EditText empty_trys;
    Button add;


    BottomSheetDialog bottomSheetDialog1;
    LinearLayout voice_report;
    LinearLayout cus_report;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cities);
        ButterKnife.bind(this);

        db = FirebaseDatabase.getInstance();
        pharmacies = db.getReference("Cities");
        car_parts = db.getReference("Car_Parts");


        retry.setOnClickListener(this);
        logout_customer.setOnClickListener(this);
        addpart.setOnClickListener(this);
        report.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        current_user = mAuth.getCurrentUser().getUid();
        if (mAuth.getCurrentUser() == null) {
            restrict_act();
        }

        bottomSheetDialog = new BottomSheetDialog(this);
        View view1 = getLayoutInflater().inflate(R.layout.item_addpart,null);
        empty_trys = (EditText) view1.findViewById(R.id.empty_trys);
        add = (Button) view1.findViewById(R.id.add);
        bottomSheetDialog.setContentView(view1);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveInformationToDataBase();
            }
        });


        bottomSheetDialog1 = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.item_dialog,null);
        voice_report = (LinearLayout) view.findViewById(R.id.voice_report);
        cus_report = (LinearLayout) view.findViewById(R.id.cus_report);
        bottomSheetDialog1.setContentView(view);

        voice_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(CitiesActivity.this,INVoiceReportListActivity.class);
                startActivity(intent);
            }
        });

        cus_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(CitiesActivity.this,FullReportListActivity.class);
                startActivity(intent);
            }
        });


    }

    private void SaveInformationToDataBase() {

        hideKeyboard();

        String messageKey = car_parts.push().getKey();

        String company_names = empty_trys.getText().toString();


        if (TextUtils.isEmpty(company_names)) {
            empty_trys.setError("Enter Empty Trys");
            empty_trys.requestFocus();
        }else {

            HashMap<String, Object> messageInfoMap = new HashMap<>();
            messageInfoMap.put("part_id", messageKey);
            messageInfoMap.put("part_name", company_names);


            assert messageKey != null;
            car_parts.child(messageKey).updateChildren(messageInfoMap);

            Toast.makeText(getApplicationContext(), "New Car Part Added", Toast.LENGTH_SHORT).show();
            bottomSheetDialog.dismiss();
            empty_trys.setText("");

        }

    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) Objects.requireNonNull(getSystemService(Context.INPUT_METHOD_SERVICE))).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        flErrorView.setVisibility(View.GONE);
        flRecyclerView.setVisibility(View.VISIBLE);
        getAppoinments();
    }

    private void getAppoinments() {
        progressBar.setVisibility(View.VISIBLE);
        appointments = new ArrayList<>();
        pharmacies.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointments.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Cities medication = snapshot.getValue(Cities.class);
                        assert medication != null;
                        appointments.add(medication);
                    }
                }

                if (appointments.size() > 0){
                    progressBar.setVisibility(View.GONE);
                    setUprecyclerView();
                }else {
                    flRecyclerView.setVisibility(View.GONE);
                    flErrorView.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setUprecyclerView() {
        Collections.reverse(appointments);
        adapter = new CitiesAdapter( CitiesActivity.this,appointments);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.retry:
                flErrorView.setVisibility(View.GONE);
                flRecyclerView.setVisibility(View.VISIBLE);
                getAppoinments();
                break;
            case R.id.logout_customer:
                logoutDialog();
                break;
            case R.id.addpart:
                bottomSheetDialog.show();
                break;
            case R.id.report:
                bottomSheetDialog1.show();
                break;
        }
    }

    //Logout Card Click Method Call
    private void logoutDialog() {
        new AlertDialog.Builder(CitiesActivity.this)
                .setIcon(R.drawable.ic_warning)
                .setTitle("Log Out!")
                .setMessage("\nAre you sure to log out?\n")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        mAuth.signOut();
                        Intent intent = new Intent(CitiesActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }

    void restrict_act() {
        startActivity(new Intent(CitiesActivity.this, LoginActivity.class));
        finish();

    }

}

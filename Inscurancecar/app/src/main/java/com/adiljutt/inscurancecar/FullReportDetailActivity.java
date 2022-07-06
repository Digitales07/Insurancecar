package com.adiljutt.inscurancecar;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.adiljutt.inscurancecar.model.FullVioceReport;
import com.adiljutt.inscurancecar.model.VoiceReports;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullReportDetailActivity extends AppCompatActivity {

    @BindView(R.id.report)
    TextView report;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.insurer)
    TextView insurer;
    @BindView(R.id.insured)
    TextView insured;
    @BindView(R.id.policy)
    TextView policy;
    @BindView(R.id.expiry)
    TextView expiry;
    @BindView(R.id.certified)
    TextView certified;
    @BindView(R.id.sum)
    TextView sum;
    @BindView(R.id.loss)
    TextView loss;
    @BindView(R.id.excess)
    TextView excess;
    @BindView(R.id.classs)
    TextView classs;
    @BindView(R.id.marker)
    TextView marker;
    @BindView(R.id.body)
    TextView body;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.chassis)
    TextView chassis;
    @BindView(R.id.model)
    TextView model;
    @BindView(R.id.engine)
    TextView engine;
    @BindView(R.id.power)
    TextView power;
    @BindView(R.id.color)
    TextView color;
    @BindView(R.id.odometer)
    TextView odometer;
    @BindView(R.id.driver_name)
    TextView driver_name;
    @BindView(R.id.driver_age)
    TextView driver_age;
    @BindView(R.id.driver_license)
    TextView driver_license;
    @BindView(R.id.issue_place)
    TextView issue_place;
    @BindView(R.id.date_issue)
    TextView date_issue;
    @BindView(R.id.expiry_date)
    TextView expiry_date;
    @BindView(R.id.accident_date)
    TextView accident_date;
    @BindView(R.id.request_date)
    TextView request_date;
    @BindView(R.id.done_date)
    TextView done_date;
    @BindView(R.id.places)
    TextView places;
    @BindView(R.id.survey_held)
    TextView survey_held;
    @BindView(R.id.circumfence)
    TextView circumfence;

    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @BindView(R.id.doc)
    Button doc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_report_detail);
        ButterKnife.bind(this);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Added_FullReport").child("01");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                FullVioceReport list = dataSnapshot.getValue(FullVioceReport.class);

                if (list!=null) {
                    report.setText(list.getReports());
                    date.setText(list.getSelect_dates());
                    insurer.setText(list.getInsurers());
                    insured.setText(list.getInsureds());
                    policy.setText(list.getPolicys());
                    expiry.setText(list.getExpiry_dates());
                    certified.setText(list.getCertifieds());
                    sum.setText(list.getSum_insureds());
                    loss.setText(list.getLosss());
                    excess.setText(list.getAccesss());
                    classs.setText(list.getClassss());
                    marker.setText(list.getMarkers());
                    body.setText(list.getBodys());
                    register.setText(list.getRegisters());
                    chassis.setText(list.getChassics());
                    model.setText(list.getModels());
                    engine.setText(list.getEngines());
                    power.setText(list.getPowers());
                    color.setText(list.getColors());
                    odometer.setText(list.getOdometers());
                    driver_name.setText(list.getName_drivers());
                    driver_age.setText(list.getDriver_ages());
                    driver_license.setText(list.getDriver_licence_nos());
                    issue_place.setText(list.getDriver_locations());
                    date_issue.setText(list.getDriver_issue_dates());
                    expiry_date.setText(list.getDriver_expiry_dates());

                    accident_date.setText(list.getAccident_dates());
                    request_date.setText(list.getRequest_dates());
                    done_date.setText(list.getDone_dates());
                    places.setText(list.getAccident_places());
                    survey_held.setText(list.getSurvey_helds());
                    circumfence.setText(list.getCircumfences());



                }else {
                    report.setText("");
                    date.setText("");
                    insurer.setText("");
                    insured.setText("");
                    policy.setText("");
                    expiry.setText("");
                    certified.setText("");
                    sum.setText("");
                    loss.setText("");
                    excess.setText("");
                    classs.setText("");
                    marker.setText("");
                    body.setText("");
                    register.setText("");
                    chassis.setText("");
                    model.setText("");
                    engine.setText("");
                    power.setText("");
                    color.setText("");
                    odometer.setText("");
                    driver_name.setText("");
                    driver_age.setText("");
                    driver_license.setText("");
                    issue_place.setText("");
                    date_issue.setText("");
                    expiry_date.setText("");
                    accident_date.setText("");
                    request_date.setText("");
                    done_date.setText("");
                    places.setText("");
                    survey_held.setText("");
                    circumfence.setText("");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FullReportDetailActivity.this, "Downloaded", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

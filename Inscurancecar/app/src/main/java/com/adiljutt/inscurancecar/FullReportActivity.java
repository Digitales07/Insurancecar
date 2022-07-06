package com.adiljutt.inscurancecar;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullReportActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.select_date)
    TextView select_date;
    @BindView(R.id.report)
    EditText report;
    @BindView(R.id.ntn)
    EditText ntn;
    @BindView(R.id.insurer)
    EditText insurer;
    @BindView(R.id.insured)
    EditText insured;
    @BindView(R.id.policy)
    EditText policy;
    @BindView(R.id.loss)
    EditText loss;
    @BindView(R.id.sum_insured)
    EditText sum_insured;
    @BindView(R.id.certified)
    EditText certified;
    @BindView(R.id.access)
    EditText access;

    @BindView(R.id.expiry_date)
    TextView expiry_date;

    @BindView(R.id.classs)
    EditText classs;
    @BindView(R.id.marker)
    EditText marker;
    @BindView(R.id.body)
    EditText body;
    @BindView(R.id.register)
    EditText register;
    @BindView(R.id.chassic)
    EditText chassic;
    @BindView(R.id.model)
    EditText model;
    @BindView(R.id.engine)
    EditText engine;
    @BindView(R.id.power)
    EditText power;
    @BindView(R.id.color)
    EditText color;
    @BindView(R.id.odometer)
    EditText odometer;
    @BindView(R.id.name_driver)
    EditText name_driver;
    @BindView(R.id.driver_age)
    EditText driver_age;
    @BindView(R.id.driver_licence_no)
    EditText driver_licence_no;
    @BindView(R.id.driver_location)
    EditText driver_location;

    @BindView(R.id.driver_issue_date)
    TextView driver_issue_date;
    @BindView(R.id.driver_expiry_date)
    TextView driver_expiry_date;
    @BindView(R.id.accident_date)
    TextView accident_date;
    @BindView(R.id.request_date)
    TextView request_date;
    @BindView(R.id.done_date)
    TextView done_date;

    @BindView(R.id.accident_place)
    EditText accident_place;
    @BindView(R.id.survey_held)
    EditText survey_held;
    @BindView(R.id.circumfence)
    EditText circumfence;

    final Calendar myCalendar = Calendar.getInstance();

    String currentDate;

    FirebaseDatabase database;
    DatabaseReference databaseReference,databaseReference1;
    @BindView(R.id.add_case)
    Button add_case;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_report);
        ButterKnife.bind(this);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Added_FullReport").child("01");
        databaseReference1 = database.getReference("VoiceReport");

        select_date.setOnClickListener(this);
        expiry_date.setOnClickListener(this);
        driver_issue_date.setOnClickListener(this);
        driver_expiry_date.setOnClickListener(this);
        accident_date.setOnClickListener(this);
        request_date.setOnClickListener(this);
        done_date.setOnClickListener(this);
        add_case.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.select_date:
                Calendar calforDate = Calendar.getInstance();
                SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                currentDate = currentDateFormat.format(calforDate.getTime());
                select_date.setText(currentDate);
                break;

            case R.id.expiry_date:
                showDateDialog();
                break;
            case R.id.driver_issue_date:
                showDateDialog1();
                break;
            case R.id.driver_expiry_date:
                showDateDialog2();
                break;
            case R.id.accident_date:
                showDateDialog3();
                break;

            case R.id.request_date:
                showDateDialog4();
                break;
            case R.id.done_date:
                showDateDialog5();
                break;
            case R.id.add_case:
                SaveDataToFirebase();
                break;
        }
    }

    private void SaveDataToFirebase() {

        hideKeyboard();

        String messageKey = databaseReference.push().getKey();

        String ntns = ntn.getText().toString();
        String reports = report.getText().toString();
        String insurers = insurer.getText().toString();
        String insureds = insured.getText().toString();
        String losss = loss.getText().toString();
        String policys = policy.getText().toString();
        String sum_insureds = sum_insured.getText().toString();
        String certifieds = certified.getText().toString();
        String accesss = access.getText().toString();
        String classss = classs.getText().toString();
        String markers = marker.getText().toString();
        String bodys = body.getText().toString();
        String registers = register.getText().toString();
        String chassics = chassic.getText().toString();
        String models = model.getText().toString();
        String engines = engine.getText().toString();
        String powers = power.getText().toString();
        String colors = color.getText().toString();
        String odometers = odometer.getText().toString();
        String name_drivers = name_driver.getText().toString();
        String driver_ages = driver_age.getText().toString();
        String driver_licence_nos = driver_licence_no.getText().toString();
        String driver_locations = driver_location.getText().toString();
        String accident_places = accident_place.getText().toString();
        String survey_helds = survey_held.getText().toString();
        String circumfences = circumfence.getText().toString();

        String select_dates = select_date.getText().toString();
        String expiry_dates = expiry_date.getText().toString();
        String driver_issue_dates = driver_issue_date.getText().toString();
        String driver_expiry_dates = driver_expiry_date.getText().toString();
        String accident_dates = accident_date.getText().toString();
        String request_dates = request_date.getText().toString();
        String done_dates = done_date.getText().toString();


        if (TextUtils.isEmpty(ntns)) {
            ntn.setError("Enter NTN Number");
            ntn.requestFocus();
        }else if (TextUtils.isEmpty(reports)) {
            report.setError("Enter Address");
            report.requestFocus();
        }else if (TextUtils.isEmpty(insurers)) {
            insurer.setError("Enter Particulars");
            insurer.requestFocus();
        }else if (TextUtils.isEmpty(insureds)) {
            insured.setError("Enter policys");
            insured.requestFocus();
        } else if (TextUtils.isEmpty(losss)) {
            loss.setError("Enter losss");
            loss.requestFocus();
        }else {

            HashMap<String, Object> messageInfoMap = new HashMap<>();
            messageInfoMap.put("report_id", messageKey);
            messageInfoMap.put("ntns", ntns);
            messageInfoMap.put("reports", reports);
            messageInfoMap.put("insurers", insurers);
            messageInfoMap.put("policys", policys);
            messageInfoMap.put("losss", losss);
            messageInfoMap.put("insureds", insureds);
            messageInfoMap.put("sum_insureds", sum_insureds);
            messageInfoMap.put("certifieds", certifieds);
            messageInfoMap.put("accesss", accesss);
            messageInfoMap.put("classss", classss);
            messageInfoMap.put("markers", markers);
            messageInfoMap.put("bodys", bodys);
            messageInfoMap.put("registers", registers);
            messageInfoMap.put("chassics", chassics);
            messageInfoMap.put("models", models);
            messageInfoMap.put("engines", engines);
            messageInfoMap.put("powers", powers);
            messageInfoMap.put("colors", colors);
            messageInfoMap.put("odometers", odometers);
            messageInfoMap.put("name_drivers", name_drivers);
            messageInfoMap.put("driver_ages", driver_ages);
            messageInfoMap.put("driver_licence_nos", driver_licence_nos);
            messageInfoMap.put("driver_locations", driver_locations);
            messageInfoMap.put("accident_places", accident_places);
            messageInfoMap.put("survey_helds", survey_helds);
            messageInfoMap.put("circumfences", circumfences);
            messageInfoMap.put("select_dates", select_dates);
            messageInfoMap.put("expiry_dates", expiry_dates);
            messageInfoMap.put("driver_issue_dates", driver_issue_dates);
            messageInfoMap.put("driver_expiry_dates", driver_expiry_dates);
            messageInfoMap.put("accident_dates", accident_dates);
            messageInfoMap.put("request_dates", request_dates);
            messageInfoMap.put("done_dates", done_dates);


            assert messageKey != null;
            databaseReference.updateChildren(messageInfoMap);
            databaseReference1.child(messageKey).updateChildren(messageInfoMap);

            Toast.makeText(getApplicationContext(), "Check and Download", Toast.LENGTH_SHORT).show();
            Intent intent  = new Intent(FullReportActivity.this,FullReportDetailActivity.class);
            startActivity(intent);
            finish();

        }}

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) Objects.requireNonNull(getSystemService(Context.INPUT_METHOD_SERVICE))).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void showDateDialog() {

        new DatePickerDialog(this, (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            expiry_date.setText(sdf.format(myCalendar.getTime()));
        }, 2019, 01, 01).show();


    }
    private void showDateDialog1() {

        new DatePickerDialog(this, (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            driver_issue_date.setText(sdf.format(myCalendar.getTime()));
        }, 2019, 01, 01).show();


    }
    private void showDateDialog2() {

        new DatePickerDialog(this, (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            driver_expiry_date.setText(sdf.format(myCalendar.getTime()));
        }, 2019, 01, 01).show();


    }
    private void showDateDialog3() {
        new DatePickerDialog(this, (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            accident_date.setText(sdf.format(myCalendar.getTime()));
        }, 2019, 01, 01).show();
    }
    private void showDateDialog4() {
        new DatePickerDialog(this, (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            request_date.setText(sdf.format(myCalendar.getTime()));
        }, 2019, 01, 01).show();
    }
    private void showDateDialog5() {
        new DatePickerDialog(this, (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            done_date.setText(sdf.format(myCalendar.getTime()));
        }, 2019, 01, 01).show();
    }

}

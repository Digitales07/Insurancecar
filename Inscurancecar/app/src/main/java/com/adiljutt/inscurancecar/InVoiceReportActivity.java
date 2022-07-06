package com.adiljutt.inscurancecar;

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

import java.util.HashMap;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InVoiceReportActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.ntn)
    EditText ntn;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.particulars)
    EditText particulars;
    @BindView(R.id.policy)
    EditText policy;
    @BindView(R.id.loss)
    EditText loss;
    @BindView(R.id.pofessional_description)
    EditText pofessional_description;
    @BindView(R.id.pofessional_fee)
    EditText pofessional_fee;
    @BindView(R.id.reinspection_description)
    EditText reinspection_description;
    @BindView(R.id.reinspection_fee)
    EditText reinspection_fee;
    @BindView(R.id.conveyance_description)
    EditText conveyance_description;
    @BindView(R.id.local_conveyance_fee)
    EditText local_conveyance_fee;
    @BindView(R.id.photographs)
    EditText photographs;
    @BindView(R.id.photographs_fee)
    EditText photographs_fee;
    @BindView(R.id.grandtotal)
    EditText grandtotal;
    @BindView(R.id.add_case)
    Button add_case;
    FirebaseDatabase database;
    DatabaseReference databaseReference,databaseReference1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_voice_report);
        ButterKnife.bind(this);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Added_InVoiceReport").child("01");
        databaseReference1 = database.getReference("InVoiceReport");

        add_case.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_case:
                SaveDataToFirebase();
                break;
        }
    }

    private void SaveDataToFirebase() {

        hideKeyboard();

        String messageKey = databaseReference.push().getKey();

        String ntns = ntn.getText().toString();
        String addresss = address.getText().toString();
        String particularss = particulars.getText().toString();
        String policys = policy.getText().toString();
        String losss = loss.getText().toString();
        String pofessional_descriptions = pofessional_description.getText().toString();
        String pofessional_fees = pofessional_fee.getText().toString();
        String reinspection_descriptions = reinspection_description.getText().toString();
        String reinspection_fees = reinspection_fee.getText().toString();
        String conveyance_descriptions = conveyance_description.getText().toString();
        String local_conveyance_fees = local_conveyance_fee.getText().toString();
        String photographss = photographs.getText().toString();
        String photographs_fees = photographs_fee.getText().toString();
        String grandtotals = grandtotal.getText().toString();

        if (TextUtils.isEmpty(ntns)) {
            ntn.setError("Enter NTN Number");
            ntn.requestFocus();
        }else if (TextUtils.isEmpty(addresss)) {
            address.setError("Enter Address");
            address.requestFocus();
        }else if (TextUtils.isEmpty(particularss)) {
            particulars.setError("Enter Particulars");
            particulars.requestFocus();
        }else if (TextUtils.isEmpty(policys)) {
            policy.setError("Enter policys");
            particulars.requestFocus();
        } else if (TextUtils.isEmpty(losss)) {
            loss.setError("Enter losss");
            loss.requestFocus();
        }else {

            HashMap<String, Object> messageInfoMap = new HashMap<>();
            messageInfoMap.put("report_id", messageKey);
            messageInfoMap.put("ntns", ntns);
            messageInfoMap.put("addresss", addresss);
            messageInfoMap.put("particularss", particularss);
            messageInfoMap.put("policys", policys);
            messageInfoMap.put("losss", losss);
            messageInfoMap.put("pofessional_descriptions", pofessional_descriptions);
            messageInfoMap.put("pofessional_fees", pofessional_fees);
            messageInfoMap.put("reinspection_descriptions", reinspection_descriptions);
            messageInfoMap.put("reinspection_fees", reinspection_fees);
            messageInfoMap.put("conveyance_descriptions", conveyance_descriptions);
            messageInfoMap.put("local_conveyance_fees", local_conveyance_fees);
            messageInfoMap.put("photographss", photographss);
            messageInfoMap.put("photographs_fees", photographs_fees);
            messageInfoMap.put("grandtotals", grandtotals);

            assert messageKey != null;
            databaseReference.updateChildren(messageInfoMap);
            databaseReference1.child(messageKey).updateChildren(messageInfoMap);

            Toast.makeText(getApplicationContext(), "Check and Download", Toast.LENGTH_SHORT).show();
            Intent intent  = new Intent(InVoiceReportActivity.this,InVoiceReoprtDetailsActivity.class);
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
}

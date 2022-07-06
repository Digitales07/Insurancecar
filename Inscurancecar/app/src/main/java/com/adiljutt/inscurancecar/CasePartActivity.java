package com.adiljutt.inscurancecar;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.adiljutt.inscurancecar.model.Cases;
import com.adiljutt.inscurancecar.model.Parts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CasePartActivity extends AppCompatActivity {


    @BindView(R.id.condition)
    EditText condition;
    @BindView(R.id.dep)
    EditText dep;
    @BindView(R.id.price)
    EditText price;
    @BindView(R.id.part_name)
    TextView part_name;

    @BindView(R.id.total)
    TextView total;

    @BindView(R.id.add)
    Button add;

    String current_user,currentTime;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference,partsReference;

    Parts current_promotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_part);
        ButterKnife.bind(this);

//        id = getIntent().getStringExtra("id");



        mAuth = FirebaseAuth.getInstance();
        current_user = mAuth.getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("NewCarParts");
        partsReference = database.getReference("Car_Parts");

        if (getIntent()!=null)
            currentTime = getIntent().getStringExtra("case_detail");
        if (!currentTime.isEmpty()){
            getDetailProduct(currentTime);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveDatatoDatabase();
            }
        });


        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()){

                    String deps = dep.getText().toString();
                    String prixce= price.getText().toString();
                    double number = Integer.parseInt(deps) / 100;
                    double number1 = Double.parseDouble(String.valueOf(number)) * Integer.parseInt(prixce);
                    double number2 =  Integer.parseInt(prixce) - Double.parseDouble(String.valueOf(number1));

                    DecimalFormat decimalFormat = new DecimalFormat("#.00");
                    total.setText(String.valueOf(Double.parseDouble(decimalFormat.format(number2))));


                }else {
                    total.setText("0.0");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void SaveDatatoDatabase() {

        String messageKey = databaseReference.push().getKey();
        String conditions = condition.getText().toString();
        String deps = dep.getText().toString();
        String prices = price.getText().toString();
        String totals = total.getText().toString();
        String part_names = part_name.getText().toString();

        HashMap<String, Object> messageInfoMap = new HashMap<>();
            messageInfoMap.put("messageKey_id", messageKey);
            messageInfoMap.put("conditions", conditions);
            messageInfoMap.put("deps", deps);
            messageInfoMap.put("totals", totals);
            messageInfoMap.put("prices", prices);
//            messageInfoMap.put("caseid", id);
            messageInfoMap.put("part_name", part_names);
            messageInfoMap.put("currentuser", current_user);
//
        assert messageKey != null;
        databaseReference.child(messageKey).updateChildren(messageInfoMap);
        finish();
//
    }

    private void getDetailProduct(String currentTime) {
        partsReference.child(currentTime).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                current_promotion = dataSnapshot.getValue(Parts.class);

                assert current_promotion != null;
                part_name.setText(current_promotion.getPart_name());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

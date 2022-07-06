package com.adiljutt.inscurancecar;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adiljutt.inscurancecar.adapter.CitiesAdapter;
import com.adiljutt.inscurancecar.model.Cases;
import com.adiljutt.inscurancecar.model.PatsAddedData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CaseDetailActivity extends AppCompatActivity implements View.OnClickListener{

    String discountId = "";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference discount;

    Cases current_promotion;

    @BindView(R.id.auto_cuurent_date)
    TextView auto_cuurent_date;
    @BindView(R.id.insurance_id)
    TextView insurance_id;
    @BindView(R.id.owner_name)
    TextView owner_name;
    @BindView(R.id.father_name)
    TextView father_name;
    @BindView(R.id.cnic)
    TextView cnic;
    @BindView(R.id.workshop_name)
    TextView workshop_name;
    @BindView(R.id.car_number)
    TextView car_number;
    @BindView(R.id.asking_amount)
    TextView asking_amount;
    @BindView(R.id.settle_amount)
    TextView settle_amount;
    @BindView(R.id.tax)
    TextView tax;
    @BindView(R.id.engine_number)
    TextView engine_number;
    @BindView(R.id.chasis_numbers)
    TextView chasis_numbers;


    @BindView(R.id.insurance_history_amount)
    TextView insurance_history_amount;

    @BindView(R.id.custom_date)
    TextView custom_date;
    @BindView(R.id.sum_insureds)
    TextView sum_insureds;
    @BindView(R.id.car_companys)
    TextView car_companys;
    @BindView(R.id.premiums)
    TextView premiums;

    @BindView(R.id.branchs)
    TextView branchs;

    @BindView(R.id.models)
    TextView models;
    @BindView(R.id.detuctables)
    TextView detuctables;
    @BindView(R.id.reinspections)
    TextView reinspections;
    @BindView(R.id.remarks)
    TextView remarks;
    @BindView(R.id.previous_amount_details)
    TextView previous_amount_details;
    @BindView(R.id.previous_losses__lodgement_date)
    TextView previous_losses__lodgement_date;

    @BindView(R.id.custom_lose_dates)
    TextView custom_lose_dates;

    @BindView(R.id.after)
    TextView after;
    @BindView(R.id.before)
    TextView before;


    @BindView(R.id.edit_case)
    Button edit_case;

    @BindView(R.id.delete_case)
    Button delete_case;

    @BindView(R.id.add_case)
    Button add_case;


    BottomSheetDialog bottomSheetDialog;
    LinearLayout voice_report;
    LinearLayout cus_report;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case_detail);
        ButterKnife.bind(this);

        //Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        discount = firebaseDatabase.getReference("Case");

        if (getIntent()!=null)
            discountId = getIntent().getStringExtra("case_detail");
        if (!discountId.isEmpty()){
            getDetailProduct(discountId);
        }

        before.setOnClickListener(this);
        after.setOnClickListener(this);
        delete_case.setOnClickListener(this);
        edit_case.setOnClickListener(this);
        add_case.setOnClickListener(this);

        bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.item_dialog,null);
        voice_report = (LinearLayout) view.findViewById(R.id.voice_report);
        cus_report = (LinearLayout) view.findViewById(R.id.cus_report);
        bottomSheetDialog.setContentView(view);

        voice_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(CaseDetailActivity.this,InVoiceReportActivity.class);
                startActivity(intent);
            }
        });

        cus_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(CaseDetailActivity.this,FullReportActivity.class);
                startActivity(intent);
            }
        });


    }

    private void getDetailProduct(String discountId) {

        discount.child(discountId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                current_promotion = dataSnapshot.getValue(Cases.class);

                assert current_promotion != null;
                auto_cuurent_date.setText(current_promotion.getAuto_cuurent_dates());
                insurance_id.setText(current_promotion.getInsurance_ids());
                owner_name.setText(current_promotion.getOwner_names());
                father_name.setText(current_promotion.getFather_names());
                cnic.setText(current_promotion.getCnics());
                workshop_name.setText(current_promotion.getWorkshop_names());
                car_number.setText(current_promotion.getCar_numbers());
                asking_amount.setText(current_promotion.getAsking_amounts());
                settle_amount.setText(current_promotion.getSettle_amounts());
                tax.setText(current_promotion.getTaxs());
                insurance_history_amount.setText(current_promotion.getInsurance_history_amounts());
                custom_date.setText(current_promotion.getCustom_dates());
                engine_number.setText(current_promotion.getEngine_numbers());
                chasis_numbers.setText(current_promotion.getChasis_numbers());
                sum_insureds.setText(current_promotion.getSum_insureds());
                car_companys.setText(current_promotion.getCar_companys());
                premiums.setText(current_promotion.getPremiums());
                models.setText(current_promotion.getModels());
                branchs.setText(current_promotion.getBranchs());
                detuctables.setText(current_promotion.getDetuctables());
                reinspections.setText(current_promotion.getReinspections());
                remarks.setText(current_promotion.getRemarks());
                previous_amount_details.setText(current_promotion.getPrevious_amount_details());
                previous_losses__lodgement_date.setText(current_promotion.getPrevious_losses__lodgement_date());
                custom_lose_dates.setText(current_promotion.getCustom_lose_dates());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.after:
                Intent intent = new Intent(CaseDetailActivity.this,AfterImagesActivity.class);
                startActivity(intent);
                break;
            case R.id.before:
                Intent intent1 = new Intent(CaseDetailActivity.this,BeforeImagesActivity.class);
                startActivity(intent1);
                break;
            case R.id.add_case:

                bottomSheetDialog.show();

                break;
            case R.id.edit_case:
                Intent edit_case = new Intent(CaseDetailActivity.this,EditCaseActivity.class);
                edit_case.putExtra("case_detail",discountId);
                startActivity(edit_case);
                break;
            case R.id.delete_case:
                discount.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){
                            for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                                Cases medication = snapshot.getValue(Cases.class);
                                assert medication != null;
                                if (medication.getInsurance_ids().equals(insurance_id.getText().toString())){
                                    discount.removeValue();
                                }
                            }

                            Toast.makeText(CaseDetailActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                            Intent intent  = new Intent(CaseDetailActivity.this,CitiesActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
                break;
        }
    }
}

package com.adiljutt.inscurancecar;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adiljutt.inscurancecar.model.Cases;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditCaseActivity extends AppCompatActivity implements View.OnClickListener{
    String discountId = "";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference discount;

    Cases current_promotion;

    final Calendar myCalendar = Calendar.getInstance();


    @BindView(R.id.insurance_id)
    EditText insurance_id;
    @BindView(R.id.auto_cuurent_date)
    TextView auto_cuurent_date;
    @BindView(R.id.owner_name)
    EditText owner_name;
    @BindView(R.id.father_name)
    EditText father_name;
    @BindView(R.id.cnic)
    EditText cnic;
    @BindView(R.id.workshop_name)
    EditText workshop_name;
    @BindView(R.id.car_number)
    EditText car_number;
    String currentDate,currentTime;


    @BindView(R.id.asking_amount)
    EditText asking_amount;
    @BindView(R.id.settle_amount)
    EditText settle_amount;
    @BindView(R.id.tax)
    EditText tax;
    @BindView(R.id.insurance_history_amount)
    EditText insurance_history_amount;
    @BindView(R.id.custom_date)
    TextView custom_date;

    @BindView(R.id.add_case)
    Button add_case;

    @BindView(R.id.add_part)
    Button add_part;

    @BindView(R.id.engine_number)
    EditText engine_number;
    @BindView(R.id.chasis_number)
    EditText chasis_number;
    @BindView(R.id.sum_insured)
    EditText sum_insured;
    @BindView(R.id.car_company)
    EditText car_company;
    @BindView(R.id.premium)
    EditText premium;
    @BindView(R.id.model)
    EditText model;
    @BindView(R.id.branch)
    EditText branch;
    @BindView(R.id.detuctable)
    EditText detuctable;
    @BindView(R.id.reinspection)
    EditText reinspection;
    @BindView(R.id.remark)
    EditText remark;
    @BindView(R.id.previous_amount_detail)
    EditText previous_amount_detail;
    @BindView(R.id.loses_dates)
    EditText loses_dates;

    @BindView(R.id.custom_lose_date)
    TextView custom_lose_date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_case);
        ButterKnife.bind(this);

        //Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        discount = firebaseDatabase.getReference("Case");

        if (getIntent()!=null)
            discountId = getIntent().getStringExtra("case_detail");
        if (!discountId.isEmpty()){
            getDetailProduct(discountId);
        }

        add_case.setOnClickListener(this);
        auto_cuurent_date.setOnClickListener(this);
        add_part.setOnClickListener(this);


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
                chasis_number.setText(current_promotion.getChasis_numbers());
                sum_insured.setText(current_promotion.getSum_insureds());
                car_company.setText(current_promotion.getCar_companys());
                premium.setText(current_promotion.getPremiums());
                model.setText(current_promotion.getModels());
                branch.setText(current_promotion.getBranchs());
                detuctable.setText(current_promotion.getDetuctables());
                reinspection.setText(current_promotion.getReinspections());
                remark.setText(current_promotion.getRemarks());
                previous_amount_detail.setText(current_promotion.getPrevious_amount_details());
                loses_dates.setText(current_promotion.getPrevious_losses__lodgement_date());
                custom_lose_date.setText(current_promotion.getCustom_lose_dates());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add_part:
                SaveDatatoDatabase();
            break;
            case R.id.add_case:
                finish();
                break;
            case R.id.auto_cuurent_date:
                Calendar calforDate = Calendar.getInstance();
                SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                currentDate = currentDateFormat.format(calforDate.getTime());
                auto_cuurent_date.setText(currentDate);
                break;
        }
    }

    private void SaveDatatoDatabase() {

        String insurance_ids = insurance_id.getText().toString();
        String auto_cuurent_dates = auto_cuurent_date.getText().toString();
        String owner_names = owner_name.getText().toString();
        String father_names = father_name.getText().toString();
        String cnics = cnic.getText().toString();
        String workshop_names = workshop_name.getText().toString();
        String car_numbers = car_number.getText().toString();
        String asking_amounts = asking_amount.getText().toString();
        String settle_amounts = settle_amount.getText().toString();
        String taxs = tax.getText().toString();
        String insurance_history_amounts = insurance_history_amount.getText().toString();
        String custom_dates = custom_date.getText().toString();
        String engine_numbers = engine_number.getText().toString();
        String chasis_numbers = chasis_number.getText().toString();
        String sum_insureds = sum_insured.getText().toString();
        String  car_companys = car_company.getText().toString();
        String  premiums = premium.getText().toString();
        String  models = model.getText().toString();
        String  branchs = branch.getText().toString();
        String  detuctables = detuctable.getText().toString();
        String  reinspections = reinspection.getText().toString();
        String  remarks = remark.getText().toString();
        String  previous_amount_details = previous_amount_detail.getText().toString();
        String  losses_date = loses_dates.getText().toString();
        String custom_lose_dates = custom_lose_date.getText().toString();

        if (TextUtils.isEmpty(insurance_ids)) {
            insurance_id.setError("Enter Insurance Id");
            insurance_id.requestFocus();
        }else if (TextUtils.isEmpty(auto_cuurent_dates)) {
            auto_cuurent_date.setError("Select Current Date");
            auto_cuurent_date.requestFocus();
        }else if (TextUtils.isEmpty(owner_names)) {
            owner_name.setError("Enter Owner Name");
            owner_name.requestFocus();
        }else if (TextUtils.isEmpty(father_names)) {
            father_name.setError("Enter Father Name");
            father_name.requestFocus();
        }else if (TextUtils.isEmpty(cnics)) {
            cnic.setError("Enter CNIC Number");
            cnic.requestFocus();
        }else if (TextUtils.isEmpty(workshop_names)) {
            workshop_name.setError("Enter WorkShop Name");
            workshop_name.requestFocus();
        }else if (TextUtils.isEmpty(car_numbers)) {
            car_number.setError("Enter Car Number");
            car_number.requestFocus();
        }else if (TextUtils.isEmpty(asking_amounts)) {
            asking_amount.setError("Enter Asking Amount");
            asking_amount.requestFocus();
        }else if (TextUtils.isEmpty(settle_amounts)) {
            settle_amount.setError("Enter Settle Amount");
            settle_amount.requestFocus();
        }else if (TextUtils.isEmpty(taxs)) {
            tax.setError("Enter Tax");
            tax.requestFocus();
        }else if (TextUtils.isEmpty(insurance_history_amounts)) {
            insurance_history_amount.setError("Enter Insurance Amount");
            insurance_history_amount.requestFocus();
        }else if (TextUtils.isEmpty(custom_dates)) {
            custom_date.setError("Select Date");
            custom_date.requestFocus();
        }else if (TextUtils.isEmpty(engine_numbers)) {
            engine_number.setError("Enter Engine Number");
            engine_number.requestFocus();
        }else if (TextUtils.isEmpty(chasis_numbers)) {
            chasis_number.setError("Enter Chassis Number");
            chasis_number.requestFocus();
        }else if (TextUtils.isEmpty(sum_insureds)) {
            sum_insured.setError("Enter Sum Insured");
            sum_insured.requestFocus();
        }else if (TextUtils.isEmpty(car_companys)) {
            car_company.setError("Enter Car Company");
            car_company.requestFocus();
        }else if (TextUtils.isEmpty(premiums)) {
            premium.setError("Enter Premium");
            premium.requestFocus();
        }else if (TextUtils.isEmpty(models)) {
            model.setError("Enter Model");
            model.requestFocus();
        }else if (TextUtils.isEmpty(branchs)) {
            branch.setError("Enter Branch");
            branch.requestFocus();
        }else if (TextUtils.isEmpty(detuctables)) {
            detuctable.setError("Enter Detuctables");
            detuctable.requestFocus();
        }else if (TextUtils.isEmpty(reinspections)) {
            reinspection.setError("Enter reinspections");
            reinspection.requestFocus();
        }else if (TextUtils.isEmpty(remarks)) {
            remark.setError("Enter remarks");
            remark.requestFocus();
        }else if (TextUtils.isEmpty(previous_amount_details)) {
            previous_amount_detail.setError("Enter previous amount details");
            previous_amount_detail.requestFocus();
        }else if (TextUtils.isEmpty(custom_lose_dates)) {
            custom_lose_date.setError("Select Lose Date");
            custom_lose_date.requestFocus();
        }else {
            HashMap<String, Object> messageInfoMap = new HashMap<>();
            messageInfoMap.put("insurance_ids", insurance_ids);
            messageInfoMap.put("auto_cuurent_dates", auto_cuurent_dates);
            messageInfoMap.put("owner_names", owner_names);
            messageInfoMap.put("father_names", father_names);
            messageInfoMap.put("cnics", cnics);
            messageInfoMap.put("workshop_names", workshop_names);
            messageInfoMap.put("car_numbers", car_numbers);
            messageInfoMap.put("asking_amounts", asking_amounts);
            messageInfoMap.put("settle_amounts", settle_amounts);
            messageInfoMap.put("taxs", taxs);
            messageInfoMap.put("insurance_history_amounts", insurance_history_amounts);
            messageInfoMap.put("custom_dates", custom_dates);

            messageInfoMap.put("engine_numbers", engine_numbers);
            messageInfoMap.put("chasis_numbers", chasis_numbers);
            messageInfoMap.put("sum_insureds", sum_insureds);
            messageInfoMap.put("car_companys", car_companys);
            messageInfoMap.put("premiums", premiums);
            messageInfoMap.put("models", models);
            messageInfoMap.put("branchs", branchs);
            messageInfoMap.put("detuctables", detuctables);
            messageInfoMap.put("reinspections", reinspections);
            messageInfoMap.put("remarks", remarks);
            messageInfoMap.put("previous_amount_details", previous_amount_details);
            messageInfoMap.put("previous_losses__lodgement_date", losses_date);
            messageInfoMap.put("custom_lose_dates", custom_lose_dates);

            messageInfoMap.put("city_name", currentTime);
            discount.child(discountId).updateChildren(messageInfoMap);

            Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();

            Intent intent =  new Intent(EditCaseActivity.this,CitiesActivity.class);
            startActivity(intent);
            finish();

        }

    }


}

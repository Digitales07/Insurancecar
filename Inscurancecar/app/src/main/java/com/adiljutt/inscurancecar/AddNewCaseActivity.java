package com.adiljutt.inscurancecar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.adiljutt.inscurancecar.model.Cities;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddNewCaseActivity extends AppCompatActivity implements View.OnClickListener{

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

    @BindView(R.id.before_img_one)
    ImageView before_img_one;
    @BindView(R.id.before_img_two)
    ImageView before_img_two;
    @BindView(R.id.before_img_three)
    ImageView before_img_three;
    @BindView(R.id.before_img_four)
    ImageView before_img_four;

    @BindView(R.id.after_img_one)
    ImageView after_img_one;
    @BindView(R.id.after_img_two)
    ImageView after_img_two;
    @BindView(R.id.after_img_three)
    ImageView after_img_three;
    @BindView(R.id.after_img_four)
    ImageView after_img_four;

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

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseStorage storage;
    StorageReference storageReference;
    Uri imageUri;
    String currentDate,currentTime;
    private int PICK_IMAGE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_case);
        ButterKnife.bind(this);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Case");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference().child("Image");

        auto_cuurent_date.setOnClickListener(this);
        add_case.setOnClickListener(this);
        custom_date.setOnClickListener(this);
        before_img_one.setOnClickListener(this);
        custom_lose_date.setOnClickListener(this);
        add_part.setOnClickListener(this);

        currentTime = getIntent().getStringExtra("site");

        Toast.makeText(this, ""+currentTime, Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.auto_cuurent_date:
                Calendar calforDate = Calendar.getInstance();
                SimpleDateFormat currentDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                currentDate = currentDateFormat.format(calforDate.getTime());
                auto_cuurent_date.setText(currentDate);
            break;
            case R.id.custom_date:
                showDateDialog();
                break;
            case R.id.custom_lose_date:
                showDateDialog1();
                break;
            case R.id.add_part:
                SaveDatatoDatabase();
                break;
            case R.id.before_img_one:
                chooseImage();
                break;
            case R.id.add_case:
//                SaveDatatoDatabase();
                Intent intent = new Intent(AddNewCaseActivity.this,CitiesActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

                before_img_one.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showDateDialog() {

        new DatePickerDialog(this, (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            custom_date.setText(sdf.format(myCalendar.getTime()));
        }, 2019, 01, 01).show();


    }

    private void showDateDialog1() {

        new DatePickerDialog(this, (datePicker, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "dd-MM-yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            custom_lose_date.setText(sdf.format(myCalendar.getTime()));
        }, 2019, 01, 01).show();


    }

    private void SaveDatatoDatabase() {


        String messageKey = databaseReference.push().getKey();
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
            messageInfoMap.put("messageKey_id", messageKey);
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
            assert messageKey != null;
            databaseReference.child(messageKey).updateChildren(messageInfoMap);

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    Intent intent =  new Intent(AddNewCaseActivity.this,LabourActivity.class);
                    intent.putExtra("id",messageKey);
                    startActivity(intent);
                    finish();
                }
            }, 1000);

        }

    }
}

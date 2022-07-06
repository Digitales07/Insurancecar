package com.adiljutt.inscurancecar;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adiljutt.inscurancecar.model.FullVioceReport;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FullReportDetailListActivity extends AppCompatActivity {

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
    String discountId = "";

    @BindView(R.id.pdf)
    Button pdf;
    @BindView(R.id.layout)
    LinearLayout layout;

    private static final int Storage_Code=1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_report_detail_list);
        ButterKnife.bind(this);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("VoiceReport");


        if (getIntent()!=null)
            discountId = getIntent().getStringExtra("case_detail");
        if (!discountId.isEmpty()){
            getDetailProduct(discountId);
        }



        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FullReportDetailListActivity.this, "Downloaded and save in Internal Storage" , Toast.LENGTH_SHORT).show();
            }
        });

        pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layout.setDrawingCacheEnabled(true);
                layout.buildDrawingCache();
                Bitmap bm = layout.getDrawingCache();
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.JPEG, 1000, bytes);

                File f = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
                try {
                    f.createNewFile();
                    FileOutputStream fo = new FileOutputStream(f);
                    fo.write(bytes.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){

                        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission,Storage_Code);
                    }else {
                        try {
                            imageToPDF();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }else {

                }





            }
        });

    }

    public void imageToPDF() throws FileNotFoundException {
        try {
            Document document = new Document();
            String fileName = new SimpleDateFormat("dd-mm-yyyy HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
//            dirpath = android.os.Environment.getExternalStorageDirectory().toString();
            String filePath = Environment.getExternalStorageDirectory() + "/" + fileName + ".pdf";
//            PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/NewPDF.pdf")); //  Change pdf's name.
            PdfWriter.getInstance(document,new FileOutputStream(filePath));
            document.open();
            Image img = Image.getInstance(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100;
            img.scalePercent(scaler);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            document.close();
            Toast.makeText(this, "PDF Generated successfully!..", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

//    private void savepdf() {
//        Document  document= new Document();
//        String fileName = new SimpleDateFormat("dd-mm-yyyy HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
//        String filePath = Environment.getExternalStorageDirectory() + "/" + fileName + ".pdf";
//        try{
//            PdfWriter.getInstance(document,new FileOutputStream(filePath));
//            document.open();
//            String layoutstext = la
//        }catch (Exception e){
//            Toast.makeText(getApplicationContext(), ""+e.getMessage(), Toast.LENGTH_SHORT).show();
//        }
//    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode){
//            case Storage_Code:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                    savepdf();
//                }else {
//                    Toast.makeText(this, "Permission denied...", Toast.LENGTH_SHORT).show();
//                }
//        }
//    }

    private void getDetailProduct(String discountId) {
        databaseReference.child(discountId).addValueEventListener(new ValueEventListener() {
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

    }
}

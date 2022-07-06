package com.adiljutt.inscurancecar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adiljutt.inscurancecar.model.VoiceReports;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InVoiceReoprtDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.report)
    TextView report;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.to)
    TextView to;
    @BindView(R.id.particular)
    TextView particular;
    @BindView(R.id.policy)
    TextView policy;
    @BindView(R.id.loss)
    TextView loss;

    @BindView(R.id.pofessional_description)
    TextView pofessional_description;
    @BindView(R.id.pofessional_amount)
    TextView pofessional_amount;
    @BindView(R.id.reinspection_description)
    TextView reinspection_description;
    @BindView(R.id.reinspection_fee)
    TextView reinspection_fee;
    @BindView(R.id.local_description)
    TextView local_description;
    @BindView(R.id.local_fee)
    TextView local_fee;
    @BindView(R.id.photograph_description)
    TextView photograph_description;
    @BindView(R.id.photographs_fee)
    TextView photographs_fee;
    @BindView(R.id.micelaous_description)
    EditText micelaous_description;
    @BindView(R.id.micelaous_fee)
    EditText micelaous_fee;
    @BindView(R.id.grand_total)
    TextView grand_total;
    @BindView(R.id.ntn)
    TextView ntn;

    FirebaseDatabase database;
    DatabaseReference databaseReference;
    @BindView(R.id.image)
    Button image;
    @BindView(R.id.doc)
    Button doc;
    @BindView(R.id.layout)
    LinearLayout layout;

    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_voice_reoprt_details);
        ButterKnife.bind(this);

        pd = new ProgressDialog(InVoiceReoprtDetailsActivity.this);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Added_InVoiceReport").child("01");

        image.setOnClickListener(this);
        doc.setOnClickListener(this);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                VoiceReports list = dataSnapshot.getValue(VoiceReports.class);

                if (list!=null) {
                    to.setText(list.getAddresss());
                    particular.setText(list.getParticularss());
                    policy.setText(list.getPolicys());
                    loss.setText(list.getLosss());

                    pofessional_description.setText(list.getPofessional_descriptions());
                    pofessional_amount.setText(list.getPofessional_fees());
                    reinspection_description.setText(list.getReinspection_descriptions());
                    reinspection_fee.setText(list.getReinspection_fees());
                    local_description.setText(list.getConveyance_descriptions());
                    local_fee.setText(list.getLocal_conveyance_fees());
                    photograph_description.setText(list.getPhotographss());
                    photographs_fee.setText(list.getPhotographs_fees());
                    ntn.setText(list.getNtns());

                }else {
                    to.setText("");
                    particular.setText("");
                    policy.setText("");
                    loss.setText("");
                    pofessional_description.setText("");
                    pofessional_amount.setText("0");
                    reinspection_description.setText("");
                    reinspection_fee.setText("0");
                    local_description.setText("");
                    local_fee.setText("0");
                    photograph_description.setText("");
                    photographs_fee.setText("0");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        micelaous_fee.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()){
                    String pofessional_amounts = pofessional_amount.getText().toString();
                    String reinspection_fees = reinspection_fee.getText().toString();
                    String local_fees = local_fee.getText().toString();
                    String photographs_fees = photographs_fee.getText().toString();
                    String micelaous_fees = micelaous_fee.getText().toString();

                    int number = Integer.parseInt(pofessional_amounts) +Integer.parseInt(reinspection_fees)
                            +Integer.parseInt(local_fees)
                            +Integer.parseInt(photographs_fees)
                            +Integer.parseInt(micelaous_fees) ;

                    grand_total.setText(String.valueOf(number));

                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.doc:
                Toast.makeText(getApplicationContext(), "Install Latest Version on Word File From Google Play Store", Toast.LENGTH_SHORT).show();
                break;
            case R.id.image:
                AlertDialog.Builder dialog = new AlertDialog.Builder(InVoiceReoprtDetailsActivity.this);
                dialog.setCancelable(false);
                dialog.setTitle("");
                dialog.setMessage("Are you sure you want to download this Report in Image Format?" );
                dialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //Action for "Delete".

                        pd.setMessage("saving your image");
                        pd.show();


                        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                            @Override
                            public void run() {
                                File file = saveBitMap(InVoiceReoprtDetailsActivity.this,layout);
                                if (file != null) {
                                    pd.cancel();

                                    Toast.makeText(getApplicationContext(), "Report Downloaded Successfully", Toast.LENGTH_SHORT).show();

                                    Log.i("TAG", "Drawing saved to the gallery!");
                                } else {
                                    pd.cancel();
                                    Log.i("TAG", "Oops! Image could not be saved.");
                                }
                            }
                        }, 1000);



                    }
                })
                        .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.show();

                break;
        }
    }

    private File saveBitMap(Context context, View drawView){
        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"Car Insurance App");
        if (!pictureFileDir.exists()) {
            boolean isDirectoryCreated = pictureFileDir.mkdirs();
            if(!isDirectoryCreated)
                Log.i("TAG", "Can't create directory to save the image");
            return null;
        }
        String filename = pictureFileDir.getPath() +File.separator+ System.currentTimeMillis()+".jpg";
        File pictureFile = new File(filename);
        Bitmap bitmap =getBitmapFromView(drawView);
        try {
            pictureFile.createNewFile();
            FileOutputStream oStream = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, oStream);
            oStream.flush();
            oStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue saving the image.");
        }
        scanGallery( context,pictureFile.getAbsolutePath());
        return pictureFile;
    }

    //create bitmap from view and returns it
    private Bitmap getBitmapFromView(View view) {
        //Define a bitmap with the same size as the view
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        //Bind a canvas to it
        Canvas canvas = new Canvas(returnedBitmap);
        //Get the view's background
        Drawable bgDrawable =view.getBackground();
        if (bgDrawable!=null) {
            //has background drawable, then draw it on the canvas
            bgDrawable.draw(canvas);
        }   else{
            //does not have background drawable, then draw white background on the canvas
            canvas.drawColor(Color.WHITE);
        }
        // draw the view on the canvas
        view.draw(canvas);
        //return the bitmap
        return returnedBitmap;
    }

    // used for scanning gallery
    private void scanGallery(Context cntx, String path) {
        try {
            MediaScannerConnection.scanFile(cntx, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                public void onScanCompleted(String path, Uri uri) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("TAG", "There was an issue scanning gallery.");
        }
    }

}

package com.adiljutt.inscurancecar;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.adiljutt.inscurancecar.model.VoiceReports;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InvoiceListDetailActivity extends AppCompatActivity {

    String discountId = "";
    FirebaseDatabase firebaseDatabase;
    DatabaseReference discount;

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

    @BindView(R.id.doc)
    Button doc;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_list_detail);
        ButterKnife.bind(this);

        //Firebase
        firebaseDatabase = FirebaseDatabase.getInstance();
        discount = firebaseDatabase.getReference("InVoiceReport");

        if (getIntent()!=null)
            discountId = getIntent().getStringExtra("case_detail");
        if (!discountId.isEmpty()){
            getDetailProduct(discountId);
        }
        
        doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(InvoiceListDetailActivity.this, "Downloaded and save in Internal Storage" , Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getDetailProduct(String discountId) {
        discount.child(discountId).addValueEventListener(new ValueEventListener() {
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
    }
}

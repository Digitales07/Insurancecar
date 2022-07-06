package com.adiljutt.inscurancecar;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adiljutt.inscurancecar.model.PatsAddedData;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LabourActivity extends AppCompatActivity implements View.OnClickListener{


    @BindView(R.id.claim)
    EditText claim;
    @BindView(R.id.reg)
    EditText reg;
    @BindView(R.id.branch)
    EditText branch;
    @BindView(R.id.parts)
    TextView parts;
    @BindView(R.id.total)
    TextView total;

    @BindView(R.id.drepceiate)
    EditText drepceiate;

    @BindView(R.id.sala)
    EditText sala;

    @BindView(R.id.lols)
    TextView lols;

    @BindView(R.id.add_part)
    Button add_part;


    @BindView(R.id.toatalpartamount)
    TextView toatalpartamount;


    String currentTime;

    FirebaseDatabase database;
    DatabaseReference databaseReference,partsReference;

    //Order by Search
    @BindView(R.id.order_recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.retry)
    Button retry;
    @BindView(R.id.fl_errorView)
    FrameLayout flErrorView;
    @BindView(R.id.fl_recyclerView)
    FrameLayout flRecyclerView;
    //End

    @BindView(R.id.layout)
    LinearLayout layout;


    //List of New Order By Search
    List<PatsAddedData> appointments;
    NewCarPartsAdapter adapter;


    FirebaseAuth mAuth;
    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labour);
        ButterKnife.bind(this);

        currentTime = getIntent().getStringExtra("id");

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Case");
        partsReference = database.getReference("NewCarParts");

        retry.setOnClickListener(this);
        parts.setOnClickListener(this);
        add_part.setOnClickListener(this);

//        drepceiate.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (!charSequence.toString().isEmpty()) {
//                    String totals = total.getText().toString();
//                    String dre = drepceiate.getText().toString();
//
//                    double number = Double.parseDouble(totals) - Integer.parseInt(dre);
//
//                    lols.setText(String.valueOf(number));
//
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
//
//        sala.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (!charSequence.toString().isEmpty()) {
//                    String dep = lols.getText().toString();
//                    String salas = sala.getText().toString();
//
//                    double number = Double.parseDouble(dep) - Integer.parseInt(salas);
//                    toatalpartamount.setText(String.valueOf(number));
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        flErrorView.setVisibility(View.GONE);
        flRecyclerView.setVisibility(View.VISIBLE);
        getAppoinments();
    }

    private void getAppoinments() {
        progressBar.setVisibility(View.VISIBLE);
        appointments = new ArrayList<>();
        partsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointments.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        PatsAddedData medication = snapshot.getValue(PatsAddedData.class);
                        assert medication != null;
                        appointments.add(medication);

                        //calculate total price
                        int totals = 0;
                        for (PatsAddedData order:appointments)
                            totals += (Double.parseDouble(order.getTotals()));
                        Locale locale = new Locale("","");
                        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

                        total.setText(fmt.format(totals));

                    }
                }

                if (appointments.size() > 0){
                    progressBar.setVisibility(View.GONE);
                    layout.setVisibility(View.VISIBLE);
                    setUprecyclerView();
                }else {
                    flRecyclerView.setVisibility(View.GONE);
                    layout.setVisibility(View.GONE);
                    flErrorView.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
//
    private void setUprecyclerView() {
        Collections.reverse(appointments);
        adapter = new NewCarPartsAdapter( LabourActivity.this,appointments);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
//
    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.retry:
                flErrorView.setVisibility(View.GONE);
                flRecyclerView.setVisibility(View.VISIBLE);
                layout.setVisibility(View.VISIBLE);
                getAppoinments();
                break;
            case R.id.parts:
                   Intent intent =  new Intent(LabourActivity.this,AddCarPartActivity.class);
                   intent.putExtra("id",currentTime);
                   startActivity(intent);
                    break;

            case R.id.add_part:
               SavetoDatabase();
               break;
        }
    }

    private void SavetoDatabase() {

        String messageKey = databaseReference.push().getKey();
        String name = total.getText().toString();
        String number = drepceiate.getText().toString();
        String loc = sala.getText().toString();
        String total_prices = toatalpartamount.getText().toString();

        HashMap<String, Object> messageInfoMap = new HashMap<>();
        messageInfoMap.put("total", name);
        messageInfoMap.put("case_id", currentTime);
        messageInfoMap.put("drep", number);
        messageInfoMap.put("salavage", loc);
        messageInfoMap.put("total_prices", total_prices);
        messageInfoMap.put("order_id", messageKey);
        messageInfoMap.put("car_parts",appointments);
        assert messageKey != null;
        databaseReference.child(currentTime).updateChildren(messageInfoMap);


        partsReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        PatsAddedData medication = snapshot.getValue(PatsAddedData.class);
                        assert medication != null;
                        if (medication.getCurrentuser().equals(user)){
                            partsReference.removeValue();
                        }
                    }

                    Toast.makeText(LabourActivity.this, "Case Send Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent  = new Intent(LabourActivity.this,CitiesActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
    }

    class NewCarPartsAdapter extends RecyclerView.Adapter<NewCarPartsAdapter.ViewHolder> {

        private Context mContext;
        private List<PatsAddedData> mUsers;

        NewCarPartsAdapter(Context mContext, List<PatsAddedData> mUsers){
            this.mUsers = mUsers;
            this.mContext = mContext;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_addpartcase, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {

            PatsAddedData user = mUsers.get(position);
            viewHolder.city_name.setText(user.getPart_name());
            viewHolder.cond.setText(user.getConditions());
            viewHolder.dep.setText(user.getDeps());
            viewHolder.total.setText(user.getTotals());
            viewHolder.price.setText(user.getPrices());

//            viewHolder.add.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    bottomSheetDialog.show();
//                }
//            });


        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            TextView city_name,cond,dep,price,total;

            ViewHolder(View itemView) {
                super(itemView);

                city_name = (TextView) itemView.findViewById(R.id.part_name);
                cond = (TextView) itemView.findViewById(R.id.cond);
                dep = (TextView) itemView.findViewById(R.id.dep);
                price = (TextView) itemView.findViewById(R.id.price);
                total = (TextView) itemView.findViewById(R.id.total);
            }

    }

}

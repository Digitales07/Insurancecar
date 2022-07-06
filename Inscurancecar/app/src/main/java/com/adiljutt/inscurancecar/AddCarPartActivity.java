package com.adiljutt.inscurancecar;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.adiljutt.inscurancecar.model.Cases;
import com.adiljutt.inscurancecar.model.Parts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddCarPartActivity extends AppCompatActivity implements View.OnClickListener{

    FirebaseDatabase database;
    DatabaseReference databaseReference,partsReference,car_parts;

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

    @BindView(R.id.search)
    EditText search;

    @BindView(R.id.addssss)
    ImageView addssss;

    //List of New Order By Search
    List<Parts> appointments;
    PartsAdapter adapter;

//    BottomSheetDialog bottomSheetDialog;
//    TextView part_name;
//    EditText condition;
//    EditText dep;
//    EditText price;
//    TextView price_price;
//    TextView dep_price;
//    TextView subtotal;
//    TextView depdiv;
//    TextView enterprice;
//    TextView dep_result;
//    Button add;

    FirebaseAuth mAuth;
    String current_user,currentTime;

    BottomSheetDialog bottomSheetDialog;
    EditText empty_trys;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_part);
        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        current_user = mAuth.getCurrentUser().getUid();

        currentTime = getIntent().getStringExtra("id");

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Added_Parts");
        partsReference = database.getReference("Car_Parts");
        car_parts = database.getReference("Car_Parts");


        retry.setOnClickListener(this);
        addssss.setOnClickListener(this);


        bottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.item_addpart,null);
        empty_trys = (EditText) view.findViewById(R.id.empty_trys);
        add = (Button) view.findViewById(R.id.add);
        bottomSheetDialog.setContentView(view);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveInformationToDataBase();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().isEmpty()){
                    List<Parts> newValet = new ArrayList<>();

                    for (Parts user : appointments){
                        if (user.getPart_name().contains(search.getText().toString())){
                            newValet.add(user);
                        }
                    }

                    adapter = new PartsAdapter(AddCarPartActivity.this,newValet);
                    recyclerView.setAdapter(adapter);

                }else {
                    adapter = new PartsAdapter(AddCarPartActivity.this,appointments);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


    }

    private void SaveInformationToDataBase() {

        hideKeyboard();

        String messageKey = car_parts.push().getKey();

        String company_names = empty_trys.getText().toString();


        if (TextUtils.isEmpty(company_names)) {
            empty_trys.setError("Enter Empty Trys");
            empty_trys.requestFocus();
        }else {

            HashMap<String, Object> messageInfoMap = new HashMap<>();
            messageInfoMap.put("part_id", messageKey);
            messageInfoMap.put("part_name", company_names);


            assert messageKey != null;
            car_parts.child(messageKey).updateChildren(messageInfoMap);

            Toast.makeText(getApplicationContext(), "New Car Part Added", Toast.LENGTH_SHORT).show();
            bottomSheetDialog.dismiss();
            empty_trys.setText("");

        }

    }

    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) Objects.requireNonNull(getSystemService(Context.INPUT_METHOD_SERVICE))).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
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
                        Parts medication = snapshot.getValue(Parts.class);
                        assert medication != null;
                        appointments.add(medication);
                    }
                }

                if (appointments.size() > 0){
                    progressBar.setVisibility(View.GONE);
                    setUprecyclerView();
                }else {
                    flRecyclerView.setVisibility(View.GONE);
                    flErrorView.setVisibility(View.VISIBLE);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void setUprecyclerView() {
        Collections.reverse(appointments);
        adapter = new PartsAdapter( AddCarPartActivity.this,appointments);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.retry:
                flErrorView.setVisibility(View.GONE);
                flRecyclerView.setVisibility(View.VISIBLE);
                getAppoinments();
                break;
            case R.id.addssss:
                bottomSheetDialog.show();
                break;
        }
    }

    class PartsAdapter extends RecyclerView.Adapter<PartsAdapter.ViewHolder> {

        private Context mContext;
        private List<Parts> mUsers;

        PartsAdapter(Context mContext, List<Parts> mUsers){
            this.mUsers = mUsers;
            this.mContext = mContext;
        }

        @NonNull
        @Override
        public PartsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_parts, parent, false);
            return new PartsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PartsAdapter.ViewHolder viewHolder, int position) {

            Parts user = mUsers.get(position);
            viewHolder.city_name.setText(user.getPart_name());
//            part_name.setText(user.getPart_name());

            viewHolder.add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  Intent  intent = new Intent(mContext,CasePartActivity.class);
                  intent.putExtra("case_detail",user.getPart_id());
                  startActivity(intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return mUsers.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            TextView city_name,add;

            ViewHolder(View itemView) {
                super(itemView);

                city_name = (TextView) itemView.findViewById(R.id.city_name);
                add = (TextView) itemView.findViewById(R.id.add);

            }
        }
    }


}

package com.adiljutt.inscurancecar;

import android.content.Intent;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.adiljutt.inscurancecar.adapter.CasesAdapter;
import com.adiljutt.inscurancecar.adapter.CitiesAdapter;
import com.adiljutt.inscurancecar.model.Cases;
import com.adiljutt.inscurancecar.model.Cities;
import com.ferfalk.simplesearchview.SimpleSearchView;
import com.ferfalk.simplesearchview.utils.DimensUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    String currentTime;

    public static final int EXTRA_REVEAL_CENTER_PADDING = 40;
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

    //Firebase
    FirebaseDatabase db;
    DatabaseReference pharmacies;


    //List of New Order By Search
    List<Cases> appointments;
    CasesAdapter adapter;

    @BindView(R.id.searchView)
    SimpleSearchView searchView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        db = FirebaseDatabase.getInstance();
        pharmacies = db.getReference("Case");

        retry.setOnClickListener(this);


        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setTitle("Insurance Case");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        retry.setOnClickListener(this);

        currentTime = getIntent().getStringExtra("site");
        Toast.makeText(this, ""+currentTime, Toast.LENGTH_SHORT).show();

        searchView.setOnQueryTextListener(new SimpleSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d("SimpleSearchView", "Submit:" + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("SimpleSearchView", "Text changed:" + newText);

                List<Cases> newValet = new ArrayList<>();

                for (Cases user : appointments){
                    if (user.getInsurance_ids().contains(newText)){
                        newValet.add(user);
                    }
                }

                adapter = new CasesAdapter(SearchActivity.this,newValet);
                recyclerView.setAdapter(adapter);


                return true;
            }

            @Override
            public boolean onQueryTextCleared() {
                Log.d("SimpleSearchView", "Text cleared");
                return false;
            }
        });

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
        pharmacies.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                appointments.clear();
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0){
                    for (DataSnapshot snapshot:dataSnapshot.getChildren()){
                        Cases medication = snapshot.getValue(Cases.class);
                        assert medication != null;
                        if (medication.getCity_name().equals(currentTime)){
                          appointments.add(medication);
                        }
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
        adapter = new CasesAdapter( SearchActivity.this,appointments);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.retry:
                flErrorView.setVisibility(View.GONE);
                flRecyclerView.setVisibility(View.VISIBLE);
                getAppoinments();
                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        setupSearchView(menu);
        return true;
    }
    private void setupSearchView(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
//        searchView.setTabLayout(tabLayout);

        // Adding padding to the animation because of the hidden menu item
        Point revealCenter = searchView.getRevealAnimationCenter();
        revealCenter.x -= DimensUtils.convertDpToPx(EXTRA_REVEAL_CENTER_PADDING, this);
    }

    @Override
    public void onBackPressed() {
        if (searchView.onBackPressed()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (searchView.onActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}

package com.adiljutt.inscurancecar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.adiljutt.inscurancecar.model.Cities;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private Cities site;

    @BindView(R.id.city_name)
    TextView city_name;

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.add_new_case)
    Button add_new_case;
    @BindView(R.id.search_case)
    Button search_case;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (getIntent().hasExtra("site")) {
            this.site = (Cities) getIntent().getSerializableExtra("site");
        }

        if(site==null){
            finish();
        }

        city_name.setText(site.getName());
        back.setOnClickListener(this);
        add_new_case.setOnClickListener(this);
        search_case.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.back:
                Intent intent = new Intent(MainActivity.this,CitiesActivity.class);
                startActivity(intent);
                finish();
                break;

            case R.id.search_case:
                Intent add_new_case = new Intent(MainActivity.this,SearchActivity.class);
                add_new_case.putExtra("site", city_name.getText().toString());
                startActivity(add_new_case);
                finish();
                break;
            case R.id.add_new_case:
                Intent search_case = new Intent(MainActivity.this,AddNewCaseActivity.class);
                search_case.putExtra("site", city_name.getText().toString());
                startActivity(search_case);
                finish();
                break;
        }
    }
}

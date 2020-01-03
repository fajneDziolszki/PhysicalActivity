package com.example.student.physicalactivityapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GraphActivity extends AppCompatActivity {

    @BindView(R.id.btn_goToResults)
    Button btn_goToResults;

    @OnClick(R.id.btn_goToResults)
    void onClick(View view) {
        Intent intent = new Intent(this, ResultsActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        ButterKnife.bind(this);

    }
}

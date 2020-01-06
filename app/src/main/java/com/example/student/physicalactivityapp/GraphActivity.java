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

    private float[] accelerometer_Time;
    private float[] accelerometer_X;
    private float[] accelerometer_Y;
    private float[] accelerometer_Z;


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

        Bundle extras = getIntent().getExtras();
        GetAccelerometerData(extras);
    }

    private void GetAccelerometerData(Bundle extras){
        accelerometer_Time = extras.getFloatArray(DataModel.ACCELEROMETER+DataModel.TIME);
        accelerometer_X = extras.getFloatArray(DataModel.ACCELEROMETER+DataModel.X);
        accelerometer_Y = extras.getFloatArray(DataModel.ACCELEROMETER+DataModel.Y);
        accelerometer_Z = extras.getFloatArray(DataModel.ACCELEROMETER+DataModel.Z);
    }
}

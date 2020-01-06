package com.example.student.physicalactivityapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.LineChart;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class GraphActivity extends AppCompatActivity {

    private float[] accelerometer_Time;
    private float[] accelerometer_X;
    private float[] accelerometer_Y;
    private float[] accelerometer_Z;

    private float[] gyroskope_Time;
    private float[] gyroskope_X;
    private float[] gyroskope_Y;
    private float[] gyroskope_Z;

    @BindView(R.id.chart)
    LineChart chart;
    @BindView(R.id.sensor_options)
    RadioGroup sensorOption;
    @BindView(R.id.accelerometer)
    RadioButton accelerometer;
    @BindView(R.id.gyroskope)
    RadioButton gyroskope;

/*    @OnCheckedChanged(R.id.sensor_options)
    void onChange(RadioGroup group, int checkedId){
        switch(checkedId){
            case R.id.accelerometer:
                DrawChart(DataModel.ACCELEROMETER);
                break;
            case R.id.gyroskope:
                DrawChart(DataModel.GYROSKOPE);
                break;
        }
    }*/

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
        LineChartComponent.InitializeChart(chart);
        Bundle extras = getIntent().getExtras();
        SetAccelerometerData(extras);
        SetGyroskopeData(extras);
        DrawChart(DataModel.ACCELEROMETER);

    }

    private void SetAccelerometerData(Bundle extras){
        accelerometer_Time = extras.getFloatArray(DataModel.ACCELEROMETER+DataModel.TIME);
        accelerometer_X = extras.getFloatArray(DataModel.ACCELEROMETER+DataModel.X);
        accelerometer_Y = extras.getFloatArray(DataModel.ACCELEROMETER+DataModel.Y);
        accelerometer_Z = extras.getFloatArray(DataModel.ACCELEROMETER+DataModel.Z);
    }

    private void SetGyroskopeData(Bundle extras){
        gyroskope_Time = extras.getFloatArray(DataModel.GYROSKOPE+DataModel.TIME);
        gyroskope_X = extras.getFloatArray(DataModel.GYROSKOPE+DataModel.X);
        gyroskope_Y = extras.getFloatArray(DataModel.GYROSKOPE+DataModel.Y);
        gyroskope_Z = extras.getFloatArray(DataModel.GYROSKOPE+DataModel.Z);
    }

    private void DrawChart(String type){
        switch(type){
            case "0":
                SetValuesInChart(accelerometer_Time,accelerometer_X,accelerometer_Y,accelerometer_Z);
                break;
            case "1":
                SetValuesInChart(gyroskope_Time,gyroskope_X,gyroskope_Y,gyroskope_Z);
                break;
        }
    }

    private void SetValuesInChart(final float[] time, final float[] x, final float[] y, final float[] z) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < time.length; i++) {
                    LineChartComponent.AddEntry(chart,x[i],time[i],0, Color.RED);
                    LineChartComponent.AddEntry(chart,y[i],time[i],1, Color.GREEN);
                    LineChartComponent.AddEntry(chart,z[i],time[i],2, Color.BLUE);
                }
            }
        });

        thread.start();

    }

}

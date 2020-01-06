package com.example.student.physicalactivityapp;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.LineData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GraphActivity extends AppCompatActivity {

    private float[] accelerometer_Time;
    private float[] accelerometer_X;
    private float[] accelerometer_Y;
    private float[] accelerometer_Z;

    private float[] magnetometer_Time;
    private float[] magnetometer_X;
    private float[] magnetometer_Y;
    private float[] magnetometer_Z;
    private String option;
    boolean plotData = true;

    @BindView(R.id.chart)
    LineChart chart;

    @BindView(R.id.sensor_options)
    RadioGroup sensorOption;
    @BindView(R.id.accelerometer)
    RadioButton accelerometer;
    @BindView(R.id.magnetometer)
    RadioButton magnetometer;

    @BindView(R.id.btn_goToResults)
    Button btn_goToResults;

    @BindView(R.id.btn_Rysuj)
    Button btn_Rysuj;

    Thread thread;

    @OnClick(R.id.btn_goToResults)
    void onClick(View view) {
        Intent intent = new Intent(this, ResultsActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.btn_Rysuj)
    void onClickBtn(View view){
        StopPlot();
        DrawChart(option);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        ButterKnife.bind(this);
        option = DataModel.ACCELEROMETER;

        LineChartComponent.InitializeChart(chart);
        Bundle extras = getIntent().getExtras();
        SetAccelerometerData(extras);
        SetMagnetometerData(extras);
        InitOnCheckedChange();
    }

    public void InitOnCheckedChange(){
        sensorOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                StopPlot();
                switch(checkedId){
                    case R.id.accelerometer:
                        option=DataModel.ACCELEROMETER;
                        break;
                    case R.id.magnetometer:
                        option=DataModel.MAGNETOMETER;
                        break;
                }
            }
        });
    }

    private void SetAccelerometerData(Bundle extras){
        accelerometer_Time = extras.getFloatArray(DataModel.ACCELEROMETER+DataModel.TIME);
        accelerometer_X = extras.getFloatArray(DataModel.ACCELEROMETER+DataModel.X);
        accelerometer_Y = extras.getFloatArray(DataModel.ACCELEROMETER+DataModel.Y);
        accelerometer_Z = extras.getFloatArray(DataModel.ACCELEROMETER+DataModel.Z);
    }

    private void SetMagnetometerData(Bundle extras){
        magnetometer_Time = extras.getFloatArray(DataModel.MAGNETOMETER+DataModel.TIME);
        magnetometer_X = extras.getFloatArray(DataModel.MAGNETOMETER+DataModel.X);
        magnetometer_Y = extras.getFloatArray(DataModel.MAGNETOMETER+DataModel.Y);
        magnetometer_Z = extras.getFloatArray(DataModel.MAGNETOMETER+DataModel.Z);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void StopPlot(){

        LineData data = chart.getData();

        if(data!=null){
            chart.clear();
            LineChartComponent.InitializeChart(chart);
        }
    }

    private void DrawChart(String type){
        YAxis axis= chart.getAxisLeft();
            switch(type){
                case "0":
                    axis.setAxisMinimum(-30);
                    axis.setAxisMaximum(30);
                    StartPlot(accelerometer_Time,accelerometer_X,accelerometer_Y,accelerometer_Z);
                    break;
                case "5":
                    axis.setAxisMinimum(-70);
                    axis.setAxisMaximum(70);
                    StartPlot(magnetometer_Time,magnetometer_X,magnetometer_Y,magnetometer_Z);
                    break;
            }
    }

    private void StartPlot(final float[] time, final float[] x, final float[] y, final float[] z) {

        if(time.length==0){
            Toast.makeText(getApplicationContext(),"Brak danych", Toast.LENGTH_LONG).show();
            return;
        } else {
            Toast.makeText(getApplicationContext(),"Trwa wczytywanie danych...", Toast.LENGTH_LONG).show();
        }

        for (int i = 0; i < time.length; i++) {
            LineChartComponent.AddEntry(chart,x[i],time[i],0, Color.RED);
            LineChartComponent.AddEntry(chart,y[i],time[i],1, Color.GREEN);
            LineChartComponent.AddEntry(chart,z[i],time[i],2, Color.BLUE);
        }

        /*thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    for (int i = 0; i < time.length; i++) {
                        LineChartComponent.AddEntry(chart,x[i],time[i],0, Color.RED);
                        LineChartComponent.AddEntry(chart,y[i],time[i],1, Color.GREEN);
                        LineChartComponent.AddEntry(chart,z[i],time[i],2, Color.BLUE);
                    }
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        thread.start();*/
    }

}

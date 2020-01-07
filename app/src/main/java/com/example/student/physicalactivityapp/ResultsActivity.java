package com.example.student.physicalactivityapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResultsActivity extends AppCompatActivity {
    private ArrayList<DataModel> data1 = new ArrayList<DataModel>();
    private ArrayList<DataModel> data2 = new ArrayList<DataModel>();
    private String option;
    private float[] acc_Time;
    private float[] mag_Time;
    private float[] acc_Class;
    private float[] mag_Class;
    boolean plotData = true;
    @BindView(R.id.chart)
    BarChart chart;

    @BindView(R.id.sensor_options)
    RadioGroup sensorOption;
    @BindView(R.id.accelerometer)
    RadioButton accelerometer;
    @BindView(R.id.magnetometer)
    RadioButton magnetometer;

    @BindView(R.id.btn_Rysuj)
    Button btn_Rysuj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ButterKnife.bind(this);
        option = DataModel.ACCELEROMETER;

        String result = MainActivity.checkedRadioBtn;
        ArrayList<DataModel> data = MainActivity.readFileData;
        ReadTrainFile(result);
        ArrayList<DataModel> dataForAcc=ReturnGroupedListForSensor(data,"0");
        ArrayList<DataModel> dataForMagnetometer=ReturnGroupedListForSensor(data,"5");
        ArrayList<DataModel> resultAcc=TrainData(dataForAcc,data1);
        ArrayList<DataModel> resultMag=TrainData(dataForMagnetometer,data2);
        SetData(resultAcc,resultMag);
        InitOnCheckedChange();
    }

    private void SetData(ArrayList<DataModel> resultAcc, ArrayList<DataModel> resultMag) {
        acc_Time = new float[resultAcc.size()];
        acc_Class = new float[resultAcc.size()];
        mag_Time = new float[resultMag.size()];
        mag_Class = new float[resultMag.size()];
        for(int i=0;i< resultAcc.size();i++){
            acc_Time[i]=Float.parseFloat(resultAcc.get(i).czas);
            acc_Class[i]=resultAcc.get(i).czynnosc;
        }
        for(int i=0;i< resultMag.size();i++){
            mag_Time[i]=Float.parseFloat(resultMag.get(i).czas);
            mag_Class[i]=resultMag.get(i).czynnosc;
        }
    }

    @OnClick(R.id.btn_Rysuj)
    void onClickBtn(View view){
        StopPlot();
        DrawChart(option);
    }
    public void StopPlot(){

        BarData data = chart.getData();

        if(data!=null){
            chart.clear();
        }
    }

    private void DrawChart(String type){
        ArrayList<BarEntry> values = new ArrayList<>();
        switch(type) {
            case "0":
                for (int i = 0; i < acc_Time.length; i++) {
                    values.add(new BarEntry(acc_Time[i], acc_Class[i]));
                }
                break;
            case "5":
                for (int i = 0; i < mag_Time.length; i++) {
                    values.add(new BarEntry(mag_Time[i], mag_Class[i]));
                }
                break;
        }
        BarDataSet set1;
            set1 = new BarDataSet(values, "Klasyfikacja czynności");

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);

            chart.setData(data);
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

    private ArrayList<DataModel> TrainData(ArrayList<DataModel> list, ArrayList<DataModel> data2) {
        Classificator classificator = new Classificator();
        for(DataModel item:list){
            int classItem = classificator.Classify(item,5,data2);
            item.czynnosc=classItem;
        }
        return list;
    }

    private ArrayList<DataModel> ReturnGroupedListForSensor(ArrayList<DataModel> data, String s) {
        ArrayList<DataModel> list = new ArrayList<DataModel>();
        for(DataModel dataItem:data){
            if(dataItem.czujnik.equals(s)){
                list.add(dataItem);
            }
        }
        return list;
    }

    private void ReadTrainFile(String result) {
        String path = "";
        switch (result) {
            case "hip":
                path = "kieszen_akcelerometr";
                break;
            case "chest":
                path = "telefon_pasek";
                break;
        }
        String[] list;
        try {
            list = getAssets().list(path);
            StringBuilder text = new StringBuilder();
            if (list.length > 0) {
                for (String file : list) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open(path+"/"+file)));
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(";");
                        String czujnik = parts[0]; // 004
                        if(czujnik.equals("0") || czujnik.equals("5")) {
                            String czas = parts[1]; // 034556
                            int czynnosc = ReturnClass(czas, path + "/" + file);
                            String x = parts[2]; // 004
                            String y = parts[3]; // 034556
                            String z = parts[4]; // 004
                        if(czujnik.equals("0")) {
                            data1.add(new DataModel(czynnosc, x, y, z));
                        }
                        else
                            data2.add(new DataModel(czynnosc, x, y, z));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int ReturnClass(String czas, String path) {
        //0-stanie
        //1-siedzienie
        //2-spacer
        int timeInt = Integer.parseInt(czas);
        int fistTime=10000;
        int secondTime=20000;
        int thirdTime=30000;
        if(path=="kieszen_akcelerometr/ang.txt" || path=="kieszen_akcelerometr/kla.txt"){
            fistTime+=10000;
            secondTime+=10000;
            thirdTime+=10000;
        }
        if(timeInt<=fistTime || (timeInt>secondTime && timeInt<=thirdTime)){
            return 0;
        }
        else if(timeInt>fistTime && timeInt<=secondTime){
            return 1;

        }
        else
            return 2;
    }
}
package com.example.student.physicalactivityapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {
    private ArrayList<DataModel> data1 = new ArrayList<DataModel>();
    private ArrayList<DataModel> data2 = new ArrayList<DataModel>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        String result = MainActivity.checkedRadioBtn;
        ReadTrainFile(result);
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
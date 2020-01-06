package com.example.student.physicalactivityapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {
    private ArrayList<DataModel> dane = new ArrayList<DataModel>();
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
                        if(czujnik=="0" || czujnik=="5") {
                            String czas = parts[1]; // 034556
                            String x = parts[2]; // 004
                            String y = parts[3]; // 034556
                            String z = parts[4]; // 004

                            dane.add(new DataModel(czujnik, czas, x, y, z));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
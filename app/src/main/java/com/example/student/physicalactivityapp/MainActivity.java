package com.example.student.physicalactivityapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 1000;
    public static String checkedRadioBtn = "";
    private ArrayList<DataModel> dane;

    @Nullable
    @BindView(R.id.btn_next)
    Button btn_next;

    @Nullable
    @BindView(R.id.btn_addData)
    Button btn_addData;

    @Nullable
    @BindView(R.id.RGroup)
    RadioGroup radio_group;


    @Nullable
    @OnClick(R.id.btn_next)
    void onClick(View view) {
        Intent intent = new Intent(this, GraphActivity.class);
        Bundle bundle = new Bundle();

        bundle.putFloatArray(DataModel.ACCELEROMETER+DataModel.TIME,DataModel.GetData(dane, DataModel.ACCELEROMETER, DataModel.TIME));
        bundle.putFloatArray(DataModel.ACCELEROMETER+DataModel.X,DataModel.GetData(dane, DataModel.ACCELEROMETER, DataModel.X));
        bundle.putFloatArray(DataModel.ACCELEROMETER+DataModel.Y,DataModel.GetData(dane, DataModel.ACCELEROMETER, DataModel.Y));
        bundle.putFloatArray(DataModel.ACCELEROMETER+DataModel.Z,DataModel.GetData(dane, DataModel.ACCELEROMETER, DataModel.Z));

        bundle.putFloatArray(DataModel.GYROSKOPE+DataModel.TIME,DataModel.GetData(dane, DataModel.GYROSKOPE, DataModel.TIME));
        bundle.putFloatArray(DataModel.GYROSKOPE+DataModel.X,DataModel.GetData(dane, DataModel.GYROSKOPE, DataModel.X));
        bundle.putFloatArray(DataModel.GYROSKOPE+DataModel.Y,DataModel.GetData(dane, DataModel.GYROSKOPE, DataModel.Y));
        bundle.putFloatArray(DataModel.GYROSKOPE+DataModel.Z,DataModel.GetData(dane, DataModel.GYROSKOPE, DataModel.Z));

        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Nullable
    @OnClick(R.id.btn_addData)
    void onClickLoadData(View view) {
        //performFileSearch();
        dane = readDataFromSource();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setRadioButtonOption();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);

        }
    }

    private void setRadioButtonOption() {
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.rb_chest:
                        checkedRadioBtn = "chest";
                        break;
                    case R.id.rb_hip:
                        checkedRadioBtn = "hip";
                        break;
                }
            }
        });
    }
    private ArrayList<DataModel> readText(String input) {
        ArrayList<DataModel> dane = new ArrayList<DataModel>();
        File file = new File(input);
        StringBuilder text = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                String czujnik = parts[0]; // 004
                String czas = parts[1]; // 034556
                String x = parts[2]; // 004
                String y = parts[3]; // 034556
                String z = parts[4]; // 004

                dane.add(new DataModel(czujnik, czas, x, y, z));

            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        //dane.get(2).x;
        return dane;
    }

    private void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("text/*");
        startActivityForResult(intent, READ_REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            if (data != null) {
                Uri uri = data.getData();
                String path = uri.getPath();
                path = path.substring(path.indexOf(":") + 1);
                readText(path);
                Toast.makeText(this, "" + path, Toast.LENGTH_SHORT).show();
            }
        }
    }
    private ArrayList<DataModel> readDataFromSource() {
        InputStream is=getResources().openRawResource(R.raw.pati);
        ArrayList<DataModel> dane = new ArrayList<DataModel>();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );
        String line="";
        try {
            while((line=reader.readLine()) !=null){
                String[] parts = line.split(";");
                String czujnik = parts[0]; // 004
                String czas = parts[1]; // 034556
                String x = parts[2]; // 004
                String y = parts[3]; // 034556
                String z = parts[4]; // 004
                dane.add(new DataModel(czujnik, czas, x, y, z));
            }
        } catch (IOException e) {
            Log.wtf("MyActivity", "Error reading data file on line" + line, e);
            e.printStackTrace();
        }
        return dane;
    }
}

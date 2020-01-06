package com.example.student.physicalactivityapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_STORAGE = 1000;
    private static final int READ_REQUEST_CODE = 1000;

    @Nullable
    @BindView(R.id.btn_next)
    Button btn_next;

    @Nullable
    @BindView(R.id.btn_addData)
    Button btn_addData;

    @Nullable
    @OnClick(R.id.btn_next)
    void onClick(View view) {
        Intent intent = new Intent(this, GraphActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Nullable
    @OnClick(R.id.btn_addData)
    void onClickLoadData(View view) {
        performFileSearch();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_STORAGE);

        }
    }

    private String readText(String input) {
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


                text.append(line);
                text.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();

        }
        //dane.get(2).x;
        return text.toString();
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


}

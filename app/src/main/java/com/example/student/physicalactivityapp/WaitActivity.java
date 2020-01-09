package com.example.student.physicalactivityapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;

public class WaitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);

        ButterKnife.bind(this);
        threadforLottie();

    }

    public void threadforLottie() {
        Thread myThread = new Thread(){

            @Override
            public void run() {
                try{
                    sleep(4000);
                    Intent intent = new Intent(WaitActivity.this, ResultsActivity.class);
                    startActivity(intent);
                    }
                catch (InterruptedException e)
                {e.printStackTrace();}
            }
        };
        myThread.start();

    }
}

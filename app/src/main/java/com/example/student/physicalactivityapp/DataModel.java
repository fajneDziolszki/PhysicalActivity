package com.example.student.physicalactivityapp;

import android.content.Intent;

/**
 * Created by patry on 05.01.2020.
 */

public class DataModel {
    //deklaracja zmiennych.
    String x, y, z;
    String czas;
    String czujnik;

    public DataModel(String czujnik, String czas, String x, String y, String z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.czas = czas;
        this.czujnik = czujnik;
    }

    //Metody ustawia i zwracania.
    public String getx() {
        return x;
    }

    public String gety() {
        return y;
    }

    public String getz() {
        return z;
    }

    public String getczas() {
        return czas;
    }

    public String getczujnik() {
        return czujnik;
    }


}


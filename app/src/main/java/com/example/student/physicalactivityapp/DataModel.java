package com.example.student.physicalactivityapp;

import java.util.ArrayList;

public class DataModel {

    static String ACCELEROMETER = "0";
    static String GYROSKOPE = "1";
    static String GRAVITY = "2";
    static String LINEAR_ACCELERATION = "3";
    static String GEOMAGNETIC_ROTATION = "4";
    static String MAGNETOMETER = "5";
    static String ROTATION = "6";

    static String TIME = "TIME";
    static String X = "X";
    static String Y = "Y";
    static String Z = "Z";

    //deklaracja zmiennych.
    String x, y, z;
    String czas;
    String czujnik;
    int czynnosc;

    public DataModel(String czujnik, String czas, String x, String y, String z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.czas = czas;
        this.czujnik = czujnik;
    }

    public DataModel(int czynnosc, String x, String y, String z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.czynnosc = czynnosc;
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

    public static float[] GetData(ArrayList<DataModel>allData, String sensorType, String valueType){

        ArrayList<Float> sensorData = new ArrayList<>();

        for (DataModel item:allData) {
            if(item.czujnik.equals(sensorType)){
                sensorData.add(GetFieldValue(valueType,item));
                //sensorData.add(new float[]{Float.parseFloat(item.czas),Float.parseFloat(item.x),Float.parseFloat(item.y),Float.parseFloat(item.z)});
            }
        }
        return ConvertListToArray(sensorData);
    }

    private static float GetFieldValue(String valueType, DataModel item){

        String value="0";

        switch(valueType){
            case "TIME":
                value = item.czas;
                break;
            case "X":
                value = item.x;
                break;
            case "Y":
                value = item.y;
                break;
            case "Z":
                value = item.z;
                break;
        }

        return Float.parseFloat(value);
    }

    private static float[] ConvertListToArray(ArrayList<Float> arrList){
        final float[] arr = new float[arrList.size()];
        int index = 0;
        for (final Float value: arrList) {
            arr[index++] = value;
        }

        return arr;
    }


}


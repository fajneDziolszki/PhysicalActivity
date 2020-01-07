package com.example.student.physicalactivityapp;

import java.util.Comparator;

public class Neighbor {
    DataModel model;
    double distance;

    public Neighbor(DataModel model, double distance) {
        this.model = model;
        this.distance = distance;
    }

    public DataModel getModel() {
        return model;
    }

    public void setModel(DataModel model) {
        this.model = model;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
class SortByDistance implements Comparator<Neighbor>
{
    @Override
    public int compare(Neighbor neighbor, Neighbor t1) {
        return (int) (neighbor.distance-t1.distance);
    }
}

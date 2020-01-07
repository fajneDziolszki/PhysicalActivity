package com.example.student.physicalactivityapp;

import java.util.ArrayList;

public class Classificator {

    public int Classify(DataModel nItem, int k, ArrayList<DataModel> Items) {

        // Hold nearest neighbors.
        // First item is distance,
        // second class
        ArrayList<DataModel> neighbors = new ArrayList<DataModel>();

        for (DataModel item : Items) {

            // Find Euclidean Distance
            double distance = EuclideanDistance(nItem, item);

            // Update neighbors, either adding
            // the current item in neighbors
            // or not.
            neighbors = UpdateNeighbors(neighbors, item, distance, k);
        }
        // Count the number of each
        ArrayList<Classes> count = CalculateNeighborsClass(neighbors, k);


        // Find the max in count, aka the
        // class with the most appearances.
        return FindMax(count);
    }

    private ArrayList<Classes> CalculateNeighborsClass(ArrayList<DataModel> neighbors, int k) {
        ArrayList<Classes> classificator = new ArrayList<Classes>();
        return classificator;
    }

    private double EuclideanDistance(DataModel x, DataModel y) {

        // The sum of the squared
        // differences of the elements
        double s = 0;
        s += Math.pow(Double.parseDouble(x.x) - Double.parseDouble(y.x), 2);
        s += Math.pow(Double.parseDouble(x.y) - Double.parseDouble(y.y), 2);
        s += Math.pow(Double.parseDouble(x.z) - Double.parseDouble(y.z), 2);

        // The square root of the sum
        return Math.sqrt(s);
    }

    private ArrayList<DataModel> UpdateNeighbors(ArrayList<DataModel> list, DataModel item, double distance, int k) {

        return list;
    }

    private int FindMax(ArrayList<Classes> classes) {
        Classes maxItem = classes.get(0);
        for(Classes classItem:classes) {
            if(maxItem.count<classItem.count){
                maxItem=classItem;
            }
        }
        return maxItem.classIdx;
    }
}
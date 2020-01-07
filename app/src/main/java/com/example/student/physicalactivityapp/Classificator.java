package com.example.student.physicalactivityapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class Classificator {

    public int Classify(DataModel nItem, int k, ArrayList<DataModel> Items) {

        // Hold nearest neighbors.
        // First item is distance,
        // second class
        ArrayList<Neighbor> neighbors = new ArrayList<Neighbor>();

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

    private ArrayList<Classes> CalculateNeighborsClass(ArrayList<Neighbor> neighbors, int k) {
        ArrayList<Classes> classificator = new ArrayList<Classes>();
        classificator.add(new Classes(0,0));
        classificator.add(new Classes(1,0));
        classificator.add(new Classes(2,0));

        for(int i=0; i<k;i++){
            int classId = neighbors.get(i).model.czynnosc;
            if(classId==0){
                classificator.get(0).count+=1;
            }
            else if(classId==1){
                classificator.get(1).count+=1;
            }
            else{
                classificator.get(2).count+=1;
            }
        }
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

    private ArrayList<Neighbor> UpdateNeighbors(ArrayList<Neighbor> list, DataModel item, double distance, int k) {
        if (list.size() < k) {

            //List is not full, add
            // new item and sort
            Neighbor neighbor= new Neighbor(item,distance);
            list.add(neighbor);
            Collections.sort(list, new Comparator<Neighbor>() {
                @Override
                public int compare(Neighbor neighbor, Neighbor t1) {
                    return (int) (neighbor.distance - t1.distance);
                }
            });
        }
    else {

            // List is full Check if new
            // item should be entered
            if (list.get(0).distance > distance) {
                list.remove(0);
                Neighbor neighbor = new Neighbor(item, distance);
                list.add(neighbor);
                Collections.sort(list, new Comparator<Neighbor>() {
                    @Override
                    public int compare(Neighbor neighbor, Neighbor t1) {
                        return (int) (neighbor.distance - t1.distance);
                    }
                });
            }
        }

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
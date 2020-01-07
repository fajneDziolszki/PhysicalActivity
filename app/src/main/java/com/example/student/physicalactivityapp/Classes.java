package com.example.student.physicalactivityapp;

public class Classes {

    public Classes(int classIdx, int count) {
        this.classIdx = classIdx;
        this.count = count;
    }

    int classIdx,count;

    public int getClassIdx() {
        return classIdx;
    }

    public void setClassIdx(int classIdx) {
        this.classIdx = classIdx;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}

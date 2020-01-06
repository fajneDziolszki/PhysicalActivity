package com.example.student.physicalactivityapp;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

public class LineChartComponent {

    public static LineChart InitializeChart(LineChart mChart){
        // enable description text
        mChart.getDescription().setEnabled(true);
        // enable touch gestures
        mChart.setTouchEnabled(true);
        // enable scaling and dragging
        mChart.setDragEnabled(false);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        // if disabled, scaling can be done on x- and y-axis separately
        mChart.setPinchZoom(true);
        // set an alternative background color
        mChart.setBackgroundColor(Color.WHITE);


        XAxis xl = mChart.getXAxis();
        xl.setTextColor(Color.WHITE);
        xl.setDrawGridLines(true);
        xl.setAvoidFirstLastClipping(true);
        xl.setEnabled(true);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.setAxisMaximum(10f);
        leftAxis.setAxisMinimum(-10f);
        leftAxis.setDrawGridLines(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        AddLineData(mChart,Color.WHITE);

        mChart.getAxisLeft().setDrawGridLines(false);
        mChart.getXAxis().setDrawGridLines(false);
        mChart.setDrawBorders(false);

        Legend l = mChart.getLegend();
        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTextColor(Color.WHITE);
        mChart.getLegend().setEnabled(false);

        return mChart;
    }

    public static void AddLineData(LineChart mChart, Integer color){
        LineData data = new LineData();
        data.setValueTextColor(color);
        // add empty data
        mChart.setData(data);
        // get the legend (only possible after setting data)
    }

    public static LineChart AddEntry(LineChart mChart, float value, float time, int datasetIndex, Integer color) {

        LineData data = mChart.getData();

        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(datasetIndex);

            if (set == null) {
                set = CreateSet(color,datasetIndex);
                data.addDataSet(set);
            }
            set.addEntry(new Entry(time,value));
            //data.addEntry(new Entry(time, value), 0);
            data.notifyDataChanged();
            // let the chart know it's data has changed
            mChart.notifyDataSetChanged();
            // limit the number of visible entries
            //mChart.setVisibleXRangeMaximum(150);
            // move to the latest entry
            mChart.moveViewToX(data.getEntryCount());
        }

        return mChart;
    }

    private static LineDataSet CreateSet(Integer color,Integer datasetIndex){
        LineDataSet set = new LineDataSet(null, String.valueOf(datasetIndex));
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setLineWidth(3f);
        set.setColor(color);
        set.setHighlightEnabled(false);
        set.setDrawValues(false);
        set.setDrawCircles(false);
        set.setMode(LineDataSet.Mode.LINEAR);
        set.setCubicIntensity(0.2f);
        return set;
    }

}

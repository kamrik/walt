package org.chromium.latency.walt;

import android.content.Context;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Narrowly specialized BarChart for presenting histograms of the particular types WALT cares about.
 */
public class HistogramChart extends BarChart {

    public float min = 0;
    public float max = 120;
    public float binSize = 1;

    private int nSets = 1;  // How many datasets = histograms to draw, it's either 1 or 2.
    private float yMax = 0;

    private int nBins;
    private BarEntry[][] dataEntries;

    public HistogramChart(Context context) {
        super(context);
        initHistogram();
    }

    public HistogramChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHistogram();
    }

    public HistogramChart(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initHistogram();
    }


    public void addDataPoint(double val, int dataSetIndex) {
        // If this is the first ever data point we add
        nBins = (int)((max - min) / binSize);
        if (dataEntries == null) {
            dataEntries = new BarEntry[nSets][nBins];
        }

        int bin = (int) Math.round((val - min)/binSize);
        if (bin < 0) {
            bin = 0;
        } else if (bin >= nBins) {
            bin = nBins-1;
        }

        float x = bin;
        // Slight shift right or left if we have two data sets.
        if (nSets > 1) {
            x += 0.2f * Math.signum(dataSetIndex * 2 - 1);
        }

        BarEntry e = dataEntries[dataSetIndex][bin];
        if (e == null) {
            e = dataEntries[dataSetIndex][bin] = new BarEntry(x, 1);
            mData.addEntry(e, dataSetIndex);
        } else {
            float yNew = e.getY() + 1;
            e.setY(yNew);
            if (yNew > yMax) {
                yMax = e.getY();
            }

        }

        mData.notifyDataChanged();
        notifyDataSetChanged();
        getAxisLeft().setAxisMaxValue(yMax + 1);
        invalidate();
    }

    public void addDataSet(String lable) {

        nSets = mData.getDataSetCount() + 1;

        float barWidth = 0.7f / nSets;
        mData.setBarWidth(barWidth);
        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();
        BarDataSet dataSet = new BarDataSet(entries, lable);
        dataSet.setValueFormatter(new DefaultValueFormatter(0)); // 0 decimal digits
        dataSet.setColor(ColorTemplate.MATERIAL_COLORS[0]);
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        mData.addDataSet(dataSet);
    }

    private void initHistogram() {
        mData = new BarData();

        getAxisRight().setEnabled(false);

        Legend l = getLegend();
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_LEFT);

        XAxis xAxis = getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularityEnabled(true);
        xAxis.setGranularity(1.0f);

        YAxis yAxis = getAxisLeft();
        yAxis.setGranularityEnabled(true);
        yAxis.setGranularity(1.0f);


        xAxis.setAxisMinValue(min);
        xAxis.setAxisMaxValue(max);


        setDescription("Latency [ms]");

    }
}

package com.physmo.romexplorer.tilers;

public class Tiler256Col implements Tiler {

    int width, height;
    int[] metrics = {64,8,8}; // input size, width, height

    public Tiler256Col() {
        this(8,8);
    }

    public Tiler256Col(int width, int height) {
        this.width = width;
        this.height = height;
        metrics[0] = (width*height);
        metrics[1] = width;
        metrics[2] = height;
    }

    @Override
    public int[] getMetrics() {
        return metrics;
    }

    @Override
    public int[] getTile(int[] data, int index) {
        int[] output = new int[width*height];
        int outputPos = 0;

        // process rows.
        for (int pixel=0;pixel<metrics[0];pixel++) {
            int b1 = data[(index+pixel)];
            output[outputPos++]=b1;
        }

        return output;
    }
}

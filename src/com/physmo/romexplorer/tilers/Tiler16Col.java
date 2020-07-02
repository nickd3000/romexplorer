package com.physmo.romexplorer.tilers;

public class Tiler16Col implements Tiler {

    int width, height;
    int[] metrics = {32,8,8}; // input size, width, height

    public Tiler16Col() {
        this(8,8);
    }

    public Tiler16Col(int width, int height) {
        this.width = width;
        this.height = height;
        metrics[0] = (width*height)/2;
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

            int out=0;
            out = (b1)&0b0000_1111;

            output[outputPos++]=out;
            out = (b1>>4)&0b0000_1111;
            output[outputPos++]=out;

        }

        TilerUtils.scale16to256(output);

        return output;
    }
}

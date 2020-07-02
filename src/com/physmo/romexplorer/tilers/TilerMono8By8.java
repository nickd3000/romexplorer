package com.physmo.romexplorer.tilers;

public class TilerMono8By8 implements Tiler {
    int width, height;
    int[] metrics = {8,8,8}; // input size, width, height

    public TilerMono8By8() {
        this(8,8);
    }

    public TilerMono8By8(int width, int height) {
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

        int[] output = new int[8*8];
        int outputPos = 0;

        // process rows.
        for (int row=0;row<8;row++) {
            int b1 = data[(index+row)];

            int out=0;
            for (int col=0;col<8;col++) {
                out=(TilerUtils.getBit(b1,col));
                output[outputPos++]=out;
            }
        }

        TilerUtils.scale2to256(output);

        return output;
    }
}

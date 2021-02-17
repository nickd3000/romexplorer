package com.physmo.romexplorer.tilers;

public class Tiler256Col extends Tiler {


    //int[] metrics = {64, 8, 8}; // input size, width, height

    public Tiler256Col() {
        this(8, 8);
    }

    public Tiler256Col(int width, int height) {
        this.tileWidth = width;
        this.tileHeight = height;
        this.bytesPerTile = (width * height);

        tilerName = "Tiler256Col"+"_"+ tileWidth +"x"+ tileHeight;
        description = "";
    }


    public int[] getTile(int[] data, int index) {
        int[] output = new int[tileWidth * tileHeight];
        int outputPos = 0;

        for (int pixel = 0; pixel < bytesPerTile; pixel++) {
            if (index + pixel >= data.length) continue;
            int b1 = data[(index + pixel)];
            output[outputPos++] = b1;
        }

        return output;
    }

}

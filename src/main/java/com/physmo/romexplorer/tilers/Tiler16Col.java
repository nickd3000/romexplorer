package com.physmo.romexplorer.tilers;

public class Tiler16Col extends Tiler {

    boolean flipOrder = false;

    public Tiler16Col() {
        this(8, 8, false);
    }

    public Tiler16Col(int width, int height, boolean flipOrder) {
        this.tileWidth = width;
        this.tileHeight = height;
        this.bytesPerTile = (width * height) / 2;
        this.tilerName = "Tiler16Col"+"_"+width+"x"+height+(flipOrder?"_f":"");
        this.description = "";
        this.flipOrder=flipOrder;
    }

    @Override
    public int[] getTile(int[] data, int index) {

        int[] output = new int[tileWidth * tileHeight];
        int outputPos = 0;


        // process rows.
        for (int pixel = 0; pixel < bytesPerTile; pixel++) {
            if ((index + pixel) >= data.length) continue;
            int b1 = data[(index + pixel)];

            int out = 0;

            if (!flipOrder) {
                out = (b1) & 0b0000_1111;
                output[outputPos++] = out;

                out = (b1 >> 4) & 0b0000_1111;
                output[outputPos++] = out;
            } else {
                out = (b1 >> 4) & 0b0000_1111;
                output[outputPos++] = out;

                out = (b1) & 0b0000_1111;
                output[outputPos++] = out;
            }



        }

        TilerUtils.scale16to256(output);

        return output;
    }

}

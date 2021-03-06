package com.physmo.romexplorer.tilers;

public class TilerMono8By8 extends Tiler {

    public TilerMono8By8() {
        this(8, 8);
    }

    public TilerMono8By8(int width, int height) {
        this.tileWidth = width;
        this.tileHeight = height;
        this.bytesPerTile = height;
        this.tilerName = "TilerMono8By8";
    }



    @Override
    public int[] getTile(int[] data, int index) {

        int[] output = new int[tileWidth*tileHeight];
        int outputPos = 0;

        // process rows.
        for (int row = 0; row < bytesPerTile; row++) {
            if ((index + row)>=data.length) continue;
            int b1 = data[(index + row)];

            int out = 0;
            for (int col = 0; col < 8; col++) {
                out = (TilerUtils.getBit(b1, col));
                output[outputPos++] = out;
            }
        }

        TilerUtils.scale2to256(output);

        return output;
    }

}

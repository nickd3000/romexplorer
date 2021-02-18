package com.physmo.romexplorer.tilers;

public class TilerNES extends Tiler {

    public TilerNES() {
        this(8, 8);
    }

    public TilerNES(int width, int height) {
        this.tileWidth = width;
        this.tileHeight = height;
        this.bytesPerTile = (width*height)/4; //16;
        this.tilerName = "TilerNES";
    }

    @Override
    public int[] getTile(int[] data, int index) {
        int[] output = new int[8 * 8];
        int outputPos = 0;

        int dataSize = data.length;

        // process rows.
        for (int row = 0; row < 8; row++) {
            int i1 = (index + row);
            int i2 = (index + row*2);
            if (i2 >= dataSize) continue;

            int b1 = data[i1];
            int b2 = data[i2];
            int out = 0;
            for (int col = 0; col < 8; col++) {
                out = (TilerUtils.getBit(b1, col) * 1) + (TilerUtils.getBit(b2, col) * 2);
                output[outputPos++] = out;
            }
        }

        TilerUtils.scale4to256(output);

        return output;
    }

    @Override
    public String getName() {
        return tilerName;
    }

    @Override
    public String getDescription() {
        return null;
    }
}

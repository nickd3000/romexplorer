package com.physmo.romexplorer.tilers;

public class TilerAmiga16p4Col extends Tiler {

    int[] metrics = {4 * 8, 16, 16}; // input size, width, height

    public TilerAmiga16p4Col() {
        this(16, 16);
    }

    public TilerAmiga16p4Col(int width, int height) {
        this.tileWidth = width;
        this.tileHeight = height;
        this.bytesPerTile = (2*height);
        this.tilerName = "TilerAmiga16p4Col";
    }

    @Override
    public int[] getTile(int[] data, int index) {
        int[] output = new int[16 * 16];
        int outputPos = 0;

        // process rows.
        for (int row = 0; row < 16; row++) {
            if ((index + row * 4)> data.length-5) continue;

            int b1 = data[(index + (row * 4))];
            int b2 = data[(index + (row * 4)) + 1];
            int b3 = data[(index + (row * 4)) + 2];
            int b4 = data[(index + (row * 4)) + 3];


            for (int bit = 0; bit < 8; bit++) {
                int pix = 0;
                pix |= TilerUtils.getBit(b1, bit) << 1;
                pix |= TilerUtils.getBit(b3, bit);
                output[outputPos++] = pix;
            }
            for (int bit = 0; bit < 8; bit++) {
                int pix = 0;
                pix |= TilerUtils.getBit(b2, bit) << 1;
                pix |= TilerUtils.getBit(b4, bit);
                output[outputPos++] = pix;
            }

        }

        TilerUtils.scale16to256(output);

        return output;
    }

    @Override
    public String getName() {
        return this.tilerName;
    }

    @Override
    public String getDescription() {
        return null;
    }
}

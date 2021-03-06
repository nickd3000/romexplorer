package com.physmo.romexplorer.tilers;

public class TilerSega16Col extends Tiler {

    int[] metrics = {4 * 8, 8, 8}; // input size, width, height

    public TilerSega16Col() {
        this(8, 8);
    }

    public TilerSega16Col(int width, int height) {
        this.tileWidth = width;
        this.tileHeight = height;
        this.bytesPerTile = (width*height)/2;
        this.tilerName = "TilerSega16Col";
    }

    @Override
    public int[] getTile(int[] data, int index) {
        int[] output = new int[8 * 8];
        int outputPos = 0;

        // process rows.
        for (int row = 0; row < 8; row++) {
            if ((index + row * 4)> data.length-5) continue;

            int b1 = data[(index + row * 4)];
            int b2 = data[(index + row * 4) + 1];
            int b3 = data[(index + row * 4) + 2];
            int b4 = data[(index + row * 4) + 3];


            for (int bit = 0; bit < 8; bit++) {
                int pix = 0;
                pix |= TilerUtils.getBit(b4, bit) << 3;
                pix |= TilerUtils.getBit(b3, bit) << 2;
                pix |= TilerUtils.getBit(b2, bit) << 1;
                pix |= TilerUtils.getBit(b1, bit);
                output[outputPos++] = pix;
            }

        }

        TilerUtils.scale16to256(output);

        return output;
    }

    @Override
    public String getName() {
        return "TilerSega16Col";
    }

    @Override
    public String getDescription() {
        return null;
    }
}

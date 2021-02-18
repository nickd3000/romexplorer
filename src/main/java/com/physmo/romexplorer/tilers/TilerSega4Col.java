package com.physmo.romexplorer.tilers;

public class TilerSega4Col extends Tiler {

    int[] metrics = {2 * 8, 8, 8}; // input size, width, height

    public TilerSega4Col() {
        this(8, 8);
    }

    public TilerSega4Col(int width, int height) {
        this.tileWidth = width;
        this.tileHeight = height;
        this.bytesPerTile = (width*height)/4;;
        this.tilerName = "TilerSega4Col";
    }

    @Override
    public int[] getTile(int[] data, int index) {
        int[] output = new int[8 * 8];
        int outputPos = 0;

        // process rows.
        for (int row = 0; row < 8; row++) {
            if ((index + row * 2)> data.length-3) continue;
            int b1 = data[(index + row * 2)];
            int b2 = data[(index + row * 2) + 1];

            for (int bit = 0; bit < 8; bit++) {
                int pix = 0;
                pix |= TilerUtils.getBit(b2, bit) << 1;
                pix |= TilerUtils.getBit(b1, bit);
                output[outputPos++] = pix;
            }

        }

        TilerUtils.scale4to256(output);

        return output;
    }

    @Override
    public String getName() {
        return "TilerSega4Col";
    }

    @Override
    public String getDescription() {
        return null;
    }
}

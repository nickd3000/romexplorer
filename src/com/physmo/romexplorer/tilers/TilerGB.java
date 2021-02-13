package com.physmo.romexplorer.tilers;

public class TilerGB implements Tiler {

    int[] metrics = {16, 8, 8}; // input size, width, height

    @Override
    public int[] getMetrics() {
        return metrics;
    }

    @Override
    public int[] getTile(int[] data, int index) {
        int[] output = new int[8 * 8];
        int outputPos = 0;

        int dataSize = data.length;

        // process rows.
        for (int row = 0; row < 8; row++) {
            int i2 = (index + row * 2);
            if (i2 > dataSize - 2) continue;

            int b1 = data[i2];
            int b2 = data[i2 + 1];
            int out = 0;
            for (int col = 0; col < 8; col++) {
                out = (TilerUtils.getBit(b1, col) * 2) + (TilerUtils.getBit(b2, col) * 1);
                output[outputPos++] = out;
            }
        }

        TilerUtils.scale4to256(output);

        return output;
    }

    @Override
    public String getName() {
        return "TilerGB";
    }

    @Override
    public String getDescription() {
        return null;
    }
}

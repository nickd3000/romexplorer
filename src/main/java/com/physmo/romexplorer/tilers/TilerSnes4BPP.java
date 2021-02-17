package com.physmo.romexplorer.tilers;

public class TilerSnes4BPP extends Tiler {

    int[] metrics = {32, 8, 8}; // input size, width, height



    @Override
    public int[] getTile(int[] data, int index) {
        int[] output = new int[8 * 8];
        int outputPos = 0;

        // process rows.
        for (int row = 0; row < 8; row++) {
            if ((index + (row + 8) * 2) + 1 > data.length-4) continue;

            int b1 = data[(index + row * 2)];
            int b2 = data[(index + row * 2) + 1];
            int b3 = data[(index + (row + 8) * 2)];
            int b4 = data[(index + (row + 8) * 2) + 1];
            int out = 0;
            for (int col = 0; col < 8; col++) {
                out = (TilerUtils.getBit(b1, col) * 2) + (TilerUtils.getBit(b2, col) * 1);
                out = out << 2;
                out |= (TilerUtils.getBit(b3, col) * 2) + (TilerUtils.getBit(b4, col) * 1);
                output[outputPos++] = out;
            }
        }

        TilerUtils.scale16to256(output);

        return output;
    }

    @Override
    public String getName() {
        return "TilerSnes4BPP";
    }

    @Override
    public String getDescription() {
        return null;
    }
}

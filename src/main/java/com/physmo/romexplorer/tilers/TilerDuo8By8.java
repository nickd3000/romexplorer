package com.physmo.romexplorer.tilers;

public class TilerDuo8By8 extends Tiler {

    public TilerDuo8By8() {
        this(8, 8);
    }

    public TilerDuo8By8(int width, int height) {
        this.tileWidth = width;
        this.tileHeight = height;
        this.bytesPerTile = height;
        this.tilerName = "TilerDuo8By8";
    }



    @Override
    public int[] getTile(int[] data, int index) {

        int[] output = new int[tileWidth*tileHeight];
        int outputPos = 0;

        // process rows.
        for (int row = 0; row < bytesPerTile; row++) {
            if ((index + row)>=data.length) continue;
            int b1 = data[(index + row)];

            int a1,a2 = 0;
            for (int col = 0; col < 4; col++) {
                a1 = (TilerUtils.getBit(b1, col*2));
                a2 = (TilerUtils.getBit(b1, (col*2)+1));

                // Wide pixels
                output[outputPos++] = a1+(a2*2);
                output[outputPos++] = a1+(a2*2);
            }
        }

        TilerUtils.scale4to256(output);

        return output;
    }

}

package com.physmo.romexplorer;

// https://mrclick.zophar.net/TilEd/download/consolegfx.txt
/*
4. 2BPP SNES/Gameboy/GBC
  Colors Per Tile - 0-3
  Space Used - 2 bits per pixel.  16 bytes per 8x8 tile.

  Note: This is a tiled, planar bitmap format.
  Each pair represents one byte
  Format:

  [r0, bp1], [r0, bp2], [r1, bp1], [r1, bp2], [r2, bp1], [r2, bp2], [r3, bp1], [r3, bp2]
  [r4, bp1], [r4, bp2], [r5, bp1], [r5, bp2], [r6, bp1], [r6, bp2], [r7, bp1], [r7, bp2]

  Short Description:

  Bitplanes 1 and 2 are intertwined row by row.

 */
public class TileTranslators {

    public static int getBit(int val, int pos) {
        if (((val >> (7 - pos)) & 1) > 0) return 1;
        return 0;
    }

    // consumes 16 bytes, outputs 8*8 pixels
    public static int[] GBC(int[] data, int startPos) {
        int[] output = new int[8 * 8];
        int outputPos = 0;

        // process rows.
        for (int row = 0; row < 8; row++) {
            int b1 = data[(startPos + row * 2)];
            int b2 = data[(startPos + row * 2) + 1];
            int out = 0;
            for (int col = 0; col < 8; col++) {
                out = (getBit(b1, col) * 2) + (getBit(b2, col) * 1);
                output[outputPos++] = out;
            }
        }

        scale4to256(output);

        return output;
    }

    // consumes 8 bytes, outputs 8*8 pixels
    public static int[] monoChar(int[] data, int startPos) {
        int[] output = new int[8 * 8];
        int outputPos = 0;

        // process rows.
        for (int row = 0; row < 8; row++) {
            int b1 = data[(startPos + row * 2)];

            int out = 0;
            for (int col = 0; col < 8; col++) {
                out = (getBit(b1, col));
                output[outputPos++] = out;
            }
        }

        scale2to256(output);

        return output;
    }

    public static void scale2to256(int[] data) {
        int length = data.length;
        for (int i = 0; i < length; i++) {
            data[i] = data[i] * 128;
        }
    }

    public static void scale4to256(int[] data) {
        int length = data.length;
        for (int i = 0; i < length; i++) {
            data[i] = data[i] * 64;
        }
    }
}

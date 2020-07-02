package com.physmo.romexplorer.tilers;

public class TilerUtils {

    public static int getBit(int val, int pos) {
        if (((val>>(7-pos))&1)>0) return 1;
        return 0;
    }

    public static void scale2to256(int[] data) {
        int length = data.length;
        for (int i=0;i<length;i++) {
            data[i] = data[i]*128;
        }
    }
    public static void scale4to256(int[] data) {
        int length = data.length;
        for (int i=0;i<length;i++) {
            data[i] = data[i]*64;
        }
    }
    public static void scale16to256(int[] data) {
        int length = data.length;
        for (int i=0;i<length;i++) {
            data[i] = data[i]*16;
        }
    }
}

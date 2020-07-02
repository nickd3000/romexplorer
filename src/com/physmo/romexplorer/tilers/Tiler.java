package com.physmo.romexplorer.tilers;

public interface Tiler {

    static int SIZE = 0;
    static int WIDTH = 1;
    static int HEIGHT = 2;

    // input size, width, height
    int[] getMetrics();
    int[] getTile(int[] data, int index);
}

package com.physmo.romexplorer.tilers;

public interface Tiler {

    int SIZE = 0;
    int WIDTH = 1;
    int HEIGHT = 2;

    // input size, width, height
    int[] getMetrics();

    int[] getTile(int[] data, int index);

    String getName();

    String getDescription();
}

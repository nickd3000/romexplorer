package com.physmo.romexplorer;

public class BlockHistogram {
    public static int[] histogram(int[] data) {
        int[] hist = new int[8];
        for (int i=0;i<data.length;i++) {
            hist[i%8]+=data[i];
        }
        return hist;
    }
    public static int getMinOffset(int[] histogram) {
        int minVal=histogram[0];
        int minIndex=0;
        for (int i=0;i<8;i++) {
            if (histogram[i]<minVal) {
                minVal=histogram[i];
                minIndex=i;
            }
        }
        return minIndex;
    }
    public static int getMaxOffset(int[] histogram) {
        int maxVal=histogram[0];
        int maxIndex=0;
        for (int i=0;i<8;i++) {
            if (histogram[i]>maxVal) {
                maxVal=histogram[i];
                maxIndex=i;
            }
        }
        return maxIndex;
    }
}

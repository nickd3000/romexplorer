package com.physmo.colormapper;


import java.awt.Color;

public class GenerateColorMap {

    public static void main(String[] args) {
        testGenerate();
    }

    public static Color[] generateMapFromPreset(Color[] guide) {
        Color[] newColors = new Color[0xff+1];
        int numColors  = guide.length;

        for (int i=0;i<numColors-1;i++) {
            int from = getDestIndex(numColors, i);
            int to = getDestIndex(numColors, i+1);
            System.out.println("from:"+from+" to:"+to);
            spread(newColors, guide[i],from,guide[i+1], to);
        }

        Color lastCol = new Color(0,0,0);

        for (int i=0;i<newColors.length;i++) {
            if (newColors[i]!=null) lastCol = newColors[i];
            else newColors[i] = lastCol;
        }

        //spread(newColors, guide[0],0,guide[3], 0xff);

        return newColors;
    }

    // Return index in destination palette from map index.
    public static int getDestIndex(int numColors, int mapIndex) {
        double span = (double)0xff/(double)(numColors-1);
        double pos = (double)mapIndex*span;
        int index = (int)pos;
        if (index>0xff) index=0xff;
        if (index<0) index = 0;
        return index;
    }

    public static void spread(Color[] colors, Color col1, int pos1, Color col2, int pos2) {
        int length = pos2-pos1;

        double dr = (col2.getRed()-col1.getRed())/(double)(length);
        double dg = (col2.getGreen()-col1.getGreen())/(double)(length);
        double db = (col2.getBlue()-col1.getBlue())/(double)(length);

        for (int i=0;i<length;i++) {
            double ii = (double)i;
            int nr = col1.getRed()+(int)(dr*ii);
            int ng = col1.getGreen()+(int)(dg*ii);
            int nb = col1.getBlue()+(int)(db*ii);
            Color nc = new Color(clamp(nr), clamp(ng), clamp(nb));
            if (nc==null) System.out.println("Null color in spread");
            colors[pos1+i] = nc;
        }

    }

    public static Color[] testGenerate() {
        Color[] guide = new Color[] {
                new Color(132, 136, 63),
                new Color(86, 91, 59),
                new Color(57, 59, 26),
                new Color(44, 52, 39)

                 };

        return generateMapFromPreset(guide);
    }

    public static int clamp(int v) {
        if (v<0) return 0;
        if (v>0xff) return 0xff;
        return v;
    }
}

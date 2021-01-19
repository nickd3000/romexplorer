package com.physmo.romexplorer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SummaryStrip {
    BufferedImage summaryStrip = new BufferedImage(20, 400, BufferedImage.TYPE_INT_ARGB);

    static int numInputValues = 32;
    public boolean summaryStripRendered = false;

    public BufferedImage getSummaryStrip(int[] data) {
        if (summaryStripRendered) return summaryStrip;

        int stripHeight = summaryStrip.getHeight();
        int numRowsInFile = (int)((float)data.length/(float)(numInputValues));
        //numInputValues
        Graphics graphics = summaryStrip.getGraphics();
        for (int y=0;y<stripHeight;y++) {
            float normalisedPositionInFile = ((float)y/(float)stripHeight);
            int positionInFile = 	(int)(normalisedPositionInFile*(float)numRowsInFile);
            Color stripColour = getStripColor(data, positionInFile*numInputValues);
            graphics.setColor(stripColour);
            graphics.drawLine(0,y,10,y);

            graphics.setColor(getStripEntropy(data, positionInFile*numInputValues));
            graphics.drawLine(10,y,20,y);
        }

        summaryStripRendered=true;
        return summaryStrip;

    }

    public Color getStripColor(int[] data, int position) {
        int r=0,g=0,b=0;

        if (position+numInputValues+10>=data.length) return Color.BLACK;

        for (int i=0;i<numInputValues;i++) {
            int value = data[position+i]&0xff;
            Color col = getColForCharBrighter(value);
            r+=col.getRed();
            g+=col.getGreen();
            b+=col.getBlue();
        }

        r/=numInputValues;
        g/=numInputValues;
        b/=numInputValues;

        return new Color(r,g,b);
    }

    public Color getStripEntropy(int[] data, int position) {
        int max = 0;
        int prevValue = 0;
        int entropy=0;
        int rowAverage = 0;
        for (int i=0;i<numInputValues;i++) {
            rowAverage+=data[position+i]&0xff;
        }
        rowAverage/=numInputValues;

        for (int i=0;i<numInputValues;i++) {
            int val=data[position+i]&0xff;
            prevValue = val;
            entropy+=Math.abs(rowAverage-val);
            entropy+=Math.abs(prevValue-val);
            max+=0xff+0xff;
        }

        entropy/=(max/0xff);
        entropy*=3;
        if (entropy>0xff) entropy=0xff;

        return new Color(entropy,entropy,entropy);
    }

    public Color getColForCharBrighter(int val) {
        if (val>=65 && val<=90) return Color.GREEN;
        if (val>=97 && val<=122) return Color.GREEN;
        if (val>=48 && val<=57) return Color.BLUE;
        return Color.DARK_GRAY;
    }
}

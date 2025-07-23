package com.physmo.romexplorer.rowdrawer;

import com.physmo.romexplorer.Application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.RenderingHints;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class RowDrawerText8Bit implements RowDrawer {

    BufferedImage bufferedImage = null;

    int charSize = 16;
    boolean summaryStripRendered = false;
    int bytesPerRow = 16 * 2;

    Color colBg = new Color(31, 31, 31);
    Color colFg = new Color(167, 167, 167);
    Font font = new Font(Font.MONOSPACED, Font.BOLD, charSize - 2);

    Color colBgChar = new Color(0, 52, 0);
    Color colBgNumber = new Color(3, 3, 82);
    Application application;

    public RowDrawerText8Bit(Application application) {
        this.application = application;
        //bufferedImage = new BufferedImage(getOutputRowWidth(), getOutputRowHeight(),
        //        BufferedImage.TYPE_INT_RGB);

        bufferedImage = createImageBufferCompatible(getOutputRowWidth(), getOutputRowHeight());
    }

        public  BufferedImage createImageBufferCompatible(int width, int height) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration config = device.getDefaultConfiguration();
        return config.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
    }

    @Override
    public int getBytesPerRow() {
        return bytesPerRow;
    }

    @Override
    public int getBytesPerStrip() {
        return bytesPerRow;
    }

    @Override
    public int getOutputRowHeight() {
        return charSize;
    }

    @Override
    public int getOutputRowWidth() {

        return charSize * bytesPerRow;
    }

    @Override
    public int getNumRowsForFile() {
        int data[] = application.getDataFile().getData();
        return data.length/bytesPerRow;
    }

    @Override
    public int getNumRowsForRefactoredFile() {
        int data[] = application.getDataFile().getData();
        return data.length/bytesPerRow;
    }

    @Override
    public Dimension getDocumentSize() {
        int data[] = application.getDataFile().getData();
        int docWidth = charSize * bytesPerRow;
        int docHeight = (data.length/bytesPerRow)*charSize;

        return new Dimension(docWidth,docHeight);
    }

    @Override
    public BufferedImage renderStrip(int row) {
        int data[] = application.getDataFile().getData();
        int scaledRow = row * bytesPerRow;

        Graphics g = bufferedImage.getGraphics();
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.setFont(font);
        g.setColor(colBg);
        g.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g.setColor(colFg);
        int value = 0;
        for (int i = 0; i < bytesPerRow; i++) {
            if (scaledRow + i >= data.length-1) continue;
            value = data[scaledRow + i] & 0xff;
            //g.setColor(new Color(value,value,value));
            g.setColor(getColForChar(value));
            g.fillRect(i * charSize, 0, (i + 1) * charSize, charSize);
            g.setColor(colFg);
            g.drawString("" + calculateChar(value), i * charSize + 2, charSize - 2);
        }

        g.dispose();
        return bufferedImage;
    }


    public char calculateChar(int i) {
        char c = (char) (i & 0xff);
        return c;
    }

    // Set colour based on character type, eg text, number, non printing
    public Color getColForChar(int val) {
        if (val >= 65 && val <= 90) return colBgChar;
        if (val >= 97 && val <= 122) return colBgChar;
        if (val >= 48 && val <= 57) return colBgNumber;
        return colBg;
    }


}

package com.physmo.romexplorer.rowdrawer;

import com.physmo.romexplorer.Application;
import com.physmo.romexplorer.tilers.Tiler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RowDrawerPixel8Bit implements RowDrawer {

    BufferedImage bufferedImage = null;
    int pixelSize = 4;
    int numInputValues = 64;
    int numOutputValues = 64;
    Color colBg = new Color(36, 36, 36);

    int pixelsPerByte = 1;
    Color[] palette = null;
    int[] copy = null;

    String tilerName = "";
    Application application;
    int[] subPixelBuffer = new int[8];

    public RowDrawerPixel8Bit(Application application) {
        this.application = application;

        createImageBuffer();

        initPalette();
    }

    public void createImageBuffer() {
        bufferedImage = new BufferedImage(getOutputRowWidth(), getOutputRowHeight(),
                BufferedImage.TYPE_INT_RGB);
    }

    public void initPalette() {
        palette = new Color[0xff + 1];
        for (int i = 0; i <= 0xff; i++) {
            palette[i] = new Color(i, i, i);
        }
    }

    @Override
    public int getBytesPerRow() {
        return numInputValues;
    }

    @Override
    public int getOutputRowHeight() {
        return pixelSize;
    }

    @Override
    public int getOutputRowWidth() {

        return pixelSize * numOutputValues * pixelsPerByte;
    }

    public void maketestData(int[] data, int blockWidth, int blockHeight) {
        for (int i = 0; i < data.length; i++) {
            data[i] = ((i / (blockHeight * blockWidth)) * 2) & 0xff;
        }
    }

    public void refactorData(int[] data, Tiler tiler) {
        createImageBuffer();

        copy = new int[data.length];
        int[] tileData;
        int[] tilerMetrics = tiler.getMetrics();

        int blockWidth = tilerMetrics[Tiler.WIDTH];
        int blockHeight = tilerMetrics[Tiler.WIDTH];
        int tilerSize = tilerMetrics[Tiler.SIZE];

        int outputStride = numInputValues;
        int blocksPerRow = outputStride / blockWidth;

        //int offset = BlockHistogram.getMaxOffset(BlockHistogram.histogram(data));

        int scanningHead = 0;
        boolean keepGoing = true;
        int gx = 0, gy = 0; // grid x,y
        while (keepGoing) {
            // Grab tile
            //tileData = TileTranslators.monoChar(data, scanningHead);
            tileData = tiler.getTile(data, scanningHead);
            scanningHead += tilerSize;

            if (scanningHead >= data.length) keepGoing = false;

            drawTile(copy, tileData, outputStride, gx * blockWidth, gy * blockHeight, blockWidth, blockHeight);
            gx++;
            if (gx >= outputStride / 8) {
                gx = 0;
                gy++;
            }
        }


    }

    public void drawTile(int[] dest, int[] tileData, int destStride, int destx, int desty, int tileWidth, int tileHeight) {
        int i = 0;
        int c = 0;
        int xx, yy;
        for (int y = 0; y < tileHeight; y++) {
            for (int x = 0; x < tileWidth; x++) {
                c = tileData[i++];
                xx = destx + x;
                yy = desty + y;
                if (xx + (yy * destStride) < dest.length) {
                    dest[xx + (yy * destStride)] = c;
                }
            }
        }
    }

    // q: will this trigger a refactor - can we call it after a load?
    public void clearCopy() {
        copy = null;
    }

    // refactors if the tiler changed
    public void handleRefactoring(int[] data) {
        // hack
        if (copy == null || !tilerName.equals(application.getTilerName())) {
            //refactorData(data, new TilerSnes4BPP());
            Tiler tiler = application.getTilerRepo().getByName(application.getTilerName());
            if (tiler == null) return;

            refactorData(data, tiler); //new Tiler256Col());
            //refactorData(data, new TilerGB());

            tilerName = application.getTilerName();
        }
    }

    @Override
    public BufferedImage drawRow(int[] data, int offset) {

        //handleRefactoring(data);
        if (copy == null) {
            return null;
        }
        Graphics g = bufferedImage.getGraphics();

        g.setColor(colBg);
        g.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        int value = 0;
        int count = 0;
        int component = 50;
        for (int i = 0; i < numInputValues; i++) {
            //value = data[blockAdjuster(offset, i, 4, 8)]&0xff;
            int index = blockAdjuster(offset, i, 4, 8);
            if (index >= copy.length) continue;

            value = copy[index] & 0xff;
            //value = data[i+offset]&0xff;
            calculateSubPixels(value);
            for (int j = 0; j < pixelsPerByte; j++) {
                component = subPixelBuffer[j];
                g.setColor(palette[component & 0xff]);
                g.fillRect(count * pixelSize, 0, (count + 1) * pixelSize, pixelSize);
                count++;
            }


        }

        return bufferedImage;
    }

    public int blockAdjuster(int startIndex, int index, int blockWidth, int blockHeight) {
        return startIndex + index;

//		int rowVolume = (numInputValues / pixelsPerByte) * blockHeight;
//		int row = startIndex / rowVolume;

//		int delta = index-startIndex;
//		int blockSize = 8;
//
//		int blockNum = delta/blockSize;
//		int blockMod = delta%blockSize;
//		return index+(blockNum*(blockSize*blockSize));
    }

    public void calculateSubPixels(int val) {
        if (pixelsPerByte == 1) {
            subPixelBuffer[0] = val;
        }
        if (pixelsPerByte == 2) {
            subPixelBuffer[0] = (val & 0b11110000) >> 4;
            subPixelBuffer[1] = val & 0b00001111;
            subPixelBuffer[0] = subPixelBuffer[0] * 16;
            subPixelBuffer[1] = subPixelBuffer[1] * 16;
        }
        if (pixelsPerByte == 4) {
            subPixelBuffer[1] = (val & 0b11000000) >> 6;
            subPixelBuffer[2] = (val & 0b00110000) >> 4;
            subPixelBuffer[3] = (val & 0b00001100) >> 2;
            subPixelBuffer[4] = (val & 0b00000011);
            subPixelBuffer[0] = subPixelBuffer[0] * 64;
            subPixelBuffer[1] = subPixelBuffer[1] * 64;
            subPixelBuffer[2] = subPixelBuffer[2] * 64;
            subPixelBuffer[3] = subPixelBuffer[3] * 64;
        }

    }

}

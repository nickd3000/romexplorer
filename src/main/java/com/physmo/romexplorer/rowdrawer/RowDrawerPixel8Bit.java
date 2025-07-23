package com.physmo.romexplorer.rowdrawer;

import com.physmo.romexplorer.Application;
import com.physmo.romexplorer.tilers.Tiler;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Transparency;
import java.awt.image.BufferedImage;

public class RowDrawerPixel8Bit implements RowDrawer {

    BufferedImage bufferedImage = null;
    int scale = 4;
    int outputPixelWidth = 320/4; // Raw unscaled pixels
    //int numOutputValues = 64;
    Color colBg = new Color(36, 36, 36);

    Color[] palette = null;
    int[] refactoredData = null;


    Application application;

    int numRows = 0;
    int bytesPerRow = 0;
    int bytesPerStrip = 0;

    public RowDrawerPixel8Bit(Application application) {
        this.application = application;

        //createImageBuffer();
        bufferedImage = createImageBufferCompatible(getOutputRowWidth(), getOutputRowHeight());

        //initPalette();
        //this.setPalette(GenerateColorMap.testGenerate());

        // TODO: Force this to pick a specific palette for now.
        this.setPalette(application.getColorMapRepo().getList().get(3).map);
    }


    public void initPalette() {
        palette = new Color[0xff + 1];
        for (int i = 0; i <= 0xff; i++) {
            palette[i] = new Color(i, i, i);
        }
    }

    public void setPalette(Color [] newPalette) {
        this.palette = newPalette;
    }

    @Override
    public int getBytesPerRow() {

        return bytesPerRow;
    }

    @Override
    public int getBytesPerStrip() {
        return bytesPerStrip;
    }

    @Override
    public int getOutputRowHeight() {
        // This should be one scaled pixel high?
        return scale;
    }

    @Override
    public int getOutputRowWidth() {
        // scaled output width in pixels?
        return scale * outputPixelWidth;
    }

    @Override
    public int getNumRowsForFile() {
        //int data[] = application.getDataFile().getData();
        int dataLength = application.getDataFile().getData().length;

        Tiler tiler = application.getCurrentTiler();
        int tilesPerRow = outputPixelWidth/tiler.getTileWidth();
        bytesPerRow = tilesPerRow * tiler.getBytesPerTile();
        bytesPerStrip = tilesPerRow * tiler.getBytesPerTile() / tiler.getTileHeight();

        System.out.println("dataLength: "+dataLength);
        System.out.println("tilesPerRow: "+tilesPerRow);
        System.out.println("bytesPerRow: "+bytesPerRow);
        System.out.println("bytesPerStrip: "+bytesPerStrip);

        //return dataLength / bytesPerRow;

        return dataLength/bytesPerRow;

    }

    public BufferedImage createImageBufferCompatible(int width, int height) {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration config = device.getDefaultConfiguration();
        BufferedImage compatibleImage = config.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        System.out.println("image color model: "+compatibleImage.getColorModel());
        return compatibleImage;
    }

    @Override
    public int getNumRowsForRefactoredFile() {
        int dataLength = refactoredData.length;
        Tiler tiler = application.getCurrentTiler();

//        System.out.println("dataLength: "+dataLength);
//        System.out.println("tilesPerRow: "+tilesPerRow);
//        System.out.println("bytesPerRow: "+bytesPerRow);
//        System.out.println("bytesPerStrip: "+bytesPerStrip);

        return dataLength/(tiler.getTileWidth()*tiler.getTileHeight());
    }

    @Override
    public Dimension getDocumentSize() {

        Tiler tiler = application.getCurrentTiler();

        if (tiler==null) return new Dimension(100,100);

        numRows = getNumRowsForRefactoredFile();
        int documentHeight = numRows * tiler.getTileHeight();// * scale;
        int documentWidth = getOutputRowWidth();

        return new Dimension(documentWidth, documentHeight);
    }

    public void maketestData(int[] data, int blockWidth, int blockHeight) {
        for (int i = 0; i < data.length; i++) {
            data[i] = ((i / (blockHeight * blockWidth)) * 2) & 0xff;
        }
    }

    // Create a representation of the data in tiled format.
    public void refactorData(int[] data, Tiler tiler) {

        System.out.println("refactorData Tiler tileWidth:"+tiler.getTileWidth()+" tileHeight:"+tiler.getTileHeight()+" bytesPerTile"+tiler.getBytesPerTile());

        //bufferedImage = createImageBufferCompatible(getOutputRowWidth(), getOutputRowHeight());

        int[] tileData;

        int tileWidth = tiler.getTileWidth();
        int tileHeight = tiler.getTileHeight();
        int bytesPerTile = tiler.getBytesPerTile();

        int dataSize = (data.length)*tileWidth*tileHeight;

        if (bytesPerTile>0) dataSize/=bytesPerTile;
        else dataSize/=2;

            refactoredData = new int[dataSize];


        int scanningHead = 0;
        boolean keepGoing = true;
        int gx = 0, gy = 0; // grid x,y
        while (keepGoing) {

            // TODO: can we check here if we would overflow?
            tileData = tiler.getTile(data, scanningHead);
            scanningHead += bytesPerTile;

            if (scanningHead >= data.length) keepGoing = false;

            //System.out.println("drawTile "+gx+", "+gy);

            drawRefactoredTile(refactoredData, tileData, outputPixelWidth,
                    gx * tileWidth, gy * tileHeight,
                    tileWidth, tileHeight);
            gx++;
            if (gx >= outputPixelWidth / tileWidth) {
                gx = 0;
                gy++;
            }
        }

        numRows = gy;

        // Calculate bytes per row - Should be number of bytes required to make up each row of tiles.
        int numberOfTilesPerRow = outputPixelWidth / tileWidth;
        bytesPerRow = numberOfTilesPerRow*bytesPerTile;
    }

    public void drawRefactoredTile(int[] dest, int[] tileData, int destStride, int destx, int desty, int tileWidth, int tileHeight) {
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

    @Override
    public BufferedImage renderStrip(int row) {
        // this drows one row of scaled pixels to the buffer

        if (refactoredData == null) {
            return null;
        }

        int scaledRow = row * outputPixelWidth;

        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();

        g.setColor(colBg);
        g.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        int value = 0;
        int count = 0;
        for (int i = 0; i < outputPixelWidth; i++) {
            int index = scaledRow+i;
            if (index >= refactoredData.length) continue;

            value = refactoredData[index] & 0xff;
            g.setColor(palette[value & 0xff]);
            g.fillRect(count * scale, 0, scale, scale);
            count++;
        }
g.dispose();
        return bufferedImage;
    }

}

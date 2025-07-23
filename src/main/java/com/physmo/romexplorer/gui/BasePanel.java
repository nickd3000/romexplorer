package com.physmo.romexplorer.gui;

import com.physmo.romexplorer.DataFile;
import com.physmo.romexplorer.rowdrawer.RowDrawer;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class BasePanel extends JPanel {
    private static final long serialVersionUID = -5541242543785611502L;
    RowDrawer rowDrawer = null;
    int dirtyRowStart;
    int dirtyRowEnd;
    Color debugColor = new Color(255, 0, 128);

    private DataFile df = null;

    public BasePanel(DataFile df, RowDrawer rowDrawer) {
        this.df = df;
        this.rowDrawer = rowDrawer;

        refreshPanelDimensions();
    }

    public RowDrawer getRowDrawer() {
        return rowDrawer;
    }

    public void refreshPanelDimensions() {
        int bytesPerRow = this.rowDrawer.getBytesPerRow();
        if (bytesPerRow == 0) bytesPerRow = 8;
        int numRows = df.size() / bytesPerRow;


        Dimension preferredSize = new Dimension(
                this.rowDrawer.getOutputRowWidth(),
                this.rowDrawer.getOutputRowHeight() * numRows); //this.rowDrawer.getNumRows(df.getData()));

        //this.setPreferredSize(preferredSize);
        Dimension documentSize = rowDrawer.getDocumentSize();

        this.setPreferredSize(documentSize);

        System.out.println("documentSize: " + documentSize.width + ", " + documentSize.height);
    }

    static boolean paintLocked = false;
    public void paintComponent(Graphics g) {

        if (paintLocked) return;
        paintLocked=true;

        Color bg = getBackground();
        Rectangle r = g.getClipBounds();

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(debugColor);

        calculateDirtyRows(r);
        int count = 0;

        for (int i = dirtyRowStart; i < dirtyRowEnd; i++) {
            //BufferedImage bi = rowDrawer.renderStrip( i * rowDrawer.getBytesPerStrip());
            BufferedImage bi = rowDrawer.renderStrip(i); // should be number of translated pixels per row
            g2.drawImage(bi, 0, i * rowDrawer.getOutputRowHeight(), null);
            count++;
        }

        g2.dispose();

        System.out.println("paintComponent " + count + " rows");

        paintLocked=false;
    }

    public void calculateDirtyRows(Rectangle r) {
        if (r == null) System.out.println("Feck");
        if (rowDrawer == null) System.out.println("Feck rowDrawer");

        int rowHeight = rowDrawer.getOutputRowHeight();
        dirtyRowStart = (r.y / rowHeight);
        dirtyRowEnd = ((r.y + r.height) / rowHeight) + 1;
    }

}
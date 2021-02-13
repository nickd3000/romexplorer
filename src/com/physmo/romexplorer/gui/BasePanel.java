package com.physmo.romexplorer.gui;

import com.physmo.romexplorer.DataFile;
import com.physmo.romexplorer.rowdrawer.RowDrawer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BasePanel extends JPanel {
    private static final long serialVersionUID = -5541242543785611502L;
    RowDrawer rowDrawer = null;
    int dirtyRowStart;
    int dirtyRowEnd;
    Color debugColor = new Color(255, 0, 128);

    private DataFile df = null;

    public BasePanel(DataFile df, RowDrawer textRowDrawer) {
        this.df = df;
        this.rowDrawer = textRowDrawer;

        int numRows = df.size() / textRowDrawer.getBytesPerRow();

        refreshPanelDimensions();
    }

    public RowDrawer getRowDrawer() {
        return rowDrawer;
    }

    public void refreshPanelDimensions() {
        int numRows = df.size() / this.rowDrawer.getBytesPerRow();

        Dimension preferredSize = new Dimension(
                this.rowDrawer.getOutputRowWidth(),
                this.rowDrawer.getOutputRowHeight() * numRows);

        this.setPreferredSize(preferredSize);

        System.out.println("Preferred height: " + this.rowDrawer.getOutputRowHeight() * numRows);
    }

    public void paintComponent(Graphics g) {

        Color bg = getBackground();
        Rectangle r = g.getClipBounds();

        g.setColor(debugColor);

        calculateDirtyRows(r);

        for (int i = dirtyRowStart; i < dirtyRowEnd; i++) {
            BufferedImage bi = rowDrawer.drawRow(df.getData(), i * rowDrawer.getBytesPerRow());
            g.drawImage(bi, 0, i * rowDrawer.getOutputRowHeight(), null);
        }

        // Draw summary strip.
//		BufferedImage summaryStrip = rowDrawer.getSummaryStrip(df.getData());
//
//        if (summaryStrip!=null) {
//        	g.drawImage(summaryStrip,
//					this.getWidth()-40,0,
//					40,this.getVisibleRect().height, null);
//        }
    }

    public void calculateDirtyRows(Rectangle r) {
        if (r == null) System.out.println("Feck");
        if (rowDrawer == null) System.out.println("Feck rowDrawer");

        int rowHeight = rowDrawer.getOutputRowHeight();
        dirtyRowStart = (r.y / rowHeight);
        dirtyRowEnd = ((r.y + r.height) / rowHeight) + 1;
    }

}
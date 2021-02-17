package com.physmo.romexplorer.rowdrawer;

import com.physmo.romexplorer.DataFile;
import com.physmo.romexplorer.tilers.Tiler;

import java.awt.*;
import java.awt.image.BufferedImage;

public interface RowDrawer {
    int getBytesPerRow();
    int getBytesPerStrip();

    int getOutputRowHeight();

    int getOutputRowWidth();

    int getNumRowsForFile();
    int getNumRowsForRefactoredFile();

    Dimension getDocumentSize();

    BufferedImage renderStrip(int row);
}

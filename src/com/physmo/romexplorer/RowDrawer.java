package com.physmo.romexplorer;

import java.awt.image.BufferedImage;

public interface RowDrawer {

	int getBytesPerRow();
	int getRowHeight();
	int getRowWidth();
	
	BufferedImage drawRow(int [] data, int offset); 
}

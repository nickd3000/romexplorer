package com.physmo.romexplorer;

import java.awt.image.BufferedImage;

public interface RowDrawer {

	int getBytesPerRow();
	int getOutputRowHeight();
	int getOutputRowWidth();
	
	BufferedImage drawRow(int [] data, int offset); 
}

package com.physmo.romexplorer;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RowDrawerText8Bit implements RowDrawer {
	
	BufferedImage bufferedImage = null;
	int charSize = 16;
	
	int numInputValues = 16*2;
	int numOutputValues = 16*2;
	
	Color colBg = new Color(31, 31, 31);
	Color colFg = new Color(167, 167, 167);
	Font  font  = new Font(Font.MONOSPACED, Font.BOLD,  charSize-2);

	Color colBgChar = new Color(0, 52, 0);
	Color colBgNumber = new Color(3, 3, 82);
	
	public RowDrawerText8Bit() {
		bufferedImage = new BufferedImage(getOutputRowWidth(), getOutputRowHeight(),
			     BufferedImage.TYPE_INT_RGB);
	}
	
	@Override
	public int getBytesPerRow() {
		return numInputValues;
	}

	@Override
	public int getOutputRowHeight() {
		return charSize;
	}

	@Override
	public int getOutputRowWidth() {
		
		return charSize*numOutputValues;
	}

	@Override
	public BufferedImage drawRow(int[] data, int offset) {
		Graphics g = bufferedImage.getGraphics();
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g.setFont(font);
		g.setColor(colBg);
		g.clearRect(0,0,bufferedImage.getWidth(),bufferedImage.getHeight());
		g.setColor(colFg);
		int value = 0;
		for (int i=0;i<numInputValues;i++) {
			value = data[offset+i]&0xff;
			//g.setColor(new Color(value,value,value));
			setBGFromChar(g,value);
			g.fillRect(i*charSize, 0, (i+1)*charSize, charSize);
			g.setColor(colFg);
			g.drawString(""+calculateChar(value), i*charSize+2,charSize-2);
		}
		
		return bufferedImage;
	}

	public char calculateChar(int i) {
		char c = (char)(i&0xff);
		return c;
	}

	// Set colour based on character type, eg text, number, non printing
	public void setBGFromChar(Graphics g, int val) {
		Color col = colBg;
		if (val>=65 && val<=90) col = colBgChar;
		if (val>=97 && val<=122) col = colBgChar;
		if (val>=48 && val<=57) col = colBgNumber;
		g.setColor(col);
	}
	
}

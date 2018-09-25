package com.physmo.romexplorer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RowDrawerText8Bit implements RowDrawer {
	
	BufferedImage bufferedImage = null;
	int charSize = 16;
	
	int numInputValues = 16;
	int numOutputValues = 16;
	
	Color colBg = new Color(0,0,0);
	Color colFg = new Color(0xff,0xff,0xff);
	Font  font  = new Font(Font.MONOSPACED, Font.BOLD,  charSize-2);

	Color colBgChar = new Color(0x00,0x60,0x00);
	Color colBgNumber = new Color(0x00,0x00,0x60);
	
	public RowDrawerText8Bit() {
		bufferedImage = new BufferedImage(getRowWidth(), getRowHeight(), 
			     BufferedImage.TYPE_INT_RGB);
	}
	
	@Override
	public int getBytesPerRow() {
		return numInputValues;
	}

	@Override
	public int getRowHeight() {
		return charSize;
	}

	@Override
	public int getRowWidth() {
		
		return charSize*numOutputValues;
	}

	@Override
	public BufferedImage drawRow(int[] data, int offset) {
		Graphics g = bufferedImage.getGraphics();
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
	
	public void setBGFromChar(Graphics g, int val) {
		Color col = colBg;
		if (val>=65 && val<=90) col = colBgChar;
		if (val>=97 && val<=122) col = colBgChar;
		if (val>=48 && val<=57) col = colBgNumber;
		g.setColor(col);
	}
	
}

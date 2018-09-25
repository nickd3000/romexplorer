package com.physmo.romexplorer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RowDrawerPixel8Bit implements RowDrawer {
	
	BufferedImage bufferedImage = null;
	int pixelSize = 4;
	int numInputValues = 64;
	int numOutputValues =64;
	Color colBg = new Color(0,0,0);
	
	int pixelsPerByte = 2;
	
	public RowDrawerPixel8Bit() {
		bufferedImage = new BufferedImage(getRowWidth(), getRowHeight(), 
			     BufferedImage.TYPE_INT_RGB);
	}
	
	@Override
	public int getBytesPerRow() {
		return numInputValues;
	}

	@Override
	public int getRowHeight() {
		return pixelSize;
	}

	@Override
	public int getRowWidth() {
		
		return pixelSize*numOutputValues*pixelsPerByte;
	}

	@Override
	public BufferedImage drawRow(int[] data, int offset) {
		Graphics g = bufferedImage.getGraphics();
		
		g.setColor(colBg);
		g.clearRect(0,0,bufferedImage.getWidth(),bufferedImage.getHeight());
		
		int value = 0;
		int count = 0;
		int component = 50;
		for (int i=0;i<numInputValues;i++) {
			value = data[blockAdjuster(offset, offset+i)]&0xff;
			//value = data[i+offset]&0xff;
			calculateSubPixels(value);
			for (int j=0;j<pixelsPerByte;j++) {
				component=subPixelBuffer[j];
				g.setColor(new Color(component,component,component));
				g.fillRect(count*pixelSize, 0, (count+1)*pixelSize, pixelSize);
				count++;
			}
			
			
		}
		
		return bufferedImage;
	}

	public int blockAdjuster(int startIndex, int index) {
		int delta = index-startIndex;
		int blockSize = 8;
		
		int blockNum = delta/blockSize;
		int blockMod = delta%blockSize;
		return index+(blockNum*(blockSize*blockSize));
	}
	
	int [] subPixelBuffer = new int[8];
	
	public void calculateSubPixels(int val) {
		if (pixelsPerByte==1) {
			subPixelBuffer[0] = val;
		}
		if (pixelsPerByte==2) {
			subPixelBuffer[0] = (val&0b11110000)>>4;
			subPixelBuffer[1] = val&0b00001111;
			subPixelBuffer[0] = subPixelBuffer[0] * 16;
			subPixelBuffer[1] = subPixelBuffer[1] * 16;
		}
		if (pixelsPerByte==4) {
			subPixelBuffer[1] = (val&0b11000000)>>6;
			subPixelBuffer[2] = (val&0b00110000)>>4;
			subPixelBuffer[3] = (val&0b00001100)>>2;
			subPixelBuffer[4] = (val&0b00000011);
			subPixelBuffer[0] = subPixelBuffer[0] * 64;
			subPixelBuffer[1] = subPixelBuffer[1] * 64;
			subPixelBuffer[2] = subPixelBuffer[2] * 64;
			subPixelBuffer[3] = subPixelBuffer[3] * 64;
			
		}
	}
	
}

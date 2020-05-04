package com.physmo.romexplorer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class RowDrawerPixel8Bit implements RowDrawer {
	
	BufferedImage bufferedImage = null;
	int pixelSize = 4;
	int numInputValues = 64;
	int numOutputValues =64;
	Color colBg = new Color(36, 36, 36);
	
	int pixelsPerByte = 2;
	Color [] palette = null;
	int[] copy = null;



	public RowDrawerPixel8Bit() {
		bufferedImage = new BufferedImage(getOutputRowWidth(), getOutputRowHeight(),
			     BufferedImage.TYPE_INT_RGB);

		initPalette();
	}

	public void initPalette() {
		palette = new Color[0xff+1];
		for (int i=0;i<=0xff;i++) {
			palette[i] = new Color(i,i,i);
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
		
		return pixelSize*numOutputValues*pixelsPerByte;
	}

	public void maketestData(int[] data, int blockWidth, int blockHeight) {
		for (int i=0;i<data.length;i++) {
			data[i] = ((i/(blockHeight*blockWidth))*2)&0xff;
		}
	}

	public void refactorData(int[] data, int blockWidth, int blockHeight, int pixelsPerByte) {
		copy = new int[data.length];

int outputStride=numInputValues;
		int blocksPerRow = outputStride/blockWidth;
		int scanningHead = 0;
		int outputHead = 0;
		int innerx=0;
		int innery=0;
		int blocksInRow=0;

		//maketestData(data,blockWidth,blockHeight);

		for (;;) {
			if (outputHead>=copy.length || scanningHead>=data.length) break;

			copy[outputHead]=data[scanningHead];
			outputHead++;
			scanningHead++;
			innerx++;

			if (innerx==blockWidth) {
				innerx=0;
				innery++;
				outputHead+=outputStride-blockWidth;
				if (innery==blockHeight) {
					innery=0;
					outputHead-=(outputStride*blockHeight);
					outputHead+=blockWidth;
					blocksInRow++;
				}
				if (blocksInRow>=blocksPerRow) {
					blocksInRow=0;
					outputHead+=(outputStride*blockHeight);
					outputHead-=outputStride;
				}
			}
		}



	}

	@Override
	public BufferedImage drawRow(int[] data, int offset) {
		// hack
		if (copy==null) {
			refactorData(data, 4,8,2);
		}

		Graphics g = bufferedImage.getGraphics();
		
		g.setColor(colBg);
		g.clearRect(0,0,bufferedImage.getWidth(),bufferedImage.getHeight());
		
		int value = 0;
		int count = 0;
		int component = 50;
		for (int i=0;i<numInputValues;i++) {
			//value = data[blockAdjuster(offset, i, 4, 8)]&0xff;
			value = copy[blockAdjuster(offset, i, 4, 8)]&0xff;
			//value = data[i+offset]&0xff;
			calculateSubPixels(value);
			for (int j=0;j<pixelsPerByte;j++) {
				component=subPixelBuffer[j];
				g.setColor(palette[component&0xff]);
				g.fillRect(count*pixelSize, 0, (count+1)*pixelSize, pixelSize);
				count++;
			}
			
			
		}
		
		return bufferedImage;
	}

	public int blockAdjuster(int startIndex, int index, int blockWidth, int blockHeight) {
		return startIndex+index;

//		int rowVolume = (numInputValues / pixelsPerByte) * blockHeight;
//		int row = startIndex / rowVolume;

//		int delta = index-startIndex;
//		int blockSize = 8;
//
//		int blockNum = delta/blockSize;
//		int blockMod = delta%blockSize;
//		return index+(blockNum*(blockSize*blockSize));
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

package com.physmo.romexplorer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class TextPanel extends JPanel
{
	RowDrawer rowDrawer = null;
	int dirtyRowStart;
	int dirtyRowEnd;
	private static final long serialVersionUID = -5541242543785611502L;
	Color debugColor = new Color(255, 0, 128);
	
	private DataFile df = null;
	
	public TextPanel(DataFile df, RowDrawer textRowDrawer) {
		this.df = df;
		this.rowDrawer = textRowDrawer;
		
		int numRows = df.size() / textRowDrawer.getBytesPerRow();
				
		Dimension preferredSize = new Dimension(
				textRowDrawer.getOutputRowWidth(),
				textRowDrawer.getOutputRowHeight()*numRows);
		
		this.setPreferredSize(preferredSize);
		
		System.out.println("Preferred height: "+textRowDrawer.getOutputRowHeight()*numRows);
	}

	public void paintComponent(Graphics g) {
		
        Color bg = getBackground();
        Rectangle r = g.getClipBounds();

        g.setColor(debugColor);

        calculateDirtyRows(r);
        
        for (int i=dirtyRowStart;i<dirtyRowEnd;i++) {
        	BufferedImage bi = rowDrawer.drawRow(df.getData(), i*rowDrawer.getBytesPerRow());
        	g.drawImage(bi,0,i*rowDrawer.getOutputRowHeight(),null);
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
		if (r==null) System.out.println("Feck"); 
		if (rowDrawer==null) System.out.println("Feck rowDrawer"); 
		
		int rowHeight = rowDrawer.getOutputRowHeight();
		dirtyRowStart = (r.y / rowHeight);
		dirtyRowEnd = ((r.y+r.height) / rowHeight)+1;
	}
	
}
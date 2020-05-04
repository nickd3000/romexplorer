package com.physmo.romexplorer;
 
import java.awt.*;
import java.io.IOException;

import com.physmo.romexplorer.*;

/*
 * HelloWorldSwing.java requires no other files. 
 */
import javax.swing.*;        
 
public class Application {
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     * @throws IOException 
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");

        frame.setSize(300, 200);
        Color bgColor = new Color(100, 100, 200);
        frame.setBackground(bgColor);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add the ubiquitous "Hello World" label.
        //JLabel label = new JLabel("Hello World");
        //frame.getContentPane().add(label);
        
        //DataFile df = createDummyDataFile(2000);
        DataFile dataFile=null;
		try {
			dataFile = new BasicDataFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        		
        //TextPanel panel = new TextPanel(df, new RowDrawerText8Bit());
        TextPanel panel1 = new TextPanel(dataFile, new RowDrawerText8Bit());
		//TextPanel panel = new TextPanel(dataFile, new RowDrawerPixel8Bit());
        JScrollPane scrollPane1 = new JScrollPane(panel1);
    	scrollPane1.setSize(200, 200);
    	scrollPane1.setVisible(true);


		TextPanel panel2 = new TextPanel(dataFile, new RowDrawerPixel8Bit());
		JScrollPane scrollPane2 = new JScrollPane(panel2);
		scrollPane2.setSize(200, 200);
		scrollPane2.setVisible(true);
    	
    	int fontSize = 14;
    	int bytesPerRow = 8;
    	JTable table = new JTable(new DataTableModel(dataFile,bytesPerRow));
    	table.setFont(new Font(Font.MONOSPACED, Font.PLAIN, fontSize));
    	table.getColumnModel().getColumn(0).setPreferredWidth(fontSize*10);
    	table.getColumnModel().getColumn(1).setPreferredWidth(fontSize*bytesPerRow*3);
    	table.getColumnModel().getColumn(2).setPreferredWidth(fontSize*bytesPerRow*2);
    	//table.setFont(new Font("Serif", Font.BOLD, 20));

		GridLayout gridLayout = new GridLayout(3,1);
		frame.getContentPane().setLayout(gridLayout);

        frame.getContentPane().add(scrollPane1);
		frame.getContentPane().add(scrollPane2);
        
        JScrollPane scrollPane = new JScrollPane(table);
        frame.getContentPane().add(scrollPane);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }


    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    
    public static DataFile createDummyDataFile(int size) {
    	BasicDataFile df = null;
		try {
			df = new BasicDataFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	df.setSize(size);
    	for (int i=0;i<size;i++) {
    		df.setValue(i, i&0xff);
    	}
    	return df;
    }
}


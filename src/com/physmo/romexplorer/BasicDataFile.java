package com.physmo.romexplorer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BasicDataFile implements DataFile {

	private int [] data = null;
	
	public BasicDataFile() throws IOException {
		int arraySize = 1024*200;
		data = new int[arraySize];
		
		FileInputStream in = null;
		
		ClassLoader classLoader = getClass().getClassLoader();
		//File file = new File(classLoader.getResource("Tetris.gb").getFile());
		//File file = new File(classLoader.getResource("FinalFantasyIII.sfc").getFile());
		//File file = new File(classLoader.getResource("SuperMarioLand.gb").getFile());
		//File file = new File(classLoader.getResource("Cadaver_1990_A.adf").getFile());
		//File file = new File(classLoader.getResource("Cadaver_1990_B.adf").getFile());
		//File file = new File(classLoader.getResource("Fish.adf").getFile());
		//File file = new File(classLoader.getResource("SuperMarioWorld.smc").getFile());
		//File file = new File(classLoader.getResource("pwd").getFile());
		File file = new File(classLoader.getResource("finders.prg").getFile());
		
		
        try {
            in = new FileInputStream(file); //"/Tetris.gb");
            
            int c;
            int count=0;;
            while ((c = in.read()) != -1) {
            	if (count<arraySize) data[count++] = c;   
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }
	}
	
	@Override
	public int getValue(int index) {
		return data[index];
	}

	@Override
	public int size() {
		return data.length;
	}

	@Override
	public void setValue(int index, int value) {
		data[index] = value;
	}

	@Override
	public void setSize(int size) {
		data = new int[size];
	}

	@Override
	public int[] getData() {
		return data;
	}

}

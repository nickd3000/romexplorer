package com.physmo.romexplorer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class BasicDataFile implements DataFile {

    private int[] data = null;

    public BasicDataFile() {
        int arraySize = 1024 * 200;
        data = new int[arraySize];

        ClassLoader classLoader = getClass().getClassLoader();

        File file = new File(classLoader.getResource("/floracy.gbc").getFile());

        try {
            load(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }

//        int histogram[] = BlockHistogram.histogram(data);
//		System.out.println("Histogram: ");
//		for (int i=0;i<8;i++) {
//			System.out.println(histogram[i]);
//		}
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
    public void load(String path) throws IOException {
        System.out.println("Load path:"+path);
        FileInputStream in = null;

        long fileSize = getFileSize(path);
        data = new int[(int) fileSize];

        int arraySize = (int) fileSize;

        try {
            in = new FileInputStream(path); //"/Tetris.gb");

            int c;
            int count = 0;
			while ((c = in.read()) != -1) {
                if (count < arraySize) data[count++] = c;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    @Override
    public int[] getData() {
        return data;
    }

    public long getFileSize(String fileName) {
        File f = new File(fileName);
        return f.length();
    }
}

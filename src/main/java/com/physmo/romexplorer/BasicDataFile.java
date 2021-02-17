package com.physmo.romexplorer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class BasicDataFile implements DataFile {

    private int[] data = null;
    String fileName;
    long fileSize;

    public BasicDataFile() {
        int arraySize = 1024 * 200;
        data = new int[arraySize];

        ClassLoader classLoader = getClass().getClassLoader();

        URL resource = classLoader.getResource("floracy.gbc");


        File file = new File(classLoader.getResource("ultetris.BIN").getFile());

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
    public String getFileName() {
        return fileName;
    }

    @Override
    public long getFileSize() {
        return fileSize;
    }

    @Override
    public void load(String path) throws IOException {
        System.out.println("Load path:"+path);
        FileInputStream in = null;
this.fileName=path;
        this.fileSize = readFileSize(path);
        data = new int[(int) fileSize];

        int arraySize = (int) fileSize;

        try {
            in = new FileInputStream(path);

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

    public long readFileSize(String fileName) {
        File f = new File(fileName);
        return f.length();
    }
}

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
        //File file = new File(classLoader.getResource("Tetris.gb").getFile());
        File file = new File(classLoader.getResource("Castlevania.gb").getFile());
        //File file = new File(classLoader.getResource("drmario.gb").getFile());
        //File file = new File(classLoader.getResource("megaman.gb").getFile());
        //File file = new File(classLoader.getResource("picross.gb").getFile());
        //File file = new File(classLoader.getResource("pokemonsilver.gbc").getFile());
        //File file = new File(classLoader.getResource("rtypeii.gb").getFile());
        //File file = new File(classLoader.getResource("xenon2.gb").getFile());
        //File file = new File(classLoader.getResource("SuperMarioLand.gb").getFile());
        //File file = new File(classLoader.getResource("FinalFantasyIII.sfc").getFile());
        //File file = new File(classLoader.getResource("Cadaver_1990_A.adf").getFile());
        //File file = new File(classLoader.getResource("Cadaver_1990_B.adf").getFile());
        //File file = new File(classLoader.getResource("Fish.adf").getFile());
        //File file = new File(classLoader.getResource("SuperMarioWorld.smc").getFile());
        //File file = new File(classLoader.getResource("pwd").getFile());
        //File file = new File(classLoader.getResource("finders.prg").getFile());
        //File file = new File(classLoader.getResource("twinkingdom.d64").getFile());
        //File file = new File(classLoader.getResource("huntersm.prg").getFile());
        //File file = new File(classLoader.getResource("cpalace.dat").getFile());
        //File file = new File(classLoader.getResource("prince.exe").getFile());
        //File file = new File(classLoader.getResource("LEV1BLOX.BIN").getFile());
        //File file = new File(classLoader.getResource("FIRE.EXE").getFile());
        //File file = new File(classLoader.getResource("frankie.d64").getFile());
        //File file = new File(classLoader.getResource("entombed.d64").getFile());
        //File file = new File(classLoader.getResource("sonic.sms").getFile());
        //File file = new File(classLoader.getResource("shinobi.sms").getFile());

        try {
            load("/Users/nick/dev/emulatorsupportfiles/c64/games/rambo.prg");
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

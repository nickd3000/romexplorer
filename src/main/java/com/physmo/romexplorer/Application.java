package com.physmo.romexplorer;

import com.physmo.romexplorer.gui.Gui;
import com.physmo.romexplorer.tilers.TilerRepo;

public class Application {

    public Gui gui = new Gui();
    DataFile dataFile = null;
    TilerRepo tilerRepo = new TilerRepo();
    String tilerName;

    public Application() {
        dataFile = new BasicDataFile();
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Application app = new Application();
                app.gui.createAndShowGUI(app);
            }
        });
    }

    public static DataFile createDummyDataFile(int size) {
        BasicDataFile df = null;

        df = new BasicDataFile();


        df.setSize(size);
        for (int i = 0; i < size; i++) {
            df.setValue(i, i & 0xff);
        }
        return df;
    }

    public String getTilerName() {
        return tilerName;
    }

    public void setTilerName(String tilerName) {
        this.tilerName = tilerName;
    }

    public TilerRepo getTilerRepo() {
        return tilerRepo;
    }

    public DataFile getDataFile() {
        return dataFile;
    }
}


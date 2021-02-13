package com.physmo.romexplorer;

import java.io.IOException;

public interface DataFile {

    // move to implementation byte [] bytes = null;
    void load(String path) throws IOException;

    int[] getData();

    int getValue(int index);

    void setValue(int index, int value);

    int size();

    void setSize(int size);
}

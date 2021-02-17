package com.physmo.romexplorer.tilers;

public abstract class Tiler {

    int tileWidth, tileHeight, bytesPerTile;
    String tilerName = "No Name";
    String description = "No Name";

    public int getTileWidth() { return tileWidth; }
    public int getTileHeight() { return tileHeight; }
    public int getBytesPerTile() { return bytesPerTile; }

    public String getName() { return tilerName; }
    public String getDescription() { return description; }

    abstract public int[] getTile(int[] data, int index);

}

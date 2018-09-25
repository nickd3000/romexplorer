package com.physmo.romexplorer;

public interface DataFile {

	// move to implementation byte [] bytes = null;
	public int[] getData();
	public int getValue(int index);
	public void setValue(int index, int value);
	public int size();
	public void setSize(int size);
}

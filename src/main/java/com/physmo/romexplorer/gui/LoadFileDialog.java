package com.physmo.romexplorer.gui;

import javax.swing.*;
import java.io.File;

public class LoadFileDialog {
    static JFileChooser fileChooser = null;

    public static String show(JFrame frame) {
        String fileName;

        if (fileChooser == null) fileChooser = new JFileChooser();

        int option = fileChooser.showOpenDialog(frame);
        if (option == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            fileName = file.getAbsolutePath(); //file.getName();
        } else {
            fileName = null;
        }

        return fileName;

    }

    // gba: 16 col 8x8
}

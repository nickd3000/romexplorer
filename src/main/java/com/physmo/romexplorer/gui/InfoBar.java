package com.physmo.romexplorer.gui;

import com.physmo.romexplorer.Application;
import com.physmo.romexplorer.DataFile;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class InfoBar extends JPanel {

    Application application;
    final Map<String, JLabel> labelMap = new HashMap<>();

    public InfoBar(Application application) {
        this.application = application;

        JLabel labelFileName = new JLabel();
        labelFileName.setPreferredSize(new Dimension(200, 24));
        labelFileName.setBorder(new BevelBorder(1));
        labelMap.put("filename", labelFileName);
        this.add(labelFileName);
        labelFileName.setText("no file");

        JLabel labelFileSize = new JLabel();
        labelFileSize.setPreferredSize(new Dimension(200, 24));
        labelFileSize.setBorder(new BevelBorder(1));
        labelMap.put("filesize", labelFileSize);
        this.add(labelFileSize);
        labelFileSize.setText("0");

        installTickTimer(10);
    }

    private void tick() {
        DataFile dataFile = application.getDataFile();

        String fileName = dataFile.getFileName();
        labelMap.get("filename").setText(prettyFilePath(fileName));

        long fileSize = dataFile.getFileSize();
        String strFileSize = ""+prettySize(fileSize);
        labelMap.get("filesize").setText(strFileSize);
    }

    private String prettySize(long size) {
        if (size<1024) {
            return ""+size+" bytes";
        }

        return ""+size/1024+" kb";
    }

    private String prettyFilePath(String  name) {
        int maxLength = 20;
        int len = name.length();
        if (len<=maxLength) return name;
        else return ("..."+name.substring(len-(maxLength-3),len));
    }

    private void installTickTimer(int fps) {
        int timerDelay = 1000 / fps;
        Timer timer = new Timer(timerDelay, evt -> tick());
        timer.start();
    }
}

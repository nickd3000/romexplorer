package com.physmo.colormapper;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class ColorMapRepo {
    List<ColorMapContainer> list = new ArrayList<>();

    public ColorMapRepo() {
        initMap();
    }

    public List<ColorMapContainer> getList() {
        return list;
    }

    public void createEntry(String name, Color[] guide) {
        ColorMapContainer newContainer = new ColorMapContainer();
        newContainer.name = name;
        newContainer.guide = guide;
        newContainer.map = GenerateColorMap.generateMapFromPreset(guide);
        list.add(newContainer);
    }

    public void initMap() {

        createEntry("Gameboy Original", new Color[]{
                new Color(45, 54, 29),
                new Color(176, 192, 113),
        });

        createEntry("White to black", new Color[]{
                new Color(255, 255, 255),
                new Color(0, 0, 0),
        });

        createEntry("Black to white", new Color[]{
                new Color(0, 0, 0),
                new Color(255, 255, 255),
        });

        createEntry("c64", new Color[]{
                new Color(26, 26, 26),
                new Color(208, 208, 208),
                new Color(113, 57, 42),
                new Color(99, 136, 133),

                new Color(128, 61, 170),
                new Color(99, 143, 68),
                new Color(36, 62, 128),
                new Color(150, 152, 55),

                new Color(139, 76, 39),
                new Color(72, 41, 9),
                new Color(128, 75, 110),
                new Color(66, 66, 66),

                new Color(84, 84, 84),
                new Color(173, 212, 121),
                new Color(129, 124, 176),
                new Color(157, 157, 157),

        });

    }
}

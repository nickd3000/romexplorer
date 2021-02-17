package com.physmo.romexplorer.gui;

import com.physmo.romexplorer.Application;
import com.physmo.romexplorer.rowdrawer.RowDrawerPixel8Bit;
import com.physmo.romexplorer.rowdrawer.RowDrawerText8Bit;
import com.physmo.romexplorer.tilers.Tiler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Gui {

    private static JFrame mainFrame;
    private static Color bgColor;
    private static JPanel commandPanel;
    private static JPanel contentPanel;
    private static JButton buttonLoad;
    private static BasePanel basePanel;
    private static JScrollPane textPanelScrollPane;
    private static BasePanel pixelPanel;
    private static JScrollPane pixelPanelScrollPane;
    private static JPanel summaryStripPanel;
    private static JComboBox tilerCombo;
    private static InfoBar infoBar;
    SummaryStrip summaryStrip = new SummaryStrip();
    Application application;

    public void refreshAll() {

        //((RowDrawerPixel8Bit)(pixelPanel.getRowDrawer())).clearCopy();
        summaryStrip.reset();

        pixelPanel.refreshPanelDimensions();
        basePanel.refreshPanelDimensions();

        textPanelScrollPane.repaint();
        pixelPanelScrollPane.repaint();
        summaryStripPanel.repaint();
    }

    public void refactorImageData() {
//        String tilerName = (String) (tilerCombo.getSelectedItem());
//        Tiler byName = application.getTilerRepo().getByName(tilerName);
        if (application.getCurrentTiler() == null) System.out.println("tiler is null");
        if (application.getCurrentTiler() != null) {
            ((RowDrawerPixel8Bit) (pixelPanel.getRowDrawer())).refactorData(application.getDataFile().getData(), application.getCurrentTiler());
        }
    }

    public void createAndShowGUI(Application application) {

        this.application = application;

        //Create and set up the window.
        mainFrame = new JFrame("Rom Explorer");
        mainFrame.setLayout(new BorderLayout());

        //frame.setSize(1024,768);
        mainFrame.setSize(300, 200);
        bgColor = new Color(100, 100, 100);
        //mainFrame.setBackground(bgColor);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        commandPanel = new JPanel();
        contentPanel = new JPanel();

        commandPanel.setLayout(new FlowLayout());
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.LINE_AXIS));


        // Command panel
        buttonLoad = new JButton("Load");
        buttonLoad.addActionListener(e -> {
            System.out.println("Button pressed.");
            String filename = LoadFileDialog.show(mainFrame);
            if (filename != null) {
                try {
                    application.getDataFile().load(filename);
                    refreshAll();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        commandPanel.add(buttonLoad);

        String[] tilerNames = application.getTilerRepo().getNameList(); //{ "Tiler16Col", "TilerGB" };

        tilerCombo = new JComboBox(tilerNames);
        tilerCombo.setSelectedIndex(0);
        commandPanel.add(tilerCombo);
        tilerCombo.addActionListener(e -> {
            if (e.getActionCommand().equals("comboBoxChanged")) {
                String selected = (String) (((JComboBox) e.getSource()).getSelectedItem());
                System.out.println("changed tiler to " + selected);

                String tilerName = (String) (tilerCombo.getSelectedItem());
                Tiler newTiler = application.getTilerRepo().getByName(tilerName);
                application.setCurrentTiler(newTiler);

                refactorImageData();
                refreshAll();
            }
            //String petName = (String)e.getSelectedItem();
        });

        basePanel = new BasePanel(application.getDataFile(), new RowDrawerText8Bit(application));
        textPanelScrollPane = new JScrollPane(basePanel);
        textPanelScrollPane.setSize(200, 200);
        textPanelScrollPane.setVisible(true);

        pixelPanel = new BasePanel(application.getDataFile(), new RowDrawerPixel8Bit(application));
        pixelPanelScrollPane = new JScrollPane(pixelPanel);
        pixelPanelScrollPane.setSize(200, 200);
        pixelPanelScrollPane.setVisible(true);


        summaryStripPanel = new JPanel() {
            @Override
            public void paint(Graphics g) {
                super.paint(g);
                BufferedImage summaryStripImage = summaryStrip.getSummaryStrip(application.getDataFile().getData());

                if (summaryStripImage != null) {
                    g.drawImage(summaryStripImage,
                            0, 0,
                            40, this.getVisibleRect().height, null);
                }
            }
        };

        summaryStripPanel.setPreferredSize(new Dimension(40, summaryStripPanel.getPreferredSize().height));

        infoBar = new InfoBar(application);

        contentPanel.add(textPanelScrollPane);
        contentPanel.add(summaryStripPanel);
        contentPanel.add(pixelPanelScrollPane);

        mainFrame.add(commandPanel, BorderLayout.NORTH);
        mainFrame.add(contentPanel, BorderLayout.CENTER);
        mainFrame.add(infoBar, BorderLayout.SOUTH);

        //Display the window.
        mainFrame.pack();
        mainFrame.setVisible(true);

    }


}

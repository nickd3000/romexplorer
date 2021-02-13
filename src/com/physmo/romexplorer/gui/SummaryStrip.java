package com.physmo.romexplorer.gui;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class SummaryStrip {
    static int numInputValues = 32;
    public boolean summaryStripRendered = false;
    BufferedImage summaryStrip = new BufferedImage(20, 400, BufferedImage.TYPE_INT_ARGB);

    public void reset() {
        summaryStripRendered = false;
    }

    public BufferedImage getSummaryStrip(int[] data) {
        if (summaryStripRendered) return summaryStrip;

        int stripHeight = summaryStrip.getHeight();
        int numRowsInFile = (int) ((float) data.length / (float) (numInputValues));
        //numInputValues
        Graphics graphics = summaryStrip.getGraphics();
        for (int y = 0; y < stripHeight; y++) {
            float normalisedPositionInFile = ((float) y / (float) stripHeight);
            int positionInFile = (int) (normalisedPositionInFile * (float) numRowsInFile);
            Color stripColour = getStripColor(data, positionInFile * numInputValues);
            graphics.setColor(stripColour);
            graphics.drawLine(0, y, 10, y);

            graphics.setColor(getStripEntropy(data, positionInFile * numInputValues));
            graphics.drawLine(10, y, 20, y);

            List<String> wordList = findWordsInChunk(data, positionInFile * numInputValues);
            if (isWordListWorthFlagging(wordList)) {
                graphics.setColor(Color.YELLOW);
                graphics.drawLine(0, y, 2, y + 1);
            }
        }

        summaryStripRendered = true;
        return summaryStrip;

    }

    public Color getStripColor(int[] data, int position) {
        int r = 0, g = 0, b = 0;

        if (position + numInputValues + 10 >= data.length) return Color.BLACK;

        for (int i = 0; i < numInputValues; i++) {
            int value = data[position + i] & 0xff;
            Color col = getColForCharBrighter(value);
            r += col.getRed();
            g += col.getGreen();
            b += col.getBlue();
        }

        r /= numInputValues;
        g /= numInputValues;
        b /= numInputValues;

        return new Color(r, g, b);
    }

    public boolean isWordListWorthFlagging(List<String> words) {

        int goodLength = 3; // How small a word can be
        int goodCount = 2; // how many there should be to flag it.
        int count = 0;
        int commonWordCount = 0;

        for (String word : words) {
            if (word.length() >= goodLength) count++;

            if (word.length() > 2 && isWordCommon(word)) commonWordCount++;
        }

//        if (count >= goodCount) return true;
//        return false;

        return commonWordCount > 0;
    }

    public boolean isWordCommon(String word) {
        word = word.toLowerCase();
        for (String commonWord : WordList.commonWords) {
            if (commonWord.equals(word)) return true;
        }
        return false;
    }

    public List<String> findWordsInChunk(int[] data, int position) {
        List<String> words = new ArrayList<>();
        String currentWord = "";
        boolean inWord = false;

        for (int i = 0; i < numInputValues; i++) {
            int c = data[position + i] & 0xff;
            boolean validChar = isValidWordCharacter((char) c);

            if (inWord && validChar) {
                // Continue word
                currentWord += (char) c;
            } else if (inWord && !validChar) {
                // Finish word
                words.add(currentWord);
                currentWord = "";
                inWord = false;
            } else if (!inWord && validChar) {
                // Start new word
                inWord = true;
                currentWord += (char) c;
            }

        }
        return words;
    }

    public boolean isValidWordCharacter(char c) {
        if (c >= 'a' && c <= 'z') {
            return true;
        }
        return c >= 'A' && c <= 'Z';
    }

    public Color getStripEntropy(int[] data, int position) {
        int max = 0;
        int prevValue = 0;
        int entropy = 0;
        int rowAverage = 0;
        for (int i = 0; i < numInputValues; i++) {
            rowAverage += data[position + i] & 0xff;
        }
        rowAverage /= numInputValues;

        for (int i = 0; i < numInputValues; i++) {
            int val = data[position + i] & 0xff;
            prevValue = val;
            entropy += Math.abs(rowAverage - val);
            entropy += Math.abs(prevValue - val);
            max += 0xff + 0xff;
        }

        entropy /= (max / 0xff);
        entropy *= 3;
        if (entropy > 0xff) entropy = 0xff;

        return new Color(entropy, entropy, entropy);
    }

    public Color getColForCharBrighter(int val) {
        if (val >= 65 && val <= 90) return Color.GREEN;
        if (val >= 97 && val <= 122) return Color.GREEN;
        if (val >= 48 && val <= 57) return Color.BLUE;
        return Color.DARK_GRAY;
    }
}

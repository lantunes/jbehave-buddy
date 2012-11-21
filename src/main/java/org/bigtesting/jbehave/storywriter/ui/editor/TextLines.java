package org.bigtesting.jbehave.storywriter.ui.editor;

import java.util.ArrayList;
import java.util.List;

public class TextLines {

    private final List<Line> lines = new ArrayList<Line>();

    public TextLines(String text) {
        String[] textLines = text.split("\n");
        int[] lineOffsets = getLineOffsets(textLines);
        for (int i = 0; i < textLines.length; i++) {
            lines.add(new Line(textLines[i], lineOffsets[i]));
        }
    }

    public int count() {
        return lines.size();
    }

    private int[] getLineOffsets(String[] lines) {
        int[] offsets = new int[lines.length];
        int charCount = 0;
        for (int i = 0; i < offsets.length; i++) {
            offsets[i] = charCount;
            charCount += lines[i].length() + 1;
        }
        return offsets;
    }

    public Line getLine(int index) {
        return lines.get(index);
    }
}
package org.bigtesting.jbehave.storywriter.ui;

public interface StepsDocument {

    void addStylesToDocument();

    void highlightTerm(int keywordStart, int keywordLength, EditorStyle style);

    void highlightLine(int lineStart, EditorStyle style);

    String getEntireTextContent();

    void clearAllTextStyles();
    
    void setText(String text);
}

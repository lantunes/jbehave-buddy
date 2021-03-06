package org.bigtesting.jbehave.buddy.tests.ui.editor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.bigtesting.jbehave.buddy.core.ui.EditorStyle;
import org.bigtesting.jbehave.buddy.core.ui.StepsDocument;

public class TestableStepsDocument implements StepsDocument {

    private String text;

    private final List<HighlightedItem> highlightedItems = new ArrayList<HighlightedItem>();

    public TestableStepsDocument() {
    }

    public void setText(String text) {
        this.text = text;
    }

    public HighlightedItem[] getHighlightedItems() {
        HighlightedItem[] items = highlightedItems.toArray(new HighlightedItem[highlightedItems.size()]);
        Arrays.sort(items, new Comparator<HighlightedItem>() {
            public int compare(HighlightedItem o1, HighlightedItem o2) {
                return o1.text().compareTo(o2.text());
            }
        });
        return items;
    }

    public void addStylesToDocument() {
    }

    public void highlightTerm(int keywordStart, int keywordLength, EditorStyle style) {
        int keywordEnd = keywordStart + keywordLength;
        if (keywordStart < text.length() && keywordEnd <= text.length()) {
            String keyword = text.substring(keywordStart, keywordEnd);
            highlightedItems.add(new HighlightedItem(keyword, style));
        }
    }

    public void highlightLine(int lineStart, EditorStyle style) {
        int lineEndIndex = getEntireTextContent().indexOf("\n", lineStart);
        int commentLength = ((lineEndIndex != -1) ? lineEndIndex : text.length()) - lineStart;
        highlightTerm(lineStart, commentLength, style);
    }

    public String getEntireTextContent() {
        return text;
    }

    public void clearAllTextStyles() {
    }

    public static class HighlightedItem {
        private final String text;
        private final EditorStyle style;

        public HighlightedItem(String text, EditorStyle style) {
            this.text = text;
            this.style = style;
        }

        public String text() {
            return text;
        }

        public EditorStyle style() {
            return style;
        }
    }
}

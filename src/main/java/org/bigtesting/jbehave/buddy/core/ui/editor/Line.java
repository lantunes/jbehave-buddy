package org.bigtesting.jbehave.buddy.core.ui.editor;

public class Line {
    private final String content;
    private final int offset;

    public Line(String content, int offset) {
        this.content = content;
        this.offset = offset;
    }

    public String content() {
        return content;
    }

    public int offset() {
        return offset;
    }
}
package org.bigtesting.jbehave.buddy.ui.editor;

public class Region {
    public int startIndex;
    public int endIndex;

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + startIndex;
        result = prime * result + endIndex;
        return result;
    }

    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o == this)
            return true;
        if (!(o instanceof Region))
            return false;
        Region that = (Region) o;
        return this.startIndex == that.startIndex && this.endIndex == that.endIndex;
    }
}
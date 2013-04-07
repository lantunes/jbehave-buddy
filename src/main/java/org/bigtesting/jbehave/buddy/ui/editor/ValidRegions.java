package org.bigtesting.jbehave.buddy.ui.editor;

import java.util.ArrayList;
import java.util.List;

public class ValidRegions {
    private final List<Region> validRegions = new ArrayList<Region>();

    public ValidRegions(List<String> steps, String text) {
        for (String step : steps) {
            int startIndex = text.indexOf(step);
            while (startIndex > -1) {
                Region region = new Region();
                region.startIndex = startIndex;
                region.endIndex = region.startIndex + step.length();
                addValidRegion(region);

                startIndex = text.indexOf(step, startIndex + 1);
            }
        }
    }

    public void addValidRegion(Region region) {
        if (validRegions.contains(region))
            return;
        validRegions.add(region);
    }

    public boolean contains(int charIndex) {
        for (Region region : validRegions) {
            if (charIndex >= region.startIndex && charIndex < region.endIndex)
                return true;
        }
        return false;
    }
}
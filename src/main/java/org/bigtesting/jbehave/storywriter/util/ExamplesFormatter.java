package org.bigtesting.jbehave.storywriter.util;

import java.util.HashMap;
import java.util.Map;

public class ExamplesFormatter {

    public static String format(String[][] examples) {

        Map<Integer, Integer> columnLengthMap = new HashMap<Integer, Integer>();

        for (int k = 0; k < examples.length; k++) {
            String[] tokens = examples[k];
            for (int i = 0; i < tokens.length; i++) {

                Integer colSize = columnLengthMap.get(i);
                String trimmedVal = tokens[i].trim();
                if (colSize == null || colSize < trimmedVal.length()) {
                    columnLengthMap.put(i, trimmedVal.length());
                }

            }
        }

        StringBuilder formatted = new StringBuilder();

        for (int k = 0; k < examples.length; k++) {
            String[] tokens = examples[k];
            StringBuilder formattedLine = new StringBuilder();
            for (int i = 0; i < tokens.length; i++) {

                Integer colSize = columnLengthMap.get(i);
                StringBuilder val = new StringBuilder(tokens[i].trim());
                while (val.length() != colSize) {
                    val.append(" ");
                }
                val.append(" |");
                formattedLine.append(val.toString());
            }
            formatted.append(formattedLine.toString()).append("\n");
        }

        return formatted.toString();
    }
}

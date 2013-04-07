package org.bigtesting.jbehave.buddy.tests.util;

import org.bigtesting.jbehave.buddy.util.ExamplesFormatter;
import org.junit.Assert;
import org.junit.Test;

public class TestExamplesFormatter {

    @Test
    public void formatsExamples() {

        String[][] examples = new String[][] {
                { "test", "test1", "aLongerColumn" },
                { "blah blah blah", "12", "0.134343" },
                { "blah", "12213232", "0.343" }, 
        };

        String formatted = ExamplesFormatter.format(examples);

        String expected = "test           |test1    |aLongerColumn |\n"
                +         "blah blah blah |12       |0.134343      |\n"
                +         "blah           |12213232 |0.343         |\n";
        Assert.assertEquals(expected, formatted);
    }
}

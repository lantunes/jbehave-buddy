package org.bigtesting.jbehave.buddy.ui;

import java.io.PrintWriter;

public class StoryExporter {

    public void export(StoryModel storyModel) {
        
        String story = storyModel.print();
        
        String[] lines = story.split("\n");
        
        PrintWriter pw = new PrintWriter(System.out);
        for (String line : lines) {
            pw.println(line);
        }
        pw.flush();
        pw.close();
    }
}

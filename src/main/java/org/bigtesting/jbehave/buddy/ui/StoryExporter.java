package org.bigtesting.jbehave.buddy.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

public class StoryExporter {

    public void export(StoryModel storyModel) {
        exportToStream(System.out, storyModel);
    }
    
    public void exportToFile(File file, StoryModel storyModel) throws Exception {
        exportToStream(new FileOutputStream(file), storyModel);
    }
    
    private void exportToStream(OutputStream out, StoryModel storyModel) {
        
        String story = storyModel.print();
        
        String[] lines = story.split("\n");
        
        PrintWriter pw = new PrintWriter(out);
        for (String line : lines) {
            pw.println(line);
        }
        pw.flush();
        pw.close();
    }
}

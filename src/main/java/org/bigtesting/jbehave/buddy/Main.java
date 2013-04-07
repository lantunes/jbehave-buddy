package org.bigtesting.jbehave.buddy;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.UIManager;

import org.bigtesting.jbehave.buddy.ui.Screen;
import org.bigtesting.jbehave.buddy.util.ExceptionFileWriter;

public class Main {

    public static void main(final String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable e) {
            ExceptionFileWriter.writeException(e);
            System.exit(1);
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                    File storyFile = null;
                    if (args.length > 0) {
                        storyFile = new File(args[0]);
                    }
                    
                    Screen screen = new Screen(storyFile);
                    screen.display();

                } catch (Exception e) {
                    ExceptionFileWriter.writeException(e);
                    System.exit(1);
                }
            }
        });
    }
}

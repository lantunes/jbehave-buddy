package org.bigtesting.jbehave.storywriter;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.UIManager;

import org.bigtesting.jbehave.storywriter.ui.Screen;

public class Main {

    public static void main(final String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable e) {
            e.printStackTrace();
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
                    e.printStackTrace();
                }
            }
        });
    }
}

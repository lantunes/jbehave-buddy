package org.bigtesting.jbehave.storywriter;

import java.awt.EventQueue;

import javax.swing.UIManager;

import org.bigtesting.jbehave.storywriter.ui.Screen;

public class Main {

    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Throwable e) {
            e.printStackTrace();
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {

                    Screen screen = new Screen();
                    screen.display();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

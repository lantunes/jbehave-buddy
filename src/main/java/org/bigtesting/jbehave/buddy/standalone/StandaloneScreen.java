package org.bigtesting.jbehave.buddy.standalone;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import net.miginfocom.swing.MigLayout;

import org.bigtesting.jbehave.buddy.core.ui.Screen;
import org.bigtesting.jbehave.buddy.core.ui.ScreenContext;
import org.bigtesting.jbehave.buddy.core.util.ExceptionFileWriter;
import org.bigtesting.jbehave.buddy.core.util.Resources;

public class StandaloneScreen implements ScreenContext {

    private final Screen screen;
    
    private JFrame mainFrame;
    private JMenuItem saveMenuItem;
    
    public StandaloneScreen() {
        this(null);
    }
    
    public StandaloneScreen(File storyFile) {
        this.screen = new Screen(storyFile, this);
        initMainFrame();
        initMenuBar();
    }
    
    private void initMainFrame() {
        mainFrame = new JFrame();
        mainFrame.setPreferredSize(new Dimension(859, 582));
        mainFrame.setMinimumSize(new Dimension(859, 582));
        mainFrame.setTitle(Resources.TITLE);
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("logo.png"));
        } catch (IOException e) {
            ExceptionFileWriter.writeException(e);
        }
        mainFrame.setIconImage(image);
        mainFrame.setName(Resources.MAIN_FRAME);
        mainFrame.setBounds(100, 100, 859, 582);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
        
        mainFrame.getContentPane().add(this.screen.getMainPanel(), "cell 0 0,grow");
    }
    
    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        mainFrame.setJMenuBar(menuBar);

        initFileMenu(menuBar);
        initHelpMenu(menuBar);
    }
    
    private void initFileMenu(JMenuBar menuBar) {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setName(Resources.FILE_MENU);
        menuBar.add(fileMenu);

        initNewStoryMenuItem(fileMenu);
        initOpenExistingStoryMenuItem(fileMenu);
        initSaveMenuItem(fileMenu);
        initExitMenuItem(fileMenu);
    }
    
    private void initNewStoryMenuItem(JMenu fileMenu) {
        JMenuItem newStoryMenuItem = new JMenuItem("New story...");
        newStoryMenuItem.setName(Resources.NEW_STORY_MENU_ITEM);
        fileMenu.add(newStoryMenuItem);
        newStoryMenuItem.setEnabled(!screen.hasExistingStoryFile());
        newStoryMenuItem.setMnemonic('N');
        newStoryMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_N, 
                java.awt.Event.CTRL_MASK));
        newStoryMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                screen.newStory();
            }
        });
    }
    
    private void initOpenExistingStoryMenuItem(JMenu fileMenu) {
        JMenuItem openExistingStoryMenuItem = new JMenuItem("Open existing story...");
        openExistingStoryMenuItem.setName(Resources.OPEN_EXISTING_STORY_MENU_ITEM);
        fileMenu.add(openExistingStoryMenuItem);
        openExistingStoryMenuItem.setEnabled(!screen.hasExistingStoryFile());
        openExistingStoryMenuItem.setMnemonic('O');
        openExistingStoryMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_O, 
                java.awt.Event.CTRL_MASK));
        openExistingStoryMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                screen.openExistingStory();
            }
        });
    }
    
    private void initSaveMenuItem(JMenu fileMenu) {
        saveMenuItem = new JMenuItem("Save...");
        saveMenuItem.setName(Resources.SAVE_MENU_ITEM);
        fileMenu.add(saveMenuItem);
        saveMenuItem.setEnabled(false);
        saveMenuItem.setMnemonic('S');
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(
                java.awt.event.KeyEvent.VK_S, 
                java.awt.Event.CTRL_MASK));
        saveMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                screen.saveStory();
            }
        });
    }
    
    private void initExitMenuItem(JMenu fileMenu) {
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName(Resources.EXIT_MENU_ITEM);
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                close();
            }
        });
        fileMenu.add(exitMenuItem);
    }
    
    private void initHelpMenu(JMenuBar menuBar) {
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setName(Resources.HELP_MENU);
        menuBar.add(helpMenu);
        
        initAboutMenuItem(helpMenu);
    }
    
    private void initAboutMenuItem(JMenu helpMenu) {
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.setName(Resources.ABOUT_MENU_ITEM);
        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JOptionPane.showMessageDialog(mainFrame, 
                        Resources.TITLE + " version " + Resources.version, 
                        "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(aboutMenuItem);
    }
    
    public JFrame getFrame() {
        return mainFrame;
    }
    
    public void display() {
        mainFrame.setVisible(true);
    }
    
    public void close() {
        mainFrame.setVisible(false);
        mainFrame.dispose();
    }

    public void setTitle(String title) {
        mainFrame.setTitle(title);
    }

    public void enableSaving(boolean enable) {
        saveMenuItem.setEnabled(enable);
    }
}

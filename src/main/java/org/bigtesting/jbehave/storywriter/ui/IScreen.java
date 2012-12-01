package org.bigtesting.jbehave.storywriter.ui;

import javax.swing.JTextPane;
import javax.swing.event.TableModelEvent;

public interface IScreen {

    void examplesTableChanged(TableModelEvent e);
    
    JTextPane newStepsTextPane();
}

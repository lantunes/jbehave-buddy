package org.bigtesting.jbehave.buddy.core.ui;

import javax.swing.JTextPane;
import javax.swing.event.TableModelEvent;

public interface IScreen {

    void examplesTableChanged(TableModelEvent e);
    
    JTextPane newStepsTextPane();

    ScreenContext getScreenContext();
}

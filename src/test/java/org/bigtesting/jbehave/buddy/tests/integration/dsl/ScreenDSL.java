package org.bigtesting.jbehave.buddy.tests.integration.dsl;

import org.bigtesting.jbehave.buddy.ui.Screen;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JTextComponentFixture;

public class ScreenDSL {

    private static FrameFixture screen;
    
    public static void createScreen() {
        Screen s = GuiActionRunner.execute(new GuiQuery<Screen>() {
            protected Screen executeInEDT() {
                return new Screen();
            }
        });
        screen = new FrameFixture(s.getFrame());
        screen.show();
    }
    
    public static void typeSteps(String steps) {
        JTextComponentFixture t = screen.textBox(Screen.TEXT_PANE);
        t.setText(steps);
    }
    
    public static void assertThatStepsEditorIsEnabled() {
        screen.textBox(Screen.TEXT_PANE).requireEnabled();
    }
    
    public static void closeScreen() {
        screen.cleanUp();
    }
}

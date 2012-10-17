package org.bigtesting.jbehave.buddy.tests.integration;

import org.bigtesting.jbehave.buddy.ui.Screen;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JTextComponentFixture;
import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

public class ScreenSteps {

    private FrameFixture screen;
    
    @Given("a screen")
    public void createScreen() {
        Screen s = GuiActionRunner.execute(new GuiQuery<Screen>() {
            protected Screen executeInEDT() {
                return new Screen();
            }
        });
        screen = new FrameFixture(s.getFrame());
        screen.show();
    }
    
    @When("I type the steps: $steps")
    public void typeSteps(String steps) {
        JTextComponentFixture t = screen.textBox(Screen.TEXT_PANE);
        t.setText(steps);
    }
    
    @Then("the steps editor is enabled")
    public void assertThatStepsEditorIsEnabled() {
        screen.textBox(Screen.TEXT_PANE).requireEnabled();
    }
    
    /*--------------*/
    
    @AfterScenario
    public void cleanUp() {
        screen.cleanUp();
    }
}

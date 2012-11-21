package org.bigtesting.jbehave.storywriter.tests.integration;

import static org.bigtesting.jbehave.storywriter.ui.Screen.*;

import org.bigtesting.jbehave.storywriter.ui.Screen;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JOptionPaneFixture;
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
        JTextComponentFixture t = screen.textBox(TEXT_PANE);
        t.setText(steps);
    }
    
    @When("I select new story from the file menu")
    public void selectNewStoryFromFileMenu() {
        screen.menuItem(NEW_STORY_MENU_ITEM).click();
    }
    
    @When("I add a new scenario called \"$title\"")
    public void addNewScenario(String title) {
        navigateToScenariosTab();
        screen.button(ADD_SCENARIO_BUTTON).click();
        JOptionPaneFixture op = screen.optionPane();
        op.textBox().setText(title);
        op.okButton().click();
    }
    
    @When("I confirm that I want to create a new story")
    public void confirmCreateNewStory() {
        JOptionPaneFixture op = screen.optionPane();
        op.okButton().click();
    }
    
    @When("I decline that I want to create a new story")
    public void declineCreateNewStory() {
        JOptionPaneFixture op = screen.optionPane();
        op.cancelButton().click();
    }
    
    @When("I decline to add a new scenario")
    public void declineAddNewScenario() {
        screen.button(ADD_SCENARIO_BUTTON).click();
        JOptionPaneFixture op = screen.optionPane();
        op.textBox().setText("Test");
        op.cancelButton().click();
    }
    
    @Then("the steps editor is enabled")
    public void assertThatStepsEditorIsEnabled() {
        navigateToStepsTab();
        screen.textBox(TEXT_PANE).requireEnabled();
    }
    
    @Then("the steps editor is disabled")
    public void assertThatStepsEditorIsDisabled() {
        navigateToStepsTab();
        screen.textBox(TEXT_PANE).requireDisabled();
    }
    
    @Then("the add param value button is disabled")
    public void assertThatAddParamValueButtonIsDisabled() {
        navigateToStepsTab();
        screen.button(ADD_PARAM_VALUE_BUTTON).requireDisabled();
    }
    
    @Then("the add param value button is enabled")
    public void assertThatAddParamValueButtonIsEnabled() {
        navigateToStepsTab();
        screen.button(ADD_PARAM_VALUE_BUTTON).requireEnabled();
    }
    
    @Then("the remove param value button is disabled")
    public void assertThatRemoveParamValueButtonIsDisabled() {
        navigateToStepsTab();
        screen.button(REMOVE_PARAM_VALUE_BUTTON).requireDisabled();
    }
    
    @Then("the remove param value button is enabled")
    public void assertThatRemoveParamValueButtonIsEnabled() {
        navigateToStepsTab();
        screen.button(REMOVE_PARAM_VALUE_BUTTON).requireEnabled();
    }
    
    @Then("the generate examples button is disabled")
    public void assertThatGenerateExamplesButtonIsDisabled() {
        navigateToExamplesTab();
        screen.button(GENERATE_EXAMPLES_BUTTON).requireDisabled();
    }
    
    @Then("the generate examples button is enabled")
    public void assertThatGenerateExamplesButtonIsEnabled() {
        navigateToExamplesTab();
        screen.button(GENERATE_EXAMPLES_BUTTON).requireEnabled();
    }
    
    @Then("the add example button is disabled")
    public void assertThatAddExampleButtonIsDisabled() {
        navigateToExamplesTab();
        screen.button(ADD_EXAMPLE_BUTTON).requireDisabled();
    }
    
    @Then("the add example button is enabled")
    public void assertThatAddExampleButtonIsEnabled() {
        navigateToExamplesTab();
        screen.button(ADD_EXAMPLE_BUTTON).requireEnabled();
    }
    
    @Then("the remove example button is disabled")
    public void assertThatRemoveExampleButtonIsDisabled() {
        navigateToExamplesTab();
        screen.button(REMOVE_EXAMPLE_BUTTON).requireDisabled();
    }
    
    @Then("the remove example button is enabled")
    public void assertThatRemoveExampleButtonIsEnabled() {
        navigateToExamplesTab();
        screen.button(REMOVE_EXAMPLE_BUTTON).requireEnabled();
    }
    
    @Then("the copy to clipboard button is disabled")
    public void assertThatCopyToClipboardButtonIsDisabled() {
        navigateToStoryTab();
        screen.button(COPY_TEXT_BUTTON).requireDisabled();
    }
    
    @Then("the copy to clipboard button is enabled")
    public void assertThatCopyToClipboardButtonIsEnabled() {
        navigateToStoryTab();
        screen.button(COPY_TEXT_BUTTON).requireEnabled();
    }
    
    @Then("the refresh story button is disabled")
    public void assertThatRefreshStoryButtonIsDisabled() {
        navigateToStoryTab();
        screen.button(REFRESH_STORY_BUTTON).requireDisabled();
    }
    
    @Then("the refresh story button is enabled")
    public void assertThatRefreshStoryButtonIsEnabled() {
        navigateToStoryTab();
        screen.button(REFRESH_STORY_BUTTON).requireEnabled();
    }
    
    @Then("the scenario combo box is disabled")
    public void assertThatScenarioComboBoxIsDisabled() {
        navigateToScenariosTab();
        screen.comboBox(SCENARIO_COMBO_BOX).requireDisabled();
    }
    
    @Then("the scenario combo box is enabled")
    public void assertThatScenarioComboBoxIsEnabled() {
        navigateToScenariosTab();
        screen.comboBox(SCENARIO_COMBO_BOX).requireEnabled();
    }
    
    @Then("the scenario combo box has selected item \"$name\"")
    public void assertThatComboBoxHasItem(String name) {
        navigateToScenariosTab();
        screen.comboBox(SCENARIO_COMBO_BOX).requireSelection(name);
    }
    
    @Then("the add scenario button is disabled")
    public void assertAddScenarioButtonIsDisabled() {
        navigateToScenariosTab();
        screen.button(ADD_SCENARIO_BUTTON).requireDisabled();
    }
    
    @Then("the add scenario button is enabled")
    public void assertAddScenarioButtonIsEnabled() {
        navigateToScenariosTab();
        screen.button(ADD_SCENARIO_BUTTON).requireEnabled();
    }
    
    @Then("I am warned that I am about to lose my work")
    public void assertThatWarningAppearsIfNewStorySelected() {
        JOptionPaneFixture op = screen.optionPane();
        op.requireQuestionMessage();
    }
    
    /*--------------*/

    private void navigateToStoryTab() {
        screen.tabbedPane(MAIN_TABS).selectTab(STORY_TAB_TITLE);
    }
    
    private void navigateToStepsTab() {
        navigateToScenariosTab();
        screen.tabbedPane(SCENARIO_TABS).selectTab(STEPS_TAB_TITLE);
    }
    
    private void navigateToExamplesTab() {
        navigateToScenariosTab();
        screen.tabbedPane(SCENARIO_TABS).selectTab(EXAMPLES_TAB_TITLE);
    }
    
    private void navigateToScenariosTab() {
        screen.tabbedPane(MAIN_TABS).selectTab(SCENARIOS_TAB_TITLE);
    }
    
    @AfterScenario
    public void cleanUp() {
        screen.cleanUp();
    }
}

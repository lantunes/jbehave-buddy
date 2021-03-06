package org.bigtesting.jbehave.buddy.tests.integration;

import java.io.File;

import org.bigtesting.jbehave.buddy.core.util.Resources;
import org.bigtesting.jbehave.buddy.standalone.StandaloneScreen;
import org.fest.assertions.Assertions;
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
    
    /*------Given----------*/
    
    @Given("a screen")
    public void createScreen() {
        StandaloneScreen s = GuiActionRunner.execute(new GuiQuery<StandaloneScreen>() {
            protected StandaloneScreen executeInEDT() {
                return new StandaloneScreen();
            }
        });
        screen = new FrameFixture(s.getFrame());
        screen.show();
    }
    
    /*-------When-----------*/
    
    @When("I type the steps: $steps")
    public void typeSteps(String steps) {
        JTextComponentFixture t = screen.textBox(Resources.TEXT_PANE);
        t.setText(steps);
    }
    
    @When("I select new story from the file menu")
    public void selectNewStoryFromFileMenu() {
        screen.menuItem(Resources.NEW_STORY_MENU_ITEM).click();
    }
    
    @When("I add a new scenario called \"$title\"")
    public void addNewScenario(String title) {
        navigateToScenariosTab();
        screen.button(Resources.ADD_SCENARIO_BUTTON).click();
        JOptionPaneFixture op = screen.optionPane();
        op.textBox().setText(title);
        op.okButton().click();
    }
    
    @When("I confirm that I want to create a new story")
    public void confirmCreateNewStory() {
        JOptionPaneFixture op = screen.optionPane();
        op.okButton().click();
    }
    
    @When("I confirm that I want to open an existing story")
    public void confirmOpenExistingStory() {
        JOptionPaneFixture op = screen.optionPane();
        op.okButton().click();
        selectExistingStory();
    }
    
    @When("I select an existing story")
    public void selectExistingStory() {
        screen.fileChooser()
            .setCurrentDirectory(defaultStoryDirectory())
            .selectFile(defaultStoryFile())
            .approve();
    }
    
    @When("I decline that I want to create a new story")
    public void declineCreateNewStory() {
        JOptionPaneFixture op = screen.optionPane();
        op.cancelButton().click();
    }
    
    @When("I decline that I want to open an existing story")
    public void declineOpenExistingStory() {
        JOptionPaneFixture op = screen.optionPane();
        op.cancelButton().click();
    }
    
    @When("I cancel opening an existing story")
    public void cancelOpenExistingStory() {
        screen.fileChooser().cancel();
    }
    
    @When("I decline to add a new scenario")
    public void declineAddNewScenario() {
        screen.button(Resources.ADD_SCENARIO_BUTTON).click();
        JOptionPaneFixture op = screen.optionPane();
        op.textBox().setText("Test");
        op.cancelButton().click();
    }
    
    @When("I edit the scenario description changing it to \"$title\"")
    public void editScenario(String title) {
        navigateToScenariosTab();
        screen.button(Resources.EDIT_SCENARIO_BUTTON).click();
        JOptionPaneFixture op = screen.optionPane();
        op.textBox().setText(title);
        op.okButton().click();
    }
    
    @When("I delete the scenario")
    public void deleteScenario() {
        navigateToScenariosTab();
        screen.button(Resources.DELETE_SCENARIO_BUTTON).click();
    }
    
    @When("I accept that I want to delete the scenario")
    public void acceptDeleteScenario() {
        JOptionPaneFixture op = screen.optionPane();
        op.requireQuestionMessage();
        op.okButton().click();
    } 
    
    @When("I decline that I want to delete the scenario")
    public void declineDeleteScenario() {
        JOptionPaneFixture op = screen.optionPane();
        op.requireQuestionMessage();
        op.cancelButton().click();
    }
    
    @When("I select open existing story from the file menu") 
    public void selectOpenExistingStoryFromFileMenu() {
        screen.menuItem(Resources.OPEN_EXISTING_STORY_MENU_ITEM).click();
    }
    
    /*------Then---------*/
    
    @Then("the steps editor is enabled")
    public void assertThatStepsEditorIsEnabled() {
        navigateToStepsTab();
        screen.textBox(Resources.TEXT_PANE).requireEnabled();
    }
    
    @Then("the steps editor is disabled")
    public void assertThatStepsEditorIsDisabled() {
        navigateToStepsTab();
        screen.textBox(Resources.TEXT_PANE).requireDisabled();
    }
    
    @Then("the add param value button is disabled")
    public void assertThatAddParamValueButtonIsDisabled() {
        navigateToStepsTab();
        screen.button(Resources.ADD_PARAM_VALUE_BUTTON).requireDisabled();
    }
    
    @Then("the add param value button is enabled")
    public void assertThatAddParamValueButtonIsEnabled() {
        navigateToStepsTab();
        screen.button(Resources.ADD_PARAM_VALUE_BUTTON).requireEnabled();
    }
    
    @Then("the remove param value button is disabled")
    public void assertThatRemoveParamValueButtonIsDisabled() {
        navigateToStepsTab();
        screen.button(Resources.REMOVE_PARAM_VALUE_BUTTON).requireDisabled();
    }
    
    @Then("the remove param value button is enabled")
    public void assertThatRemoveParamValueButtonIsEnabled() {
        navigateToStepsTab();
        screen.button(Resources.REMOVE_PARAM_VALUE_BUTTON).requireEnabled();
    }
    
    @Then("the generate examples button is disabled")
    public void assertThatGenerateExamplesButtonIsDisabled() {
        navigateToExamplesTab();
        screen.button(Resources.GENERATE_EXAMPLES_BUTTON).requireDisabled();
    }
    
    @Then("the generate examples button is enabled")
    public void assertThatGenerateExamplesButtonIsEnabled() {
        navigateToExamplesTab();
        screen.button(Resources.GENERATE_EXAMPLES_BUTTON).requireEnabled();
    }
    
    @Then("the add example button is disabled")
    public void assertThatAddExampleButtonIsDisabled() {
        navigateToExamplesTab();
        screen.button(Resources.ADD_EXAMPLE_BUTTON).requireDisabled();
    }
    
    @Then("the add example button is enabled")
    public void assertThatAddExampleButtonIsEnabled() {
        navigateToExamplesTab();
        screen.button(Resources.ADD_EXAMPLE_BUTTON).requireEnabled();
    }
    
    @Then("the remove example button is disabled")
    public void assertThatRemoveExampleButtonIsDisabled() {
        navigateToExamplesTab();
        screen.button(Resources.REMOVE_EXAMPLE_BUTTON).requireDisabled();
    }
    
    @Then("the remove example button is enabled")
    public void assertThatRemoveExampleButtonIsEnabled() {
        navigateToExamplesTab();
        screen.button(Resources.REMOVE_EXAMPLE_BUTTON).requireEnabled();
    }
    
    @Then("the copy to clipboard button is disabled")
    public void assertThatCopyToClipboardButtonIsDisabled() {
        navigateToStoryTab();
        screen.button(Resources.COPY_TEXT_BUTTON).requireDisabled();
    }
    
    @Then("the copy to clipboard button is enabled")
    public void assertThatCopyToClipboardButtonIsEnabled() {
        navigateToStoryTab();
        screen.button(Resources.COPY_TEXT_BUTTON).requireEnabled();
    }
    
    @Then("the refresh story button is disabled")
    public void assertThatRefreshStoryButtonIsDisabled() {
        navigateToStoryTab();
        screen.button(Resources.REFRESH_STORY_BUTTON).requireDisabled();
    }
    
    @Then("the refresh story button is enabled")
    public void assertThatRefreshStoryButtonIsEnabled() {
        navigateToStoryTab();
        screen.button(Resources.REFRESH_STORY_BUTTON).requireEnabled();
    }
    
    @Then("the scenario combo box is disabled")
    public void assertThatScenarioComboBoxIsDisabled() {
        navigateToScenariosTab();
        screen.comboBox(Resources.SCENARIO_COMBO_BOX).requireDisabled();
    }
    
    @Then("the scenario combo box is enabled")
    public void assertThatScenarioComboBoxIsEnabled() {
        navigateToScenariosTab();
        screen.comboBox(Resources.SCENARIO_COMBO_BOX).requireEnabled();
    }
    
    @Then("the scenario combo box has selected item \"$name\"")
    public void assertThatComboBoxHasItem(String name) {
        navigateToScenariosTab();
        screen.comboBox(Resources.SCENARIO_COMBO_BOX).requireSelection(name);
    }
    
    @Then("the add scenario button is disabled")
    public void assertAddScenarioButtonIsDisabled() {
        navigateToScenariosTab();
        screen.button(Resources.ADD_SCENARIO_BUTTON).requireDisabled();
    }
    
    @Then("the add scenario button is enabled")
    public void assertAddScenarioButtonIsEnabled() {
        navigateToScenariosTab();
        screen.button(Resources.ADD_SCENARIO_BUTTON).requireEnabled();
    }
    
    @Then("the edit scenario button is disabled")
    public void assertEditScenarioButtonIsDisabled() {
        navigateToScenariosTab();
        screen.button(Resources.EDIT_SCENARIO_BUTTON).requireDisabled();
    }
    
    @Then("the edit scenario button is enabled")
    public void assertEditScenarioButtonIsEnabled() {
        navigateToScenariosTab();
        screen.button(Resources.EDIT_SCENARIO_BUTTON).requireEnabled();
    }
    
    @Then("the delete scenario button is disabled")
    public void assertDeleteScenarioButtonIsDisabled() {
        navigateToScenariosTab();
        screen.button(Resources.DELETE_SCENARIO_BUTTON).requireDisabled();
    }
    
    @Then("the delete scenario button is enabled")
    public void assertDeleteScenarioButtonIsEnabled() {
        navigateToScenariosTab();
        screen.button(Resources.DELETE_SCENARIO_BUTTON).requireEnabled();
    }
    
    @Then("I am warned that I am about to lose my work")
    public void assertThatWarningAppearsIfNewStorySelected() {
        JOptionPaneFixture op = screen.optionPane();
        op.requireQuestionMessage();
    }
    
    @Then("I am warned that the scenario description already exists")
    public void assertThatWarningAppearsIfExistingDescriptionEntered() {
        JOptionPaneFixture op = screen.optionPane();
        op.requireErrorMessage();
        op.okButton().click();
    }
    
    @Then("the scenario combo box does not contain item \"$name\"")
    public void assertThatScenarioComboBoxDoesNotContainItem(String name) {
        navigateToScenariosTab();
        Assertions.assertThat(screen.comboBox(Resources.SCENARIO_COMBO_BOX).contents()).excludes(name);
    }
    
    /*--------------*/

    private void navigateToStoryTab() {
        screen.tabbedPane(Resources.MAIN_TABS).selectTab(Resources.STORY_TAB_TITLE);
    }
    
    private void navigateToStepsTab() {
        navigateToScenariosTab();
        screen.tabbedPane(Resources.SCENARIO_TABS).selectTab(Resources.STEPS_TAB_TITLE);
    }
    
    private void navigateToExamplesTab() {
        navigateToScenariosTab();
        screen.tabbedPane(Resources.SCENARIO_TABS).selectTab(Resources.EXAMPLES_TAB_TITLE);
    }
    
    private void navigateToScenariosTab() {
        screen.tabbedPane(Resources.MAIN_TABS).selectTab(Resources.SCENARIOS_TAB_TITLE);
    }
    
    private File defaultStoryFile() {
        return new File("default.story");
    }
    
    private File defaultStoryDirectory() {
        return new File("src/test/resources");
    }
    
    @AfterScenario
    public void cleanUp() {
        screen.cleanUp();
    }
}

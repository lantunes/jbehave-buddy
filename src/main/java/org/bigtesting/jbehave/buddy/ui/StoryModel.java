package org.bigtesting.jbehave.buddy.ui;

import javax.swing.DefaultComboBoxModel;

public class StoryModel {
    
    private DefaultComboBoxModel scenarioModels = new DefaultComboBoxModel();

    public void addScenario(String description, IScreen screen) {
        ScenarioModel model = new ScenarioModel(description, screen);
        scenarioModels.addElement(model);
        scenarioModels.setSelectedItem(model);
    }
    
    public void addExistingScenario(ScenarioModel model) {
        scenarioModels.addElement(model);
    }
    
    public DefaultComboBoxModel getComboBoxModel() {
        return scenarioModels;
    }
    
    public boolean hasScenarios() {
        return scenarioModels.getSize() > 0;
    }
    
    public boolean hasScenarioWithDescription(String description) {
        for (int i = 0; i < scenarioModels.getSize(); i++) {
            ScenarioModel model = (ScenarioModel)scenarioModels.getElementAt(i);
            if (model.getDescription().equals(description)) {
                return true;
            }
        }
        return false;
    }
    
    public ScenarioModel getSelectedScenario() {
        return (ScenarioModel)scenarioModels.getSelectedItem();
    }
    
    public void deleteSelectedScenario() {
        scenarioModels.removeElement(scenarioModels.getSelectedItem());
    }
    
    public void selectFirstScenario() {
        if (hasScenarios()) {
            scenarioModels.setSelectedItem(scenarioModels.getElementAt(0));
        }
    }
    
    public String print() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < scenarioModels.getSize(); i++) {
            ScenarioModel model = (ScenarioModel)scenarioModels.getElementAt(i);
            sb.append("Scenario: ").append(model.getDescription()).append("\n\n");
            sb.append(model.print()).append("\n\n\n");
        }
        return sb.toString();
    }
}

package org.bigtesting.jbehave.buddy.core.ui;

import javax.swing.DefaultComboBoxModel;

public class StoryModel {

    private String meta;

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

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getMeta() {
        return meta == null ? "" : meta;
    }

    public String print() {
        StringBuilder sb = new StringBuilder();
        if (meta != null && meta.trim().length() > 0) {
            sb.append("Meta: ").append("\n").append(meta).append("\n\n");
        }
        for (int i = 0; i < scenarioModels.getSize(); i++) {
            ScenarioModel model = (ScenarioModel)scenarioModels.getElementAt(i);
            sb.append("Scenario: ").append(model.getDescription()).append("\n");
            if (model.getMeta() != null && model.getMeta().trim().length() > 0) {
                sb.append("Meta: ").append("\n").append(model.getMeta()).append("\n");
            }
            sb.append("\n");
            sb.append(model.print()).append("\n\n\n");
        }
        return sb.toString();
    }
}

package org.bigtesting.jbehave.buddy.core.ui;

import javax.swing.DefaultListModel;
import javax.swing.JTextPane;
import javax.swing.ListModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import org.apache.commons.lang.StringUtils;
import org.bigtesting.jbehave.buddy.core.ui.editor.StepsEditorModel;
import org.bigtesting.jbehave.buddy.core.ui.widgets.ExamplesTableModel;
import org.bigtesting.jbehave.buddy.core.ui.widgets.ParameterValuesListModel;
import org.bigtesting.jbehave.buddy.core.ui.widgets.SwingStepsDocument;
import org.bigtesting.jbehave.buddy.core.util.ExamplesFormatter;

public class ScenarioModel {

    private String description;
    private String meta;

    private ScenarioParameters params;
    private ExamplesGenerator examplesGenerator;
    
    private JTextPane stepsTextPane;
    private StepsEditorModel stepsModel;
    private ParameterValuesListModel paramValuesListModel;
    private DefaultListModel paramsListModel;
    private ExamplesTableModel examplesTableModel;
    
    public ScenarioModel(String description, final IScreen screen) {
        
        this.description = description;
        params = new ScenarioParameters();
        stepsTextPane = screen.newStepsTextPane();
        ScreenContext ctx = screen.getScreenContext();
        stepsModel = new StepsEditorModel(new SwingStepsDocument(stepsTextPane.getStyledDocument(), ctx), ctx);
        stepsModel.addParametersListener(params);
        examplesGenerator = new ExamplesGenerator(params);
        paramValuesListModel = new ParameterValuesListModel(params);
        paramsListModel = new DefaultListModel();
        examplesTableModel = new ExamplesTableModel();
        examplesTableModel.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                screen.examplesTableChanged(e);
            }
        });
    }

    public JTextPane getStepsTextPane() {
        return stepsTextPane;
    }
    
    public void setSteps(String steps) {
        stepsModel.setSteps(steps);
        handleStepsTextEdit();
    }
    
    public String print() {
        StringBuilder sb = new StringBuilder(stepsModel.getText());
        if (examplesTableModel.getRowCount() > 0) {
            sb.append("\n\nExamples:\n");
            sb.append(ExamplesFormatter.format(examplesTableModel.getCurrentExamples()));
        }
        return sb.toString();
    }
    
    public void generateExamples() {
        String[][] examples = examplesGenerator.generateExamples();
        setExamples(examples);
    }
    
    public void setExamples(String[][] examples) {
        if (examples != null && examples.length != 0) {
            examplesTableModel.clear();
            examplesTableModel.setData(examples);
        }
    }
    
    public int numExamples() {
        return examplesGenerator.numExamples();
    }
    
    public void removeExampleRows(int[] rows) {
        examplesTableModel.removeRows(rows);
    }
    
    public void setValuesFor(String param) {
        paramValuesListModel.setValuesFor(param);
    }
    
    public void removeParamValue(String selectedParam, String selectedValue) {
        paramValuesListModel.removeValue(selectedParam, selectedValue);
    }
    
    public void clearParameterValues() {
        paramValuesListModel.clear();
    }
    
    public void addParamValue(String selectedParam, String value) {
        paramValuesListModel.addValue(selectedParam, value);
    }
    
    public String[] getActiveParameters() {
        return params.getActiveParameters();
    }
    
    public void handleStepsTextEdit() {
        stepsModel.handleTextEdit();
        paramsListModel.clear();
        String[] params = getActiveParameters();
        for (String param : params) {
            paramsListModel.addElement(param);
        }
    }
    
    public ListModel getParamValuesListModel() {
        return paramValuesListModel;
    }
    
    public ExamplesTableModel getExamplesTableModel() {
        return examplesTableModel;
    }
    
    public ListModel getParametersListModel() {
        return paramsListModel;
    }
    
    public void addExample() {
        examplesTableModel.addNewRow();
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    public void setMeta(String meta) {
        this.meta = meta;
    }

    public String getMeta() {
        return meta == null ? "" : meta;
    }

    public String toString() {
        String description = getDescription().replaceAll("\\r", "").replaceAll("\\n", "").trim();
        return StringUtils.abbreviate(description, 95);
    }
}

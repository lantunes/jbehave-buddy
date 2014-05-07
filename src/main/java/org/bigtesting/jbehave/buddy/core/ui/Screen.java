package org.bigtesting.jbehave.buddy.core.ui;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

import org.apache.commons.io.FileUtils;
import org.bigtesting.jbehave.buddy.core.ui.widgets.ListAction;
import org.bigtesting.jbehave.buddy.core.ui.widgets.ParamValuesEditListAction;
import org.bigtesting.jbehave.buddy.core.ui.widgets.StepsTextPane;
import org.bigtesting.jbehave.buddy.core.util.Resources;

public class Screen implements IScreen {

    private JPanel mainPanel;
    private JTabbedPane mainTabs;
    private JPanel scenariosTabPanel;
    private JLabel scenarioLabel;
    private JComboBox scenarioComboBox;
    private JButton addScenarioButton;
    private JTabbedPane scenarioTabs;
    private JPanel stepsTabPanel;
    private JScrollPane stepsScrollPane;
    private JPanel parametersPanel;
    private JScrollPane parametersScrollPane;
    private JPanel parameterValuesPanel;
    private JScrollPane parameterValuesScrollPane;
    private JButton addParamValueButton;
    private JButton removeParamValueButton;
    private JPanel examplesTabPanel;
    private JButton generateExamplesButton;
    private JButton addExampleButton;
    private JButton removeExampleButton;
    private JScrollPane examplesScrollPane;
    private JTable examplesTable;
    private JList parametersList;
    private JList parameterValuesList;
    private JLabel numExamplesLabel;
    private JPanel storyTabPanel;
    private JScrollPane storyScrollPane;
    private JTextArea storyTextArea;
    private JButton copyTextButton;
    private JButton refreshStoryButton;
    private JButton okButton;
    private JButton cancelButton;
    private JButton editScenarioButton;
    private JButton deleteScenarioButton;
    private ParamValuesEditListAction editListAction;
    private JTextField storyMetaTextField;
    private JTextField scenarioMetaTextField;

    private StoryModel storyModel;
    
    /*
     * a story file that comes in through the constructor 
     */
    private File existingStoryFile;

    private String existingStoryInitialContent;
    
    /*
     * a story file that is opened in the application 
     */
    private File openedStoryFile;
    
    private final ScreenContext screenContext;
    
    public Screen(ScreenContext screenContext) {
        this(null, screenContext);
    }
    
    public Screen(File existingStoryFile, ScreenContext screenContext) {
        this.existingStoryFile = existingStoryFile;
        this.screenContext = screenContext;
        initialize();
    }
    
    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void initialize() {
        
        initMainControls();
        
        initScenariosTabPanel();
        createTopLevelScenariosControls();

        initScenarioTabs();
        createStepsEditorControls();

        createParametersControls();
        createParameterValuesControls();
        
        createExamplesTab();

        createStoryTab();

        if (hasExistingStoryFile()) {
            initExistingStory();
        }
    }

    private void createTopLevelScenariosControls() {
        initScenarioLabel();
        initScenarioComboBox();
        initAddScenarioButton();
        initEditScenarioButton();
        initDeleteScenarioButton();
        initScenarioMetaControls();
    }

    private void initScenarioMetaControls() {
        JLabel scenarioMetaLabel = new JLabel("Meta:");
        scenarioMetaTextField = new JTextField();
        scenarioMetaTextField.setEnabled(false);
        scenarioMetaTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { setScenarioMetaOnScenarioModel(); }
            public void removeUpdate(DocumentEvent e) { setScenarioMetaOnScenarioModel(); }
            public void changedUpdate(DocumentEvent e) { setScenarioMetaOnScenarioModel(); }
        });
        JPanel scenarioMetaPanel = new JPanel();
        scenarioMetaPanel.setLayout(new MigLayout("", "[][grow]", "[]"));
        scenarioMetaPanel.add(scenarioMetaLabel, "cell 0 0,alignx left");
        scenarioMetaPanel.add(scenarioMetaTextField, "cell 1 0,alignx left, aligny top, growx");
        scenariosTabPanel.add(scenarioMetaPanel, "cell 0 1 5 1, aligny top, grow");
    }

    private void setScenarioMetaOnScenarioModel() {
        storyModel.getSelectedScenario().setMeta(scenarioMetaTextField.getText());
    }

    private void createStepsEditorControls() {
        initStepsTabPanel();
        initStepsScrollPane();
    }

    private void createStoryTab() {
        initStoryTabPanel();
        initStoryScrollPane();
        initStoryTextArea();
        initCopyTextButton();
        initRefreshStoryButton();
    }

    private void createExamplesTab() {
        initExamplesTabPanel();
        initGenerateExamplesButton();
        initExamplesScrollPane();
        initExamplesTable();
        initNumExamplesLabel();
        initAddExampleButton();
        initRemoveExampleButton();
    }

    private void createParameterValuesControls() {
        initParameterValuesPanel();
        initParameterValuesScrollPane();
        initParameterValuesList();
        initAddParamValueButton();
        initRemoveParamValueButton();
    }

    private void createParametersControls() {
        initParametersPanel();
        initParametersScrollPane();
        initParametersList();
    }

    private void initMainControls() {
        initMainPanel();
        initStoryMetaControls();
        initMainTabs();
    }

    private void initStoryMetaControls() {
        JLabel storyMetaLabel = new JLabel("Story Meta:");
        storyMetaTextField = new JTextField();
        storyMetaTextField.setEnabled(false);
        storyMetaTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { setStoryMetaOnStoryModel(); }
            public void removeUpdate(DocumentEvent e) { setStoryMetaOnStoryModel(); }
            public void changedUpdate(DocumentEvent e) { setStoryMetaOnStoryModel(); }
        });
        JPanel storyMetaPanel = new JPanel();
        storyMetaPanel.setLayout(new MigLayout("", "[][grow]", "[]"));
        storyMetaPanel.add(storyMetaLabel, "cell 0 0,alignx left");
        storyMetaPanel.add(storyMetaTextField, "cell 1 0,alignx left, aligny top, growx");
        mainPanel.add(storyMetaPanel, "cell 0 0, aligny top, grow");
    }

    private void setStoryMetaOnStoryModel() {
        storyModel.setMeta(storyMetaTextField.getText());
    }

    private void initExistingStory() {
        initOKButton();
        initCancelButton();
        importStory(existingStoryFile);
        existingStoryInitialContent = storyModel.print();
        screenContext.setTitle(Resources.TITLE + " - " + existingStoryFile.getName());
    }
    
    private void initOKButton() {
        if (!screenContext.isDialog()) return;
        okButton = new JButton("OK");
        okButton.setName(Resources.OK_BUTTON);
        okButton.setEnabled(true);
        mainPanel.add(okButton, "flowx,cell 0 1,alignx right,aligny bottom");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ok();
            }
        });
    }

    public void ok() {
        new StoryExporter().export(storyModel);
        screenContext.close();
    }
    
    private void initCancelButton() {
        if (!screenContext.isDialog()) return;
        cancelButton = new JButton("Cancel");
        cancelButton.setName(Resources.CANCEL_BUTTON);
        cancelButton.setEnabled(true);
        mainPanel.add(cancelButton, "cell 0 1,alignx right,aligny bottom");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                cancel();
            }
        });
    }

    public void cancel() {
        screenContext.close();
    }

    private void importStory(File storyFile) {
        try {
            storyModel = new StoryImporter().importStory(storyFile, this);
        } catch (Exception e) {
            screenContext.logException(e);
            JOptionPane.showMessageDialog(mainPanel, "there was an error parsing the story file: " + e.getMessage());
        }
        
        if (storyModel != null && storyModel.hasScenarios()) {
            prepareUIForExistingStory();
        } else {
            prepareUIForNewStory();
        }
    }
    
    private void initRefreshStoryButton() {
        refreshStoryButton = new JButton("Refresh");
        refreshStoryButton.setName(Resources.REFRESH_STORY_BUTTON);
        refreshStoryButton.setEnabled(false);
        storyTabPanel.add(refreshStoryButton, "cell 0 1,alignx right,aligny bottom");
        refreshStoryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        refreshStory();
                    }
                });
            }
        });
    }

    private void initCopyTextButton() {
        copyTextButton = new JButton("Copy to clipboard");
        copyTextButton.setName(Resources.COPY_TEXT_BUTTON);
        copyTextButton.setEnabled(false);
        storyTabPanel.add(copyTextButton, "flowx,cell 0 1,alignx right,aligny bottom");
        copyTextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                copyToClipboard();
            }
        });
    }

    private void initStoryTextArea() {
        storyTextArea = new JTextArea();
        storyTextArea.setName(Resources.STORY_TEXT_AREA);
        storyTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        storyTextArea.setEditable(false);
        storyScrollPane.setViewportView(storyTextArea);
    }

    private void initStoryScrollPane() {
        storyScrollPane = new JScrollPane();
        storyScrollPane.setName(Resources.STORY_SCROLL_PANE);
        storyTabPanel.add(storyScrollPane, "cell 0 0,grow");
    }

    private void initStoryTabPanel() {
        storyTabPanel = new JPanel();
        storyTabPanel.setName(Resources.STORY_TAB_PANEL);
        mainTabs.addTab(Resources.STORY_TAB_TITLE, null, storyTabPanel, null);
        storyTabPanel.setLayout(new MigLayout("", "[grow]", "[grow][]"));
    }

    private void initRemoveExampleButton() {
        removeExampleButton = new JButton("Remove");
        removeExampleButton.setName(Resources.REMOVE_EXAMPLE_BUTTON);
        removeExampleButton.setEnabled(false);
        examplesTabPanel.add(removeExampleButton, "cell 1 2,alignx right,aligny bottom");
        removeExampleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeExample();
            }
        });
    }

    private void initAddExampleButton() {
        addExampleButton = new JButton("Add");
        addExampleButton.setName(Resources.ADD_EXAMPLE_BUTTON);
        addExampleButton.setEnabled(false);
        examplesTabPanel.add(addExampleButton, "flowx,cell 1 2,alignx right,aligny bottom");
        addExampleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                storyModel.getSelectedScenario().addExample();
            }
        });
    }

    private void initNumExamplesLabel() {
        numExamplesLabel = new JLabel(" ");
        numExamplesLabel.setName(Resources.NUM_EXAMPLES_LABEL);
        examplesTabPanel.add(numExamplesLabel, "cell 0 2,alignx right,aligny bottom");
    }

    private void initExamplesTable() {
        examplesTable = new JTable();
        examplesTable.setName(Resources.EXAMPLES_TABLE);
        disableTable(examplesTable);
        examplesScrollPane.setViewportView(examplesTable);
    }

    private void initParameterValuesPanel() {
        parameterValuesPanel = new JPanel();
        parameterValuesPanel.setName(Resources.PARAMETER_VALUES_PANEL);
        parameterValuesPanel.setBorder(new TitledBorder(null, "Parameter Values", TitledBorder.LEADING,
                TitledBorder.TOP, null, null));
        stepsTabPanel.add(parameterValuesPanel, "cell 1 1,grow");
        parameterValuesPanel.setLayout(new MigLayout("", "[grow]", "[grow][]"));
    }

    private void initParameterValuesScrollPane() {
        parameterValuesScrollPane = new JScrollPane();
        parameterValuesScrollPane.setName(Resources.PARAMETER_VALUES_SCROLL_PANE);
        parameterValuesPanel.add(parameterValuesScrollPane, "cell 0 0,grow");
    }

    private void initParameterValuesList() {
        parameterValuesList = new JList();
        parameterValuesList.setName(Resources.PARAMETER_VALUES_LIST);
        disableList(parameterValuesList);
        editListAction = new ParamValuesEditListAction();
        new ListAction(parameterValuesList, editListAction);
        parameterValuesScrollPane.setViewportView(parameterValuesList);
        parameterValuesList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                parameterValueSelectionChanged();
            }
        });
    }

    private void initAddParamValueButton() {
        addParamValueButton = new JButton("Add");
        addParamValueButton.setName(Resources.ADD_PARAM_VALUE_BUTTON);
        addParamValueButton.setEnabled(false);
        parameterValuesPanel.add(addParamValueButton, "flowx,cell 0 1,alignx right,aligny bottom");
        addParamValueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addParamValue();
            }
        });
    }

    private void initRemoveParamValueButton() {
        removeParamValueButton = new JButton("Remove");
        removeParamValueButton.setName(Resources.REMOVE_PARAM_VALUE_BUTTON);
        removeParamValueButton.setEnabled(false);
        parameterValuesPanel.add(removeParamValueButton, "cell 0 1,alignx right,aligny bottom");
        removeParamValueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeParamValue();
            }
        });
    }

    private void initExamplesTabPanel() {
        examplesTabPanel = new JPanel();
        examplesTabPanel.setName(Resources.EXAMPLES_TAB_PANEL);
        scenarioTabs.addTab(Resources.EXAMPLES_TAB_TITLE, null, examplesTabPanel, null);
        examplesTabPanel.setLayout(new MigLayout("", "[][grow]", "[][grow][]"));
    }

    private void initExamplesScrollPane() {
        examplesScrollPane = new JScrollPane();
        examplesScrollPane.setName(Resources.EXAMPLES_SCROLL_PANE);
        examplesTabPanel.add(examplesScrollPane, "cell 0 1 2 1,grow");
    }

    private void initGenerateExamplesButton() {
        generateExamplesButton = new JButton("Generate examples");
        generateExamplesButton.setName(Resources.GENERATE_EXAMPLES_BUTTON);
        generateExamplesButton.setEnabled(false);
        examplesTabPanel.add(generateExamplesButton, "cell 0 0 2 1");
        generateExamplesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        generateExamples();
                    }
                });
            }
        });
    }

    private void initParametersList() {
        parametersList = new JList();
        parametersList.setName(Resources.PARAMETERS_LIST);
        disableList(parametersList);
        parametersScrollPane.setViewportView(parametersList);
        parametersList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                parameterSelectionChanged();
            }
        });
    }

    private void initParametersScrollPane() {
        parametersScrollPane = new JScrollPane();
        parametersScrollPane.setName(Resources.PARAMETERS_SCROLL_PANE);
        parametersPanel.add(parametersScrollPane, "cell 0 0,grow");
    }

    private void initParametersPanel() {
        parametersPanel = new JPanel();
        parametersPanel.setName(Resources.PARAMETERS_PANEL);
        parametersPanel.setBorder(new TitledBorder(null, "Parameters", TitledBorder.LEADING, TitledBorder.TOP, null,
                null));
        stepsTabPanel.add(parametersPanel, "cell 1 0,grow");
        parametersPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
    }

    public JTextPane newStepsTextPane() {
        JTextPane textPane = new JTextPane();
        textPane.setName(Resources.TEXT_PANE);
        textPane.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textPane.setBackground(Color.WHITE);
        textPane.getStyledDocument().addDocumentListener(new DocumentListener() {
            public void removeUpdate(DocumentEvent evt) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        stepsTextEdited();
                    }
                });
            }

            public void insertUpdate(DocumentEvent evt) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        stepsTextEdited();
                    }
                });
            }

            public void changedUpdate(DocumentEvent evt) {
            }
        });
        return textPane;
    }

    private void initStepsScrollPane() {
        stepsScrollPane = new JScrollPane();
        stepsScrollPane.setName(Resources.STEPS_SCROLL_PANE);
        stepsTabPanel.add(stepsScrollPane, "cell 0 0 1 2,grow");
    }

    private void initStepsTabPanel() {
        stepsTabPanel = new JPanel();
        stepsTabPanel.setName(Resources.STEPS_TAB_PANEL);
        scenarioTabs.addTab(Resources.STEPS_TAB_TITLE, null, stepsTabPanel, null);
        stepsTabPanel.setLayout(new MigLayout("", "[360.00,grow][300px:300px:300px,right]", "[grow][grow]"));
    }

    private void initScenarioTabs() {
        scenarioTabs = new JTabbedPane(JTabbedPane.TOP);
        scenarioTabs.setName(Resources.SCENARIO_TABS);
        scenariosTabPanel.add(scenarioTabs, "cell 0 2 5 1,grow");
    }

    private void initAddScenarioButton() {
        addScenarioButton = new JButton("Add");
        addScenarioButton.setName(Resources.ADD_SCENARIO_BUTTON);
        addScenarioButton.setEnabled(false);
        scenariosTabPanel.add(addScenarioButton, "cell 2 0");
        addScenarioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewScenario();
            }
        });
    }

    private void initEditScenarioButton() {
        editScenarioButton = new JButton("Edit");
        editScenarioButton.setName(Resources.EDIT_SCENARIO_BUTTON);
        editScenarioButton.setEnabled(false);
        scenariosTabPanel.add(editScenarioButton, "cell 3 0");
        editScenarioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editScenario();
            }
        });
    }
    
    private void initDeleteScenarioButton() {
        deleteScenarioButton = new JButton("Delete");
        deleteScenarioButton.setName(Resources.DELETE_SCENARIO_BUTTON);
        deleteScenarioButton.setEnabled(false);
        scenariosTabPanel.add(deleteScenarioButton, "cell 4 0");
        deleteScenarioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteScenario();
            }
        });
    }
    
    private void initScenarioComboBox() {
        scenarioComboBox = new JComboBox();
        scenarioComboBox.setName(Resources.SCENARIO_COMBO_BOX);
        scenarioComboBox.setEnabled(false);
        scenariosTabPanel.add(scenarioComboBox, "cell 1 0,growx");
        scenarioComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scenarioChanged();
            }
        });
    }

    private void initScenarioLabel() {
        scenarioLabel = new JLabel("Scenario:");
        scenarioLabel.setName(Resources.SCENARIO_LABEL);
        scenariosTabPanel.add(scenarioLabel, "cell 0 0,alignx left");
    }
    
    private void removeParamValue() {
        String selectedParam = (String) parametersList.getSelectedValue();
        String selectedValue = (String) parameterValuesList.getSelectedValue();
        if (selectedParam != null && selectedValue != null) {
            storyModel.getSelectedScenario().removeParamValue(selectedParam, selectedValue);
        }
    }

    private JPanel initScenariosTabPanel() {
        scenariosTabPanel = new JPanel();
        scenariosTabPanel.setName(Resources.SCENARIOS_TAB_PANEL);
        mainTabs.addTab(Resources.SCENARIOS_TAB_TITLE, null, scenariosTabPanel, null);
        scenariosTabPanel.setLayout(new MigLayout("", "[][grow][]", "[][][grow]"));
        return scenariosTabPanel;
    }

    private JTabbedPane initMainTabs() {
        mainTabs = new JTabbedPane(JTabbedPane.TOP);
        mainTabs.setName(Resources.MAIN_TABS);
        mainPanel.add(mainTabs, "cell 0 1,grow");
        return mainTabs;
    }

    private JPanel initMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setName(Resources.MAIN_PANEL);
        mainPanel.setLayout(new MigLayout("", "[grow]", "[][grow]"));
        return mainPanel;
    }

    private void stepsTextEdited() {
        if (storyModel != null && storyModel.getSelectedScenario() != null) {
            storyModel.getSelectedScenario().handleStepsTextEdit();
        }
    }

    private void addParamValue() {
        String selectedParam = (String) parametersList.getSelectedValue();
        if (selectedParam != null) {
            String value = JOptionPane.showInputDialog(mainPanel, "Enter value:");
            if (value != null && value.trim().length() != 0) {
                storyModel.getSelectedScenario().addParamValue(selectedParam, value);
            }
        }
    }

    private void parameterSelectionChanged() {
        String param = (String) parametersList.getSelectedValue();
        if (param != null) {
            storyModel.getSelectedScenario().setValuesFor(param);
            editListAction.setCurrentParam(param);
            addParamValueButton.setEnabled(true);
        } else {
            if (storyModel.hasScenarios()) {
                storyModel.getSelectedScenario().clearParameterValues();
            }
            addParamValueButton.setEnabled(false);
        }
    }
    
    private void parameterValueSelectionChanged() {
        removeParamValueButton.setEnabled(parameterValuesList.getSelectedValue() != null);
    }

    private void generateExamples() {
        int numExamples = storyModel.getSelectedScenario().numExamples();
        if (numExamples > 50) {
            int response = JOptionPane.showConfirmDialog(mainPanel, "There are " + 
                    numExamples + " examples. Do you wish to continue?");
            if (response != JOptionPane.YES_OPTION) {
                return;
            }
        }
        storyModel.getSelectedScenario().generateExamples();
        
        enableOrDisableExamplesTableAddRemoveButtons();
    }

    private void copyToClipboard() {
        StringSelection data = new StringSelection(storyTextArea.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(data, data);
    }

    private void refreshStory() {
        storyTextArea.setText(storyModel.print());
    }

    private void removeExample() {
        int[] rows = examplesTable.getSelectedRows();
        if (rows.length > 0) {
            storyModel.getSelectedScenario().removeExampleRows(rows);
        }
    }

    public void examplesTableChanged(TableModelEvent e) {
        int eventType = e.getType();
        if (eventType == TableModelEvent.INSERT || eventType == TableModelEvent.DELETE) {
            updateExamplesTableExamplesCount();
        }
    }

    private void updateExamplesTableExamplesCount() {
        int rowCount = examplesTable.getModel().getRowCount();
        String example = "example";
        numExamplesLabel.setText(rowCount == 0 ? "" : rowCount + " " + (rowCount == 1 ? example : example+"s"));
    }
    
    private void addNewScenario() {
        String description = JOptionPane.showInputDialog(mainPanel, "Description: ");
        if (description == null || description.trim().length() == 0) {
            return;
        }
        
        parametersList.clearSelection();
        storyModel.addScenario(description, this);
        enableControls(true);
    }
    
    private void editScenario() {
        String description = JOptionPane.showInputDialog(mainPanel, "Description: ", 
                storyModel.getSelectedScenario().getDescription());
        if (description == null || description.trim().length() == 0) {
            return;
        }
        if (storyModel.hasScenarioWithDescription(description)) {
            JOptionPane.showMessageDialog(mainPanel, 
                    "Scenario with that description already exists.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        storyModel.getSelectedScenario().setDescription(description);
        scenarioComboBox.repaint();
    }
    
    private void deleteScenario() {
        int response = JOptionPane.showConfirmDialog(mainPanel, 
                "Deleting the selected scenario is irreversible. Do you wish to continue?", 
                "Deleting Scenario",  JOptionPane.WARNING_MESSAGE);
        if (response != JOptionPane.YES_OPTION) {
            return;
        }
        storyModel.deleteSelectedScenario();
        if (storyModel.hasScenarios()) {
            storyModel.selectFirstScenario();
        } else {
            prepareUIForNewStory();
        }
    }
    
    private void scenarioChanged() {
        ScenarioModel selectedScenario = storyModel.getSelectedScenario();
        if (selectedScenario != null) {
            scenarioMetaTextField.setText(selectedScenario.getMeta());
            parameterValuesList.setModel(selectedScenario.getParamValuesListModel());
            parametersList.setModel(selectedScenario.getParametersListModel());
            examplesTable.setModel(selectedScenario.getExamplesTableModel());
            enableOrDisableExamplesTableAddRemoveButtons();
            updateExamplesTableExamplesCount();
            JTextPane textPane = selectedScenario.getStepsTextPane();
            stepsScrollPane.setViewportView(textPane);
            stepsScrollPane.setRowHeaderView(new StepsTextPane(textPane, screenContext));
            selectedScenario.clearParameterValues();
        }
    }
    
    public void newStory() {
        if (!replaceCurrentStory()) {
            return;
        }
        prepareUIForNewStory();
        setOpenedStoryFile(null);
    }
    
    public void openExistingStory() {
        if (!replaceCurrentStory()) {
            return;
        }
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showOpenDialog(mainPanel);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            setOpenedStoryFile(fc.getSelectedFile());
            importStory(openedStoryFile);
        }
    }
    
    private boolean replaceCurrentStory() {
        if (storyModel != null) {
            int result = JOptionPane.showConfirmDialog(mainPanel, 
                    "All scenarios in the current story will be removed", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
                return false;
            }
        }
        return true;
    }
    
    public void saveStory() {
        if (openedStoryFile == null) {
            JFileChooser chooser = new JFileChooser();
            chooser.showSaveDialog(mainPanel);
            setOpenedStoryFile(chooser.getSelectedFile());
        }
        if (openedStoryFile != null) {
            exportStoryModelToFile(openedStoryFile);
        }
    }

    public void saveExistingStoryFile() {
        if (existingStoryFile != null) {
            exportStoryModelToFile(existingStoryFile);
        }
    }

    public boolean isExistingStoryChanged() {
        if (existingStoryFile != null) {
            if (!existingStoryFile.exists()) {
                throw new RuntimeException("The existing story file " + existingStoryFile + " does not exist");
            }
            String currentStory = storyModel.print();
            return !currentStory.equals(existingStoryInitialContent);
        }
        return false;
    }

    private void exportStoryModelToFile(File file) {
        try {
            new StoryExporter().exportToFile(file, storyModel);
        } catch (Exception e) {
            screenContext.logException(e);
            JOptionPane.showMessageDialog(mainPanel, "There was an error saving.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setOpenedStoryFile(File file) {
        openedStoryFile = file;
        screenContext.setTitle(file != null ? Resources.TITLE + " - " + file.getAbsolutePath() : Resources.TITLE);
    }
    
    private void prepareUIForNewStory() {
        storyModel = new StoryModel();
        scenarioComboBox.setModel(storyModel.getComboBoxModel());
        addScenarioButton.setEnabled(true);
        storyTextArea.setText("");
        storyMetaTextField.setText("");
        storyMetaTextField.setEnabled(true);
        scenarioMetaTextField.setText("");
        enableControls(false);
        enableOrDisableSaving();
    }
    
    private void enableOrDisableSaving() {
        screenContext.enableSaving(storyModel != null && !hasExistingStoryFile());
    }
    
    private void prepareUIForExistingStory() {
        scenarioComboBox.setModel(storyModel.getComboBoxModel());
        addScenarioButton.setEnabled(true);
        storyTextArea.setText("");
        storyMetaTextField.setEnabled(true);
        storyMetaTextField.setText(storyModel.getMeta());
        enableControls(true);
        enableOrDisableSaving();
        scenarioChanged();
    }
    
    private void enableControls(boolean enabled) {
        scenarioComboBox.setEnabled(enabled);
        editScenarioButton.setEnabled(enabled);
        deleteScenarioButton.setEnabled(enabled);
        generateExamplesButton.setEnabled(enabled);
        copyTextButton.setEnabled(enabled);
        refreshStoryButton.setEnabled(enabled);
        scenarioMetaTextField.setEnabled(enabled);
        if (!enabled) {
            stepsScrollPane.setViewportView(null);
            stepsScrollPane.setRowHeaderView(null);
            disableList(parametersList);
            disableList(parameterValuesList);
            disableTable(examplesTable);
            enableOrDisableExamplesTableAddRemoveButtons();
            updateExamplesTableExamplesCount();
        } else {
            enableList(parametersList);
            enableList(parameterValuesList);
            enableTable(examplesTable);
            enableOrDisableExamplesTableAddRemoveButtons();
        }
    }
    
    private void disableList(JList list) {
        list.setEnabled(false);
        list.setModel(new DefaultListModel());
        list.setBackground(UIManager.getColor("List.disabledBackground"));
    }
    
    private void enableList(JList list) {
        list.setEnabled(true);
        list.setBackground(UIManager.getColor("List.background"));
    }
    
    private void disableTable(JTable table) {
        table.setEnabled(false);
        table.setModel(new DefaultTableModel());
        table.setBackground(UIManager.getColor("Table.disabledBackground"));
    }
    
    private void enableTable(JTable table) {
        table.setEnabled(true);
        table.setBackground(UIManager.getColor("Table.background"));
    }
    
    private void enableOrDisableExamplesTableAddRemoveButtons() {
        int rowCount = examplesTable.getModel().getRowCount();
        boolean enabled = rowCount > 0;
        addExampleButton.setEnabled(enabled);
        removeExampleButton.setEnabled(enabled);
    }
    
    public boolean hasExistingStoryFile() {
        return existingStoryFile != null;
    }

    public ScreenContext getScreenContext() {
        return screenContext;
    }
}

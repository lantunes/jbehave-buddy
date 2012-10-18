package org.bigtesting.jbehave.buddy.ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.text.StyledDocument;

import net.miginfocom.swing.MigLayout;

import org.bigtesting.jbehave.buddy.ui.editor.StepsEditorModel;
import org.bigtesting.jbehave.buddy.ui.widgets.ExamplesTableModel;
import org.bigtesting.jbehave.buddy.ui.widgets.ListAction;
import org.bigtesting.jbehave.buddy.ui.widgets.ParamValuesEditListAction;
import org.bigtesting.jbehave.buddy.ui.widgets.ParameterValuesListModel;
import org.bigtesting.jbehave.buddy.ui.widgets.StepsTextPane;
import org.bigtesting.jbehave.buddy.ui.widgets.SwingStepsDocument;
import org.bigtesting.jbehave.buddy.util.ExamplesFormatter;

public class Screen {

    public static final String MAIN_FRAME = "mainFrame";
    public static final String MAIN_PANEL = "mainPanel";
    public static final String MAIN_TABS = "mainTabs";
    public static final String SCENARIOS_TAB_PANEL = "scenariosTabPanel";
    public static final String SCENARIO_LABEL = "scenarioLabel";
    public static final String SCENARIO_COMBO_BOX = "scenarioComboBox";
    public static final String ADD_SCENARIO_BUTTON = "addScenarioButton";
    public static final String SCENARIO_TABS = "scenarioTabs";
    public static final String STEPS_TAB_PANEL = "stepsTabPanel";
    public static final String STEPS_SCROLL_PANE = "stepsScrollPane";
    public static final String TEXT_PANE = "textPane";
    public static final String PARAMETERS_PANEL = "parametersPanel";
    public static final String PARAMETERS_SCROLL_PANE = "parametersScrollPane";
    public static final String PARAMETERS_LIST = "parametersList";
    public static final String GENERATE_EXAMPLES_BUTTON = "generateExamplesButton";
    public static final String EXAMPLES_SCROLL_PANE = "examplesScrollPane";
    public static final String EXAMPLES_TAB_PANEL = "examplesTabPanel";
    public static final String REMOVE_PARAM_VALUE_BUTTON = "removeParamValueButton";
    public static final String ADD_PARAM_VALUE_BUTTON = "addParamValueButton";
    public static final String PARAMETER_VALUES_LIST = "parameterValuesList";
    public static final String PARAMETER_VALUES_SCROLL_PANE = "parameterValuesScrollPane";
    public static final String PARAMETER_VALUES_PANEL = "parameterValuesPanel";
    public static final String EXAMPLES_TABLE = "examplesTable";
    public static final String NUM_EXAMPLES_LABEL = "numExamplesLabel";
    public static final String ADD_EXAMPLE_BUTTON = "addExampleButton";
    public static final String REMOVE_EXAMPLE_BUTTON = "removeExampleButton";
    public static final String STORY_TAB_PANEL = "storyTabPanel";
    public static final String STORY_SCROLL_PANE = "storyScrollPane";
    public static final String STORY_TEXT_AREA = "storyTextArea";
    public static final String COPY_TEXT_BUTTON = "copyTextButton";
    public static final String REFRESH_STORY_BUTTON = "refreshStoryButton";
    public static final String EXIT_MENU_ITEM = "exitMenuItem";
    public static final String OPEN_EXISTING_STORY_MENU_ITEM = "openExistingStoryMenuItem";
    public static final String NEW_STORY_MENU_ITEM = "newStoryMenuItem";
    public static final String FILE_MENU = "fileMenu";

    private JFrame mainFrame;
    private JPanel mainPanel;
    private JTabbedPane mainTabs;
    private JPanel scenariosTabPanel;
    private JLabel scenarioLabel;
    private JComboBox scenarioComboBox;
    private JButton addScenarioButton;
    private JTabbedPane scenarioTabs;
    private JPanel stepsTabPanel;
    private JScrollPane stepsScrollPane;
    private JTextPane textPane;
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

    private StepsEditorModel model;
    private ScenarioParameters params;
    private ParamValuesEditListAction editListAction;
    private ParameterValuesListModel paramValuesListModel;
    private ExamplesGenerator examplesGenerator;
    private ExamplesTableModel examplesTableModel;
    private StepsDocument stepsDoc;

    public Screen() {
        initialize();
    }

    public JFrame getFrame() {
        return mainFrame;
    }

    public void display() {
        mainFrame.setVisible(true);
    }

    private void initialize() {
        
        initMainControls();
        
        initScenariosTabPanel();
        createTopLevelScenariosControls();

        initScenarioTabs();
        createStepsEditorControls();
        
        initControlModels();

        createParametersControls();
        createParameterValuesControls();
        
        createExamplesTab();

        createStoryTab();

        initMenuBar();
    }

    private void initControlModels() {
        StyledDocument doc = textPane.getStyledDocument();
        stepsDoc = new SwingStepsDocument(doc);
        model = new StepsEditorModel(stepsDoc);
        params = new ScenarioParameters();
        model.addParametersListener(params);
        examplesGenerator = new ExamplesGenerator(params);
        paramValuesListModel = new ParameterValuesListModel(params);
        doc.addDocumentListener(new DocumentListener() {
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
    }

    private void createTopLevelScenariosControls() {
        initScenarioLabel();
        initScenarioComboBox();
        initAddScenarioButton();
    }

    private void createStepsEditorControls() {
        initStepsTabPanel();
        initStepsScrollPane();
        initStepsTextPane();
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
        initMainFrame();
        initMainPanel();
        initMainTabs();
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        mainFrame.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        fileMenu.setName(FILE_MENU);
        menuBar.add(fileMenu);

        JMenuItem newStoryMenuItem = new JMenuItem("New story...");
        newStoryMenuItem.setName(NEW_STORY_MENU_ITEM);
        fileMenu.add(newStoryMenuItem);

        JMenuItem openExistingStoryMenuItem = new JMenuItem("Open existing story...");
        openExistingStoryMenuItem.setName(OPEN_EXISTING_STORY_MENU_ITEM);
        fileMenu.add(openExistingStoryMenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName(EXIT_MENU_ITEM);
        fileMenu.add(exitMenuItem);
    }

    private void initRefreshStoryButton() {
        refreshStoryButton = new JButton("Refresh");
        refreshStoryButton.setName(REFRESH_STORY_BUTTON);
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
        copyTextButton.setName(COPY_TEXT_BUTTON);
        storyTabPanel.add(copyTextButton, "flowx,cell 0 1,alignx right,aligny bottom");
        copyTextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                copyToClipboard();
            }
        });
    }

    private void initStoryTextArea() {
        storyTextArea = new JTextArea();
        storyTextArea.setName(STORY_TEXT_AREA);
        storyTextArea.setEditable(false);
        storyScrollPane.setViewportView(storyTextArea);
    }

    private void initStoryScrollPane() {
        storyScrollPane = new JScrollPane();
        storyScrollPane.setName(STORY_SCROLL_PANE);
        storyTabPanel.add(storyScrollPane, "cell 0 0,grow");
    }

    private void initStoryTabPanel() {
        storyTabPanel = new JPanel();
        storyTabPanel.setName(STORY_TAB_PANEL);
        mainTabs.addTab("Story", null, storyTabPanel, null);
        storyTabPanel.setLayout(new MigLayout("", "[grow]", "[grow][]"));
    }

    private void initRemoveExampleButton() {
        removeExampleButton = new JButton("Remove");
        removeExampleButton.setName(REMOVE_EXAMPLE_BUTTON);
        examplesTabPanel.add(removeExampleButton, "cell 1 2,alignx right,aligny bottom");
        removeExampleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeExample();
            }
        });
    }

    private void initAddExampleButton() {
        addExampleButton = new JButton("Add");
        addExampleButton.setName(ADD_EXAMPLE_BUTTON);
        examplesTabPanel.add(addExampleButton, "flowx,cell 1 2,alignx right,aligny bottom");
        addExampleButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                addExample();
            }
        });
    }

    private void initNumExamplesLabel() {
        numExamplesLabel = new JLabel(" ");
        numExamplesLabel.setName(NUM_EXAMPLES_LABEL);
        examplesTabPanel.add(numExamplesLabel, "cell 0 2,alignx right,aligny bottom");
    }

    private void initExamplesTable() {
        examplesTableModel = new ExamplesTableModel();
        examplesTable = new JTable(examplesTableModel);
        examplesTable.setName(EXAMPLES_TABLE);
        examplesScrollPane.setViewportView(examplesTable);
        examplesTableModel.addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                examplesTableChanged(e);
            }
        });
    }

    private void initParameterValuesPanel() {
        parameterValuesPanel = new JPanel();
        parameterValuesPanel.setName(PARAMETER_VALUES_PANEL);
        parameterValuesPanel.setBorder(new TitledBorder(null, "Parameter Values", TitledBorder.LEADING,
                TitledBorder.TOP, null, null));
        stepsTabPanel.add(parameterValuesPanel, "cell 1 1,grow");
        parameterValuesPanel.setLayout(new MigLayout("", "[grow]", "[grow][]"));
    }

    private void initParameterValuesScrollPane() {
        parameterValuesScrollPane = new JScrollPane();
        parameterValuesScrollPane.setName(PARAMETER_VALUES_SCROLL_PANE);
        parameterValuesPanel.add(parameterValuesScrollPane, "cell 0 0,grow");
    }

    private void initParameterValuesList() {
        parameterValuesList = new JList(paramValuesListModel);
        parameterValuesList.setName(PARAMETER_VALUES_LIST);
        editListAction = new ParamValuesEditListAction();
        new ListAction(parameterValuesList, editListAction);
        parameterValuesScrollPane.setViewportView(parameterValuesList);
    }

    private void initAddParamValueButton() {
        addParamValueButton = new JButton("Add");
        addParamValueButton.setName(ADD_PARAM_VALUE_BUTTON);
        parameterValuesPanel.add(addParamValueButton, "flowx,cell 0 1,alignx right,aligny bottom");
        addParamValueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addParamValue();
            }
        });
    }

    private void initRemoveParamValueButton() {
        removeParamValueButton = new JButton("Remove");
        removeParamValueButton.setName(REMOVE_PARAM_VALUE_BUTTON);
        parameterValuesPanel.add(removeParamValueButton, "cell 0 1,alignx right,aligny bottom");
        removeParamValueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                removeParamValue();
            }
        });
    }

    private void initExamplesTabPanel() {
        examplesTabPanel = new JPanel();
        examplesTabPanel.setName(EXAMPLES_TAB_PANEL);
        scenarioTabs.addTab("Examples", null, examplesTabPanel, null);
        examplesTabPanel.setLayout(new MigLayout("", "[][grow]", "[][grow][]"));
    }

    private void initExamplesScrollPane() {
        examplesScrollPane = new JScrollPane();
        examplesScrollPane.setName(EXAMPLES_SCROLL_PANE);
        examplesTabPanel.add(examplesScrollPane, "cell 0 1 2 1,grow");
    }

    private void initGenerateExamplesButton() {
        generateExamplesButton = new JButton("Generate examples");
        generateExamplesButton.setName(GENERATE_EXAMPLES_BUTTON);
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
        parametersList.setName(PARAMETERS_LIST);
        parametersScrollPane.setViewportView(parametersList);
        parametersList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                parameterSelectionChanged();
            }
        });
    }

    private void initParametersScrollPane() {
        parametersScrollPane = new JScrollPane();
        parametersScrollPane.setName(PARAMETERS_SCROLL_PANE);
        parametersPanel.add(parametersScrollPane, "cell 0 0,grow");
    }

    private void initParametersPanel() {
        parametersPanel = new JPanel();
        parametersPanel.setName(PARAMETERS_PANEL);
        parametersPanel.setBorder(new TitledBorder(null, "Parameters", TitledBorder.LEADING, TitledBorder.TOP, null,
                null));
        stepsTabPanel.add(parametersPanel, "cell 1 0,grow");
        parametersPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
    }

    private void initStepsTextPane() {
        textPane = new JTextPane();
        textPane.setName(TEXT_PANE);
        textPane.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        stepsScrollPane.setViewportView(textPane);
        StepsTextPane stepsTextPane = new StepsTextPane(textPane);
        stepsScrollPane.setRowHeaderView(stepsTextPane);
    }

    private void initStepsScrollPane() {
        stepsScrollPane = new JScrollPane();
        stepsScrollPane.setName(STEPS_SCROLL_PANE);
        stepsTabPanel.add(stepsScrollPane, "cell 0 0 1 2,grow");
    }

    private void initStepsTabPanel() {
        stepsTabPanel = new JPanel();
        stepsTabPanel.setName(STEPS_TAB_PANEL);
        scenarioTabs.addTab("Steps", null, stepsTabPanel, null);
        stepsTabPanel.setLayout(new MigLayout("", "[360.00,grow][300px:300px:300px,right]", "[grow][grow]"));
    }

    private void initScenarioTabs() {
        scenarioTabs = new JTabbedPane(JTabbedPane.TOP);
        scenarioTabs.setName(SCENARIO_TABS);
        scenariosTabPanel.add(scenarioTabs, "cell 0 1 3 1,grow");
    }

    private void initAddScenarioButton() {
        addScenarioButton = new JButton("Add...");
        addScenarioButton.setName(ADD_SCENARIO_BUTTON);
        scenariosTabPanel.add(addScenarioButton, "cell 2 0");
    }

    private void initScenarioComboBox() {
        scenarioComboBox = new JComboBox();
        scenarioComboBox.setName(SCENARIO_COMBO_BOX);
        scenariosTabPanel.add(scenarioComboBox, "cell 1 0,growx");
    }

    private void initScenarioLabel() {
        scenarioLabel = new JLabel("Scenario:");
        scenarioLabel.setName(SCENARIO_LABEL);
        scenariosTabPanel.add(scenarioLabel, "cell 0 0,alignx left");
    }
    
    private void removeParamValue() {
        String selectedParam = (String) parametersList.getSelectedValue();
        String selectedValue = (String) parameterValuesList.getSelectedValue();
        if (selectedParam != null && selectedValue != null) {
            paramValuesListModel.removeValue(selectedParam, selectedValue);
        }
    }

    private JPanel initScenariosTabPanel() {
        scenariosTabPanel = new JPanel();
        scenariosTabPanel.setName(SCENARIOS_TAB_PANEL);
        mainTabs.addTab("Scenarios", null, scenariosTabPanel, null);
        scenariosTabPanel.setLayout(new MigLayout("", "[][grow][]", "[][grow]"));
        return scenariosTabPanel;
    }

    private JTabbedPane initMainTabs() {
        mainTabs = new JTabbedPane(JTabbedPane.TOP);
        mainTabs.setName(MAIN_TABS);
        mainPanel.add(mainTabs, "cell 0 0,grow");
        return mainTabs;
    }

    private JPanel initMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setName(MAIN_PANEL);
        mainFrame.getContentPane().add(mainPanel, "cell 0 0,grow");
        mainPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        return mainPanel;
    }

    private void initMainFrame() {
        mainFrame = new JFrame();
        mainFrame.setPreferredSize(new Dimension(859, 582));
        mainFrame.setMinimumSize(new Dimension(859, 582));
        mainFrame.setTitle("JBehave BuDDy v0.1");
        mainFrame.setName(MAIN_FRAME);
        mainFrame.setBounds(100, 100, 859, 582);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
    }

    private void stepsTextEdited() {
        model.handleTextEdit();
        parametersList.setListData(params.getActiveParameters());
    }

    private void addParamValue() {
        String selectedParam = (String) parametersList.getSelectedValue();
        if (selectedParam != null) {
            String value = JOptionPane.showInputDialog(mainFrame, "Enter value:");
            if (value != null && value.trim().length() != 0) {
                paramValuesListModel.addValue(selectedParam, value);
            }
        }
    }

    private void parameterSelectionChanged() {
        String param = (String) parametersList.getSelectedValue();
        if (param != null) {
            paramValuesListModel.setValuesFor(param);
            editListAction.setCurrentParam(param);
        } else {
            paramValuesListModel.clear();
        }
    }

    private void generateExamples() {
        int numExamples = examplesGenerator.numExamples();
        if (numExamples > 50) {
            int response = JOptionPane.showConfirmDialog(mainFrame, "There are " + 
                    numExamples + " examples. Do you wish to continue?");
            if (response != JOptionPane.YES_OPTION) {
                return;
            }
        }
        String[][] examples = examplesGenerator.generateExamples();
        if (examples != null && examples.length != 0) {
            examplesTableModel.clear();
            examplesTableModel.setData(examples);
        }
    }

    private void copyToClipboard() {
        StringSelection data = new StringSelection(storyTextArea.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(data, data);
    }

    private void refreshStory() {
        StringBuilder sb = new StringBuilder(stepsDoc.getEntireTextContent());
        if (examplesTableModel.getRowCount() > 0) {
            sb.append("\n\nExamples:\n");
            sb.append(ExamplesFormatter.format(examplesTableModel.getCurrentExamples()));
        }
        storyTextArea.setText(sb.toString());
    }

    private void removeExample() {
        int[] rows = examplesTable.getSelectedRows();
        if (rows.length > 0) {
            examplesTableModel.removeRows(rows);
        }
    }

    private void addExample() {
        examplesTableModel.addNewRow();
    }

    private void examplesTableChanged(TableModelEvent e) {
        int eventType = e.getType();
        if (eventType == TableModelEvent.INSERT || eventType == TableModelEvent.DELETE) {
            int rowCount = examplesTableModel.getRowCount();
            String example = "example";
            numExamplesLabel.setText(rowCount == 0 ? "" : rowCount + " " + (rowCount == 1 ? example : example+"s"));
        }
    }
}

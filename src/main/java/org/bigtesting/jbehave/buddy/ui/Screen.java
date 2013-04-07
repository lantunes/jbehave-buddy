package org.bigtesting.jbehave.buddy.ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
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
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;

import net.miginfocom.swing.MigLayout;

import org.bigtesting.jbehave.buddy.ui.widgets.ListAction;
import org.bigtesting.jbehave.buddy.ui.widgets.ParamValuesEditListAction;
import org.bigtesting.jbehave.buddy.ui.widgets.StepsTextPane;
import org.bigtesting.jbehave.buddy.util.ExceptionFileWriter;

public class Screen implements IScreen {

    private static final String TITLE = "JBehave BuDDy";
    private static final String version = "0.1";
    
    public static final String EXAMPLES_TAB_TITLE = "Examples";
    public static final String STORY_TAB_TITLE = "Story";
    public static final String SCENARIOS_TAB_TITLE = "Scenarios";
    public static final String STEPS_TAB_TITLE = "Steps";
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
    public static final String OK_BUTTON = "okButton";
    public static final String CANCEL_BUTTON = "cancelButton";
    public static final String EXIT_MENU_ITEM = "exitMenuItem";
    public static final String OPEN_EXISTING_STORY_MENU_ITEM = "openExistingStoryMenuItem";
    public static final String NEW_STORY_MENU_ITEM = "newStoryMenuItem";
    public static final String FILE_MENU = "fileMenu";
    public static final String HELP_MENU = "helpMenu";
    public static final String ABOUT_MENU_ITEM = "aboutMenuItem";

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
    private ParamValuesEditListAction editListAction;
    
    private StoryModel storyModel;
    
    private File existingStoryFile;
    
    public Screen() {
        this(null);
    }
    
    public Screen(File existingStoryFile) {
        this.existingStoryFile = existingStoryFile;
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

        createParametersControls();
        createParameterValuesControls();
        
        createExamplesTab();

        createStoryTab();

        initMenuBar();
        
        if (hasExistingStoryFile()) {
            initExistingStory();
        }
    }

    private void createTopLevelScenariosControls() {
        initScenarioLabel();
        initScenarioComboBox();
        initAddScenarioButton();
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
        initMainFrame();
        initMainPanel();
        initMainTabs();
    }

    private void initMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        mainFrame.setJMenuBar(menuBar);

        initFileMenu(menuBar);
        initHelpMenu(menuBar);
    }

    private void initFileMenu(JMenuBar menuBar) {
        JMenu fileMenu = new JMenu("File");
        fileMenu.setName(FILE_MENU);
        menuBar.add(fileMenu);

        initNewStoryMenuItem(fileMenu);
        initOpenExistingStoryMenuItem(fileMenu);
        initExitMenuItem(fileMenu);
    }

    private void initNewStoryMenuItem(JMenu fileMenu) {
        JMenuItem newStoryMenuItem = new JMenuItem("New story...");
        newStoryMenuItem.setName(NEW_STORY_MENU_ITEM);
        newStoryMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                newStory();
            }
        });
        fileMenu.add(newStoryMenuItem);
        newStoryMenuItem.setEnabled(!hasExistingStoryFile());
    }
    
    private void initOpenExistingStoryMenuItem(JMenu fileMenu) {
        JMenuItem openExistingStoryMenuItem = new JMenuItem("Open existing story...");
        openExistingStoryMenuItem.setName(OPEN_EXISTING_STORY_MENU_ITEM);
        fileMenu.add(openExistingStoryMenuItem);
        openExistingStoryMenuItem.setEnabled(!hasExistingStoryFile());
    }
    
    private void initExitMenuItem(JMenu fileMenu) {
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName(EXIT_MENU_ITEM);
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                close();
            }
        });
        fileMenu.add(exitMenuItem);
    }
    
    private void initHelpMenu(JMenuBar menuBar) {
        JMenu helpMenu = new JMenu("Help");
        helpMenu.setName(HELP_MENU);
        menuBar.add(helpMenu);
        
        initAboutMenuItem(helpMenu);
    }

    private void initAboutMenuItem(JMenu helpMenu) {
        JMenuItem aboutMenuItem = new JMenuItem("About");
        aboutMenuItem.setName(ABOUT_MENU_ITEM);
        aboutMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                JOptionPane.showMessageDialog(mainFrame, 
                        TITLE + " version " + version, 
                        "About", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        helpMenu.add(aboutMenuItem);
    }
    
    private void close() {
        mainFrame.setVisible(false);
        mainFrame.dispose();
    }
    
    private void initExistingStory() {
        
        initOKButton();
        initCancelButton();
        
        importStory();

        if (storyModel != null && storyModel.hasScenarios()) {
            prepareUIForExistingStory();
        } else {
            prepareUIForNewStory();
        }
    }
    
    private void initOKButton() {
        okButton = new JButton("OK");
        okButton.setName(OK_BUTTON);
        okButton.setEnabled(true);
        mainPanel.add(okButton, "flowx,cell 0 1,alignx right,aligny bottom");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                new StoryExporter().export(storyModel);
                close();
            }
        });
    }
    
    private void initCancelButton() {
        cancelButton = new JButton("Cancel");
        cancelButton.setName(CANCEL_BUTTON);
        cancelButton.setEnabled(true);
        mainPanel.add(cancelButton, "cell 0 1,alignx right,aligny bottom");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                close();
            }
        });
    }

    private void importStory() {
        try {
            storyModel = new StoryImporter().importStory(existingStoryFile, this);
        } catch (Exception e) {
            ExceptionFileWriter.writeException(e);
            JOptionPane.showMessageDialog(mainFrame, "there was an error parsing the story file: " + e.getMessage());
        }
    }
    
    private void initRefreshStoryButton() {
        refreshStoryButton = new JButton("Refresh");
        refreshStoryButton.setName(REFRESH_STORY_BUTTON);
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
        copyTextButton.setName(COPY_TEXT_BUTTON);
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
        storyTextArea.setName(STORY_TEXT_AREA);
        storyTextArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
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
        mainTabs.addTab(STORY_TAB_TITLE, null, storyTabPanel, null);
        storyTabPanel.setLayout(new MigLayout("", "[grow]", "[grow][]"));
    }

    private void initRemoveExampleButton() {
        removeExampleButton = new JButton("Remove");
        removeExampleButton.setName(REMOVE_EXAMPLE_BUTTON);
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
        addExampleButton.setName(ADD_EXAMPLE_BUTTON);
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
        numExamplesLabel.setName(NUM_EXAMPLES_LABEL);
        examplesTabPanel.add(numExamplesLabel, "cell 0 2,alignx right,aligny bottom");
    }

    private void initExamplesTable() {
        examplesTable = new JTable();
        examplesTable.setName(EXAMPLES_TABLE);
        disableTable(examplesTable);
        examplesScrollPane.setViewportView(examplesTable);
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
        parameterValuesList = new JList();
        parameterValuesList.setName(PARAMETER_VALUES_LIST);
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
        addParamValueButton.setName(ADD_PARAM_VALUE_BUTTON);
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
        removeParamValueButton.setName(REMOVE_PARAM_VALUE_BUTTON);
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
        examplesTabPanel.setName(EXAMPLES_TAB_PANEL);
        scenarioTabs.addTab(EXAMPLES_TAB_TITLE, null, examplesTabPanel, null);
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
        parametersList.setName(PARAMETERS_LIST);
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

    public JTextPane newStepsTextPane() {
        JTextPane textPane = new JTextPane();
        textPane.setName(TEXT_PANE);
        textPane.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
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
        stepsScrollPane.setName(STEPS_SCROLL_PANE);
        stepsTabPanel.add(stepsScrollPane, "cell 0 0 1 2,grow");
    }

    private void initStepsTabPanel() {
        stepsTabPanel = new JPanel();
        stepsTabPanel.setName(STEPS_TAB_PANEL);
        scenarioTabs.addTab(STEPS_TAB_TITLE, null, stepsTabPanel, null);
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
        addScenarioButton.setEnabled(false);
        scenariosTabPanel.add(addScenarioButton, "cell 2 0");
        addScenarioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addNewScenario();
            }
        });
    }

    private void initScenarioComboBox() {
        scenarioComboBox = new JComboBox();
        scenarioComboBox.setName(SCENARIO_COMBO_BOX);
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
        scenarioLabel.setName(SCENARIO_LABEL);
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
        scenariosTabPanel.setName(SCENARIOS_TAB_PANEL);
        mainTabs.addTab(SCENARIOS_TAB_TITLE, null, scenariosTabPanel, null);
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
        mainFrame.setTitle(TITLE);
        BufferedImage image = null;
        try {
            image = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("logo.png"));
        } catch (IOException e) {
            ExceptionFileWriter.writeException(e);
        }
        mainFrame.setIconImage(image);
        mainFrame.setName(MAIN_FRAME);
        mainFrame.setBounds(100, 100, 859, 582);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
    }

    private void stepsTextEdited() {
        if (storyModel != null && storyModel.getSelectedScenario() != null) {
            storyModel.getSelectedScenario().handleStepsTextEdit();
        }
    }

    private void addParamValue() {
        String selectedParam = (String) parametersList.getSelectedValue();
        if (selectedParam != null) {
            String value = JOptionPane.showInputDialog(mainFrame, "Enter value:");
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
            int response = JOptionPane.showConfirmDialog(mainFrame, "There are " + 
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
        
        String description = JOptionPane.showInputDialog(mainFrame, "Description: ");
        
        if (description == null || description.trim().length() == 0) {
            return;
        }
        
        parametersList.clearSelection();
        storyModel.addScenario(description, this);
        enableControls(true);
    }
    
    private void scenarioChanged() {
        ScenarioModel selectedScenario = storyModel.getSelectedScenario();
        if (selectedScenario != null) {
            parameterValuesList.setModel(selectedScenario.getParamValuesListModel());
            parametersList.setModel(selectedScenario.getParametersListModel());
            examplesTable.setModel(selectedScenario.getExamplesTableModel());
            enableOrDisableExamplesTableAddRemoveButtons();
            updateExamplesTableExamplesCount();
            JTextPane textPane = selectedScenario.getStepsTextPane();
            stepsScrollPane.setViewportView(textPane);
            stepsScrollPane.setRowHeaderView(new StepsTextPane(textPane));
            selectedScenario.clearParameterValues();
        }
    }
    
    private void newStory() {

        if (storyModel != null) {
            int result = JOptionPane.showConfirmDialog(mainFrame, 
                    "All scenarios in the current story will be removed", 
                    "Warning", JOptionPane.WARNING_MESSAGE);
            if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
                return;
            }
        }
        
        prepareUIForNewStory();
    }
    
    private void prepareUIForNewStory() {
        storyModel = new StoryModel();
        scenarioComboBox.setModel(storyModel.getComboBoxModel());
        addScenarioButton.setEnabled(true);
        storyTextArea.setText("");
        enableControls(false);
    }
    
    private void prepareUIForExistingStory() {
        scenarioComboBox.setModel(storyModel.getComboBoxModel());
        addScenarioButton.setEnabled(true);
        storyTextArea.setText("");
        enableControls(true);
        scenarioChanged();
    }
    
    private void enableControls(boolean enabled) {
        scenarioComboBox.setEnabled(enabled);
        generateExamplesButton.setEnabled(enabled);
        copyTextButton.setEnabled(enabled);
        refreshStoryButton.setEnabled(enabled);
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
    
    private boolean hasExistingStoryFile() {
        return existingStoryFile != null;
    }
}

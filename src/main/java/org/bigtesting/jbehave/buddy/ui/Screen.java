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
            @Override
            public void removeUpdate(DocumentEvent evt) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        stepsTextEdited();
                    }
                });
            }

            @Override
            public void insertUpdate(DocumentEvent evt) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        stepsTextEdited();
                    }
                });
            }

            @Override
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
        menuBar.add(fileMenu);

        JMenuItem newStoryMenuItem = new JMenuItem("New story...");
        fileMenu.add(newStoryMenuItem);

        JMenuItem openExistingStoryMenuItem = new JMenuItem("Open existing story...");
        fileMenu.add(openExistingStoryMenuItem);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);
    }

    private void initRefreshStoryButton() {
        refreshStoryButton = new JButton("Refresh");
        storyTabPanel.add(refreshStoryButton, "cell 0 1,alignx right,aligny bottom");
        refreshStoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        refreshStory();
                    }
                });
            }
        });
    }

    private void initCopyTextButton() {
        copyTextButton = new JButton("Copy to clipboard");
        storyTabPanel.add(copyTextButton, "flowx,cell 0 1,alignx right,aligny bottom");
        copyTextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                copyToClipboard();
            }
        });
    }

    private void initStoryTextArea() {
        storyTextArea = new JTextArea();
        storyTextArea.setEditable(false);
        storyScrollPane.setViewportView(storyTextArea);
    }

    private void initStoryScrollPane() {
        storyScrollPane = new JScrollPane();
        storyTabPanel.add(storyScrollPane, "cell 0 0,grow");
    }

    private void initStoryTabPanel() {
        storyTabPanel = new JPanel();
        mainTabs.addTab("Story", null, storyTabPanel, null);
        storyTabPanel.setLayout(new MigLayout("", "[grow]", "[grow][]"));
    }

    private void initRemoveExampleButton() {
        removeExampleButton = new JButton("Remove");
        examplesTabPanel.add(removeExampleButton, "cell 1 2,alignx right,aligny bottom");
        removeExampleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeExample();
            }
        });
    }

    private void initAddExampleButton() {
        addExampleButton = new JButton("Add");
        examplesTabPanel.add(addExampleButton, "flowx,cell 1 2,alignx right,aligny bottom");
        addExampleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                addExample();
            }
        });
    }

    private void initNumExamplesLabel() {
        numExamplesLabel = new JLabel(" ");
        examplesTabPanel.add(numExamplesLabel, "cell 0 2,alignx right,aligny bottom");
    }

    private void initExamplesTable() {
        examplesTableModel = new ExamplesTableModel();
        examplesTable = new JTable(examplesTableModel);
        examplesScrollPane.setViewportView(examplesTable);
        examplesTableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                examplesTableChanged(e);
            }
        });
    }

    private void initParameterValuesPanel() {
        parameterValuesPanel = new JPanel();
        parameterValuesPanel.setBorder(new TitledBorder(null, "Parameter Values", TitledBorder.LEADING,
                TitledBorder.TOP, null, null));
        stepsTabPanel.add(parameterValuesPanel, "cell 1 1,grow");
        parameterValuesPanel.setLayout(new MigLayout("", "[grow]", "[grow][]"));
    }

    private void initParameterValuesScrollPane() {
        parameterValuesScrollPane = new JScrollPane();
        parameterValuesPanel.add(parameterValuesScrollPane, "cell 0 0,grow");
    }

    private void initParameterValuesList() {
        parameterValuesList = new JList(paramValuesListModel);
        editListAction = new ParamValuesEditListAction();
        new ListAction(parameterValuesList, editListAction);
        parameterValuesScrollPane.setViewportView(parameterValuesList);
    }

    private void initAddParamValueButton() {
        addParamValueButton = new JButton("Add");
        parameterValuesPanel.add(addParamValueButton, "flowx,cell 0 1,alignx right,aligny bottom");
        addParamValueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addParamValue();
            }
        });
    }

    private void initRemoveParamValueButton() {
        removeParamValueButton = new JButton("Remove");
        parameterValuesPanel.add(removeParamValueButton, "cell 0 1,alignx right,aligny bottom");
        removeParamValueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeParamValue();
            }
        });
    }

    private void initExamplesTabPanel() {
        examplesTabPanel = new JPanel();
        scenarioTabs.addTab("Examples", null, examplesTabPanel, null);
        examplesTabPanel.setLayout(new MigLayout("", "[][grow]", "[][grow][]"));
    }

    private void initExamplesScrollPane() {
        examplesScrollPane = new JScrollPane();
        examplesTabPanel.add(examplesScrollPane, "cell 0 1 2 1,grow");
    }

    private void initGenerateExamplesButton() {
        generateExamplesButton = new JButton("Generate examples");
        examplesTabPanel.add(generateExamplesButton, "cell 0 0 2 1");
        generateExamplesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        generateExamples();
                    }
                });
            }
        });
    }

    private void initParametersList() {
        parametersList = new JList();
        parametersScrollPane.setViewportView(parametersList);
        parametersList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                parameterSelectionChanged();
            }
        });
    }

    private void initParametersScrollPane() {
        parametersScrollPane = new JScrollPane();
        parametersPanel.add(parametersScrollPane, "cell 0 0,grow");
    }

    private void initParametersPanel() {
        parametersPanel = new JPanel();
        parametersPanel.setBorder(new TitledBorder(null, "Parameters", TitledBorder.LEADING, TitledBorder.TOP, null,
                null));
        stepsTabPanel.add(parametersPanel, "cell 1 0,grow");
        parametersPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
    }

    private void initStepsTextPane() {
        textPane = new JTextPane();
        textPane.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        stepsScrollPane.setViewportView(textPane);
        StepsTextPane stepsTextPane = new StepsTextPane(textPane);
        stepsScrollPane.setRowHeaderView(stepsTextPane);
    }

    private void initStepsScrollPane() {
        stepsScrollPane = new JScrollPane();
        stepsTabPanel.add(stepsScrollPane, "cell 0 0 1 2,grow");
    }

    private void initStepsTabPanel() {
        stepsTabPanel = new JPanel();
        scenarioTabs.addTab("Steps", null, stepsTabPanel, null);
        stepsTabPanel.setLayout(new MigLayout("", "[360.00,grow][300px:300px:300px,right]", "[grow][grow]"));
    }

    private void initScenarioTabs() {
        scenarioTabs = new JTabbedPane(JTabbedPane.TOP);
        scenariosTabPanel.add(scenarioTabs, "cell 0 1 3 1,grow");
    }

    private void initAddScenarioButton() {
        addScenarioButton = new JButton("Add...");
        scenariosTabPanel.add(addScenarioButton, "cell 2 0");
    }

    private void initScenarioComboBox() {
        scenarioComboBox = new JComboBox();
        scenariosTabPanel.add(scenarioComboBox, "cell 1 0,growx");
    }

    private void initScenarioLabel() {
        scenarioLabel = new JLabel("Scenario:");
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
        mainTabs.addTab("Scenarios", null, scenariosTabPanel, null);
        scenariosTabPanel.setLayout(new MigLayout("", "[][grow][]", "[][grow]"));
        return scenariosTabPanel;
    }

    private JTabbedPane initMainTabs() {
        mainTabs = new JTabbedPane(JTabbedPane.TOP);
        mainPanel.add(mainTabs, "cell 0 0,grow");
        return mainTabs;
    }

    private JPanel initMainPanel() {
        mainPanel = new JPanel();
        mainFrame.getContentPane().add(mainPanel, "cell 0 0,grow");
        mainPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
        return mainPanel;
    }

    private void initMainFrame() {
        mainFrame = new JFrame();
        mainFrame.setPreferredSize(new Dimension(859, 582));
        mainFrame.setMinimumSize(new Dimension(859, 582));
        mainFrame.setTitle("JBehave BuDDy v0.1");
        mainFrame.setBounds(100, 100, 859, 582);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
    }

    public void display() {
        mainFrame.setVisible(true);
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

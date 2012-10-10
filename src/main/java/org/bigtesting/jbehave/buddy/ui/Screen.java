package org.bigtesting.jbehave.buddy.ui;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.StyledDocument;

import net.miginfocom.swing.MigLayout;

import org.bigtesting.jbehave.buddy.ui.editor.StepsEditorModel;
import org.bigtesting.jbehave.buddy.ui.widgets.StepsTextPane;
import org.bigtesting.jbehave.buddy.ui.widgets.SwingStepsDocument;

public class Screen {

	private JFrame mainFrame;
	private JTable examplesTable;
	private JList parametersList;
	private StepsEditorModel model;
	private ScenarioParameters params;
	private JList parameterValuesList;
	
	public Screen() {
		initialize();
	}
	
	private void initialize() {
		mainFrame = new JFrame();
		mainFrame.setPreferredSize(new Dimension(859, 582));
		mainFrame.setMinimumSize(new Dimension(859, 582));
		mainFrame.setTitle("JBehave BuDDy");
		mainFrame.setBounds(100, 100, 859, 582);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JPanel mainPanel = new JPanel();
		mainFrame.getContentPane().add(mainPanel, "cell 0 0,grow");
		mainPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JTabbedPane mainTabs = new JTabbedPane(JTabbedPane.TOP);
		mainPanel.add(mainTabs, "cell 0 0,grow");
		
		JPanel scenariosTabPanel = new JPanel();
		mainTabs.addTab("Scenarios", null, scenariosTabPanel, null);
		scenariosTabPanel.setLayout(new MigLayout("", "[][grow][]", "[][grow]"));
		
		JLabel scenarioLabel = new JLabel("Scenario:");
		scenariosTabPanel.add(scenarioLabel, "cell 0 0,alignx left");
		
		JComboBox scenarioComboBox = new JComboBox();
		scenariosTabPanel.add(scenarioComboBox, "cell 1 0,growx");
		
		JButton addScenarioButton = new JButton("Add...");
		scenariosTabPanel.add(addScenarioButton, "cell 2 0");
		
		JTabbedPane scenarioTabs = new JTabbedPane(JTabbedPane.TOP);
		scenariosTabPanel.add(scenarioTabs, "cell 0 1 3 1,grow");
		
		JPanel stepsTabPanel = new JPanel();
		scenarioTabs.addTab("Steps", null, stepsTabPanel, null);
		stepsTabPanel.setLayout(new MigLayout("", "[360.00,grow][grow]", "[grow][grow]"));
		
		JScrollPane stepsScrollPane = new JScrollPane();
		stepsTabPanel.add(stepsScrollPane, "cell 0 0 1 2,grow");
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		stepsScrollPane.setViewportView(textPane);
		
		StepsTextPane stepsTextPane = new StepsTextPane(textPane);
		stepsScrollPane.setRowHeaderView(stepsTextPane);
		StyledDocument doc = textPane.getStyledDocument();
		model = new StepsEditorModel(new SwingStepsDocument(doc));
		params = new ScenarioParameters();
		model.addParametersListener(params);
        doc.addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent evt) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						model.handleTextEdit();
						parametersList.setListData(params.getActiveParameters());
					}
				});
			}
			@Override
			public void insertUpdate(DocumentEvent evt) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						model.handleTextEdit();
						parametersList.setListData(params.getActiveParameters());
					}
				});
			}
			@Override
			public void changedUpdate(DocumentEvent evt) {
			}
		});
		
		JPanel parametersPanel = new JPanel();
		parametersPanel.setBorder(new TitledBorder(null, "Parameters", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		stepsTabPanel.add(parametersPanel, "cell 1 0,grow");
		parametersPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JScrollPane parametersScrollPane = new JScrollPane();
		parametersPanel.add(parametersScrollPane, "cell 0 0,grow");
		
		parametersList = new JList();
		parametersScrollPane.setViewportView(parametersList);
		parametersList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String param = (String)parametersList.getSelectedValue();
				if (param != null) {
					parameterValuesList.setListData(params.getValues(param));
				} else {
					parameterValuesList.setListData(new Object[]{});
				}
			}
		});
		
		JPanel parameterValuesPanel = new JPanel();
		parameterValuesPanel.setBorder(new TitledBorder(null, "Parameter Values", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		stepsTabPanel.add(parameterValuesPanel, "cell 1 1,grow");
		parameterValuesPanel.setLayout(new MigLayout("", "[grow]", "[grow][]"));
		
		JScrollPane parameterValuesScrollPane = new JScrollPane();
		parameterValuesPanel.add(parameterValuesScrollPane, "cell 0 0,grow");
		
		parameterValuesList = new JList();
		parameterValuesScrollPane.setViewportView(parameterValuesList);
		
		JButton addParamValueButton = new JButton("Add");
		parameterValuesPanel.add(addParamValueButton, "flowx,cell 0 1,alignx right,aligny bottom");
		addParamValueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedParam = (String)parametersList.getSelectedValue();
				if (selectedParam != null) {
					ParamValueDialog dlg = new ParamValueDialog(mainFrame);
					dlg.setVisible(true);
					String value = dlg.getValue();
					if (value != null && value.trim().length() != 0) {
						params.addValue(selectedParam, dlg.getValue());
						parameterValuesList.setListData(params.getValues(selectedParam));
					}
				}
			}
		});
		
		JButton removeParamButton = new JButton("Remove");
		parameterValuesPanel.add(removeParamButton, "cell 0 1,alignx right,aligny bottom");
		removeParamButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selectedParam = (String)parametersList.getSelectedValue();
				String selectedValue = (String)parameterValuesList.getSelectedValue();
				if (selectedParam != null && selectedValue != null) {
					params.removeValue(selectedParam, selectedValue);
					parameterValuesList.setListData(params.getValues(selectedParam));
				}
			}
		});
		
		JPanel examplesTabPanel = new JPanel();
		scenarioTabs.addTab("Examples", null, examplesTabPanel, null);
		examplesTabPanel.setLayout(new MigLayout("", "[grow]", "[][grow][]"));
		
		JButton generateExamplesButton = new JButton("Generate examples");
		examplesTabPanel.add(generateExamplesButton, "cell 0 0");
		
		JScrollPane examplesScrollPane = new JScrollPane();
		examplesTabPanel.add(examplesScrollPane, "cell 0 1,grow");
		
		examplesTable = new JTable();
		examplesScrollPane.setViewportView(examplesTable);
		
		JButton addExampleButton = new JButton("Add");
		examplesTabPanel.add(addExampleButton, "flowx,cell 0 2,alignx right,aligny bottom");
		
		JButton removeExampleButton = new JButton("Remove");
		examplesTabPanel.add(removeExampleButton, "cell 0 2,alignx right,aligny bottom");
		
		JPanel storyTabPanel = new JPanel();
		mainTabs.addTab("Story", null, storyTabPanel, null);
		storyTabPanel.setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JScrollPane storyScrollPane = new JScrollPane();
		storyTabPanel.add(storyScrollPane, "cell 0 0,grow");
		
		JTextArea storyTextArea = new JTextArea();
		storyScrollPane.setViewportView(storyTextArea);
		
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
	
	public void display() {
		mainFrame.setVisible(true);
	}
}

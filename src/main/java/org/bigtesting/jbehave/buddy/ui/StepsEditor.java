package org.bigtesting.jbehave.buddy.ui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.StyledDocument;

import net.miginfocom.swing.MigLayout;

import org.bigtesting.jbehave.buddy.ui.editor.StepsEditorModel;
import org.bigtesting.jbehave.buddy.ui.widgets.StepsTextPane;

public class StepsEditor {

	private JFrame frame;
	
	public StepsEditor() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new MigLayout("", "[grow]", "[grow]"));
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		JScrollPane scrollPane = new JScrollPane(textPane);
		frame.getContentPane().add(scrollPane, "cell 0 0,grow");
		StepsTextPane stepsTextPane = new StepsTextPane(textPane);
		scrollPane.setRowHeaderView(stepsTextPane);
		StyledDocument doc = textPane.getStyledDocument();
		final StepsEditorModel model = new StepsEditorModel(doc);
		model.addStylesToDocument(doc);
        doc.addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent evt) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						model.handleTextEdit();
					}
				});
			}
			@Override
			public void insertUpdate(DocumentEvent evt) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						model.handleTextEdit();
					}
				});
			}
			@Override
			public void changedUpdate(DocumentEvent evt) {
			}
		});
	}
	
	public void display() {
		frame.setVisible(true);
	}
}

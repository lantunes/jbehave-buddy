package org.bigtesting.jbehave.buddy.ui;

import java.awt.Frame;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ParamValueDialog extends JDialog {

	private JTextField textField;
	private JOptionPane optionPane;
	private String ok = "OK";
    private String cancel = "Cancel";
	
	public ParamValueDialog(Frame parent) {
		super(parent, true);
		setTitle("Parameter Value");
		textField = new JTextField();
		
		Object[] array = {"Enter value", textField};
		Object[] options = {ok, cancel};
		
		optionPane = new JOptionPane(array,
		                 JOptionPane.QUESTION_MESSAGE,
		                 JOptionPane.YES_NO_OPTION,
		                 null,
		                 options,
		                 options[0]);
		setContentPane(optionPane);
		
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent ce) {
				textField.requestFocusInWindow();
			}
		});
		
		setSize(200,200);
		setLocationRelativeTo(parent);
	}
	
	public String getValue() {
		return textField.getText();
	}
}

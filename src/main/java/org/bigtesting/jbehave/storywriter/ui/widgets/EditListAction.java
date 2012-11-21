package org.bigtesting.jbehave.storywriter.ui.widgets;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

/**
 * Derived from http://tips4java.wordpress.com/2008/10/19/list-editor/
 * <p>
 * A simple popup editor for a JList that allows you to change the value in the
 * selected row.
 * <p>
 * The default implementation has a few limitations:
 * <p>
 * a) the JList must be using the DefaultListModel b) the data in the model is
 * replaced with a String object
 * <p>
 * If you which to use a different model or different data then you must extend
 * this class and:
 * <p>
 * a) invoke the setModelClass(...) method to specify the ListModel you need b)
 * override the applyValueToModel(...) method to update the model
 */
@SuppressWarnings("serial")
public class EditListAction extends AbstractAction {

    private JList list;

    private JPopupMenu editPopup;
    private JTextField editTextField;
    private Class<?> modelClass;

    private String preEditedValue;

    public EditListAction() {
        setModelClass(DefaultListModel.class);
    }

    protected void setModelClass(Class<?> modelClass) {
        this.modelClass = modelClass;
    }

    protected void applyValueToModel(String oldValue, String newValue, ListModel model, int row) {
        DefaultListModel dlm = (DefaultListModel) model;
        dlm.set(row, newValue);
    }

    /*
     * Display the popup editor when requested
     */
    public void actionPerformed(ActionEvent e) {

        list = (JList) e.getSource();
        ListModel model = list.getModel();

        if (!modelClass.isAssignableFrom(model.getClass()))
            return;

        if (editPopup == null)
            createEditPopup();

        // Position the popup editor over top of the selected row
        int row = list.getSelectedIndex();
        if (row == -1) {
            return;
        }
        Rectangle r = list.getCellBounds(row, row);

        editPopup.setPreferredSize(new Dimension(r.width, r.height));
        editPopup.show(list, r.x, r.y);

        // Prepare the text field for editing
        preEditedValue = list.getSelectedValue().toString();
        editTextField.setText(preEditedValue);
        editTextField.selectAll();
        editTextField.requestFocusInWindow();
    }

    private void createEditPopup() {
        // Use a text field as the editor
        editTextField = new JTextField();
        Border border = UIManager.getBorder("List.focusCellHighlightBorder");
        editTextField.setBorder(border);

        editTextField.addFocusListener(new FocusListener() {

            public void focusLost(FocusEvent arg0) {
                String value = editTextField.getText();
                ListModel model = list.getModel();
                int row = list.getSelectedIndex();
                applyValueToModel(preEditedValue, value, model, row);
                editPopup.setVisible(false);
            }

            public void focusGained(FocusEvent arg0) {
            }
        });

        // Add the editor to the popup
        editPopup = new JPopupMenu();
        editPopup.setBorder(new EmptyBorder(0, 0, 0, 0));
        editPopup.add(editTextField);
    }
}

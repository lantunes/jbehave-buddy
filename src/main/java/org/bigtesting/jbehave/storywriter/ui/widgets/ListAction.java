package org.bigtesting.jbehave.storywriter.ui.widgets;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JList;
import javax.swing.KeyStroke;

/**
 * Derived from http://tips4java.wordpress.com/2008/10/14/list-action/
 * <p>
 * Add an Action to a JList that can be invoked either by using the keyboard or
 * a mouse.
 * <p>
 * By default the Enter will will be used to invoke the Action from the keyboard
 * although you can specify and KeyStroke you wish.
 * <p>
 * A click with the mouse will invoke the same Action.
 * <p>
 * The Action can be reset at any time.
 */
public class ListAction implements MouseListener {

    private static final KeyStroke ENTER = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

    private JList list;
    private KeyStroke keyStroke;

    public ListAction(JList list, Action action) {
        this(list, action, ENTER);
    }

    public ListAction(JList list, Action action, KeyStroke keyStroke) {
        this.list = list;
        this.keyStroke = keyStroke;

        InputMap im = list.getInputMap();
        im.put(keyStroke, keyStroke);

        setAction(action);

        list.addMouseListener(this);
    }

    public void setAction(Action action) {
        list.getActionMap().put(keyStroke, action);
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
            Action action = list.getActionMap().get(keyStroke);

            if (action != null) {
                ActionEvent event = new ActionEvent(list, ActionEvent.ACTION_PERFORMED, "");
                action.actionPerformed(event);
            }
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }
}

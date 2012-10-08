package org.bigtesting.jbehave.buddy;

import java.awt.EventQueue;

import javax.swing.UIManager;

import org.bigtesting.jbehave.buddy.ui.SyntaxHighlighter;

public class Main {

	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Throwable e) {
			e.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					SyntaxHighlighter highlighter = new SyntaxHighlighter();
					highlighter.display();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

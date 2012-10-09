package org.bigtesting.jbehave.buddy.ui.widgets;

import java.awt.Color;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.bigtesting.jbehave.buddy.ui.StepsDocument;

public class SwingStepsDocument implements StepsDocument {

	private static final String STYLE_BLUE_ITALIC = "blue-italic";
	private static final String STYLE_LIGHT_GRAY_ITALIC = "light-gray-italic";
	private static final String STYLE_RED = "red";
	private static final String STYLE_ITALIC = "italic";
	private static final String STYLE_BOLD = "bold";
	private static final String STYLE_REGULAR = "regular";
	
	private final StyledDocument doc;

	public SwingStepsDocument(StyledDocument doc) {
		this.doc = doc;
	}
	
	public void addStylesToDocument() {
        Style def = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
 
        Style regular = doc.addStyle(STYLE_REGULAR, def);
        StyleConstants.setFontFamily(def, "SansSerif");
 
        Style bold = doc.addStyle(STYLE_BOLD, regular);
        StyleConstants.setBold(bold, true);
        
        Style italic = doc.addStyle(STYLE_ITALIC, regular);
        StyleConstants.setItalic(italic, true);
        
        Style red = doc.addStyle(STYLE_RED, regular);
        StyleConstants.setForeground(red, Color.red);

        Style blueItalic = doc.addStyle(STYLE_BLUE_ITALIC, italic);
        StyleConstants.setForeground(blueItalic, Color.blue);
        
        Style lightGrayItalic = doc.addStyle(STYLE_LIGHT_GRAY_ITALIC, italic);
        StyleConstants.setForeground(lightGrayItalic, Color.lightGray);
    }
	
	public void highlightKeyword(int keywordStart, int keywordLength) {
		doc.setCharacterAttributes(keywordStart, keywordLength, doc.getStyle(STYLE_BOLD), true);
	}

	public void highlightCommentedOutLine(int lineStart) {
		int lineEndIndex = getEntireTextContent().indexOf("\n", lineStart);
		int commentLength = ((lineEndIndex != -1) ? lineEndIndex : doc.getLength()) - lineStart;
		doc.setCharacterAttributes(lineStart, commentLength, doc.getStyle(STYLE_LIGHT_GRAY_ITALIC), true);
	}
	
	public void highlightParameter(int paramStart, int paramLength) {
		doc.setCharacterAttributes(paramStart, paramLength, doc.getStyle(STYLE_BLUE_ITALIC), true);
	}
	
	public void highlightError(int offset, int length) {
		doc.setCharacterAttributes(offset, length, doc.getStyle(STYLE_RED), true);
	}
	
	public String getEntireTextContent() {
		try {
			return doc.getText(0, doc.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void clearAllTextStyles() {
		doc.setCharacterAttributes(0, doc.getLength(), doc.getStyle(STYLE_REGULAR), true);
	}
}

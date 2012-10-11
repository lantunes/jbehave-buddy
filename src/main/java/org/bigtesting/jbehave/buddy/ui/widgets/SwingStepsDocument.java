package org.bigtesting.jbehave.buddy.ui.widgets;

import java.awt.Color;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.bigtesting.jbehave.buddy.ui.EditorStyle;
import org.bigtesting.jbehave.buddy.ui.StepsDocument;

public class SwingStepsDocument implements StepsDocument {

	private static final String STYLE_BLUE_ITALIC = "blue-italic";
	private static final String STYLE_LIGHT_GRAY_ITALIC = "light-gray-italic";
	private static final String STYLE_RED = "red";
	private static final String STYLE_ITALIC = "italic";
	private static final String STYLE_BOLD = "bold";
	private static final String STYLE_REGULAR = "regular";
	
	private static final Map<EditorStyle, String> styleMap;
	
	static {
		Map<EditorStyle, String> sm = new HashMap<EditorStyle, String>();
		sm.put(EditorStyle.BLUE_ITALIC, STYLE_BLUE_ITALIC);
		sm.put(EditorStyle.BOLD, STYLE_BOLD);
		sm.put(EditorStyle.ITALIC, STYLE_ITALIC);
		sm.put(EditorStyle.LIGHT_GRAY_ITALIC, STYLE_LIGHT_GRAY_ITALIC);
		sm.put(EditorStyle.RED, STYLE_RED);
		sm.put(EditorStyle.REGULAR, STYLE_REGULAR);
		styleMap = Collections.unmodifiableMap(sm);
	}
	
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
	
	public void highlightTerm(int keywordStart, int keywordLength, EditorStyle style) {
		doc.setCharacterAttributes(keywordStart, keywordLength, doc.getStyle(styleMap.get(style)), true);
	}

	public void highlightLine(int lineStart, EditorStyle style) {
		int lineEndIndex = getEntireTextContent().indexOf("\n", lineStart);
		int commentLength = ((lineEndIndex != -1) ? lineEndIndex : doc.getLength()) - lineStart;
		highlightTerm(lineStart, commentLength, style);
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
		highlightTerm(0, doc.getLength(), EditorStyle.REGULAR);
	}
}
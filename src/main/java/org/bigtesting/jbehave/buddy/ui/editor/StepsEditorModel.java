package org.bigtesting.jbehave.buddy.ui.editor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.model.Story;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.parsers.StoryParser;

public class StepsEditorModel {

	private static final String STYLE_BLUE_ITALIC = "blue-italic";
	private static final String STYLE_RED = "red";
	private static final String STYLE_ITALIC = "italic";
	private static final String STYLE_BOLD = "bold";
	private static final String STYLE_REGULAR = "regular";
	private final StyledDocument doc;
	private final Pattern paramPattern;
	
	private StoryParser parser = new RegexStoryParser();
	private Keywords kw = new Keywords();
	
	public StepsEditorModel(StyledDocument doc) {
		this.doc = doc;
		paramPattern = Pattern.compile("(<).*?(>)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	}
	
	public void addStylesToDocument(StyledDocument doc) {
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
    }
	
	public void handleTextEdit() {
		try {
			
			clearAllTextStyles();
			String text = getEntireTextContent();
			highlightMatchingText(text);
			performSemanticAnalysis(text);
		
		} catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	private void performSemanticAnalysis(String text) {
		
		ValidRegions validRegions = getValidRegions(text);
		TextLines lines = new TextLines(text);
		for (int i = 0; i < lines.count(); i++) {
			Line line = lines.getLine(i);
			if (lineIsPartOfStep(validRegions, line)) {
				highlightParameters(line);
			}
		}
	}
	
	private boolean lineIsPartOfStep(ValidRegions validRegions, Line line) {
		if (!validRegions.contains(line.offset())) {
			doc.setCharacterAttributes(line.offset(), line.content().length(), doc.getStyle(STYLE_RED), true);
			return false;
		}
		return true;
	}

	private void highlightParameters(Line line) {
		Matcher m = paramPattern.matcher(line.content());
		while (m.find()) {
			int paramStart = line.offset() + m.start();
			int paramLength = m.group().length();
			doc.setCharacterAttributes(paramStart, paramLength, doc.getStyle(STYLE_BLUE_ITALIC), true);
			//notify that a parameter has been entered
			//String param = m.group().substring(1, paramLength-1);
			//System.out.println("param: " + param);
		}
	}

	private ValidRegions getValidRegions(String text) {
		
		Story story = parser.parseStory(text);
		//each step is a String that can be composed of one or more lines
		List<String> steps = new ArrayList<String>();
		if (!story.getScenarios().isEmpty()) {
			steps.addAll(story.getScenarios().get(0).getSteps());
		}
		return new ValidRegions(steps, text);
	}

	private void highlightMatchingText(String text) {
		
		for (String keyword : Keywords.defaultKeywords().values()) {
			
			int keywordStartIndex = text.indexOf(keyword);
			while (keywordStartIndex > -1) {
				
				if (keyword.equals(kw.ignorable())) {
					highlightCommentedOutLines(text, keywordStartIndex);
				} else {
					highlightKeywords(keyword, keywordStartIndex);
				}
				
				keywordStartIndex = text.indexOf(keyword, keywordStartIndex + 1);
			}
		}
	}

	private void highlightKeywords(String keyword, int keywordStartIndex) {
		doc.setCharacterAttributes(keywordStartIndex, keyword.length(), doc.getStyle(STYLE_BOLD), true);
	}

	private void highlightCommentedOutLines(String text, int keywordStartIndex) {
		int lineEndIndex = text.indexOf("\n", keywordStartIndex);
		int commentLength = ((lineEndIndex != -1) ? lineEndIndex : doc.getLength()) - keywordStartIndex;
		doc.setCharacterAttributes(keywordStartIndex, commentLength, doc.getStyle(STYLE_ITALIC), true);
	}

	private String getEntireTextContent() throws BadLocationException {
		String text = doc.getText(0, doc.getLength());
		return text;
	}

	private void clearAllTextStyles() {
		doc.setCharacterAttributes(0, doc.getLength(), doc.getStyle(STYLE_REGULAR), true);
	}
}

package org.bigtesting.jbehave.buddy.ui.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bigtesting.jbehave.buddy.ui.StepsDocument;
import org.jbehave.core.configuration.Keywords;
import org.jbehave.core.model.Story;
import org.jbehave.core.parsers.RegexStoryParser;
import org.jbehave.core.parsers.StoryParser;

public class StepsEditorModel {

	private final StepsDocument doc;
	private final Pattern paramPattern;
	
	private StoryParser parser = new RegexStoryParser();
	private Keywords kw = new Keywords();
	
	public StepsEditorModel(StepsDocument doc) {
		this.doc = doc;
		this.doc.addStylesToDocument();
		paramPattern = Pattern.compile("(<).*?(>)", Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
	}
	
	public void handleTextEdit() {
		try {
			
			doc.clearAllTextStyles();
			String text = doc.getEntireTextContent();
			highlightText(text);
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
			doc.highlightError(line.offset(), line.content().length());
			return false;
		}
		return true;
	}

	private void highlightParameters(Line line) {
		Matcher m = paramPattern.matcher(line.content());
		while (m.find()) {
			int paramStart = line.offset() + m.start();
			int paramLength = m.group().length();
			doc.highlightParameter(paramStart, paramLength);
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

	private void highlightText(String text) {
		
		for (String keyword : Keywords.defaultKeywords().values()) {
			
			int keywordStartIndex = text.indexOf(keyword);
			while (keywordStartIndex > -1) {
				
				if (keyword.equals(kw.ignorable())) {
					doc.highlightCommentedOutLine(keywordStartIndex);
				} else {
					doc.highlightKeyword(keywordStartIndex, keyword.length());
				}
				
				keywordStartIndex = text.indexOf(keyword, keywordStartIndex + 1);
			}
		}
	}
}

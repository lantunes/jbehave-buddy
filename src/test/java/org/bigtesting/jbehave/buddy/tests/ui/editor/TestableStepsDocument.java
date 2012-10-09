package org.bigtesting.jbehave.buddy.tests.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.bigtesting.jbehave.buddy.ui.StepsDocument;

public class TestableStepsDocument implements StepsDocument {

	private final String text;
	
	private final List<String> highlightedKeywords = new ArrayList<String>();
	
	public TestableStepsDocument(String text) {
		this.text = text;
	}
	
	public String[] getHighlightedKeywords() {
		return highlightedKeywords.toArray(new String[highlightedKeywords.size()]);
	}
	
	@Override
	public void addStylesToDocument() {
		// TODO Auto-generated method stub
	}

	@Override
	public void highlightKeyword(int keywordStart, int keywordLength) {
		// TODO Auto-generated method stub
		//doc.setCharacterAttributes(keywordStartIndex, keyword.length(), doc.getStyle(STYLE_BOLD), true);
		int keywordEnd = keywordStart + keywordLength;
		if (keywordStart < text.length() && keywordEnd <= text.length()) {
			String keyword = text.substring(keywordStart, keywordEnd);
			highlightedKeywords.add(keyword);
		}
	}

	@Override
	public void highlightCommentedOutLine(int lineStart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void highlightParameter(int paramStart, int paramLength) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void highlightError(int offset, int length) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getEntireTextContent() {
		return text;
	}

	@Override
	public void clearAllTextStyles() {
		// TODO Auto-generated method stub
		
	}

}

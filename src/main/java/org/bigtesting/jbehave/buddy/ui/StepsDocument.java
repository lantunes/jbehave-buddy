package org.bigtesting.jbehave.buddy.ui;

public interface StepsDocument {

	void addStylesToDocument();
	
	void highlightKeyword(int keywordStart, int keywordLength);

	void highlightCommentedOutLine(int lineStart);

	void highlightParameter(int paramStart, int paramLength);
	
	void highlightError(int errorStart, int errorLength);
	
	String getEntireTextContent();

	void clearAllTextStyles();
}

package org.bigtesting.jbehave.buddy.tests.ui.editor;

import static org.fest.assertions.Assertions.assertThat;

import org.bigtesting.jbehave.buddy.ui.editor.StepsEditorModel;
import org.junit.Test;

public class TestStepsEditorModel {

	@Test
	public void keywordsAreHighlighted() {
		
		String text = "Given a basic test\n" +
				"And another test\n" +
				"When I test\n" +
				"Then it should pass";
		
		TestableStepsDocument doc = new TestableStepsDocument(text);
		
		StepsEditorModel model = new StepsEditorModel(doc);
		model.handleTextEdit();
		
		assertThat(doc.getHighlightedKeywords()).as("expected keyword not highlighted").containsOnly("Given", "And", "When", "Then");
	}
}

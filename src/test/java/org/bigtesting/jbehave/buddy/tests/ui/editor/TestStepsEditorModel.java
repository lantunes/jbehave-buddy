package org.bigtesting.jbehave.buddy.tests.ui.editor;

import static org.fest.assertions.Assertions.assertThat;

import org.bigtesting.jbehave.buddy.tests.ui.editor.TestableStepsDocument.HighlightedItem;
import org.bigtesting.jbehave.buddy.ui.EditorStyle;
import org.bigtesting.jbehave.buddy.ui.editor.StepsEditorModel;
import org.junit.Test;

public class TestStepsEditorModel {

	@Test
	public void termssAreHighlighted() {
		
		String text = "this is jibberish\n" +
				"!-- this is a comment\n" +
				"Given a basic <test>\n" +
				"And another test\n" +
				"When I test\n" +
				"Then it should pass";
		
		TestableStepsDocument doc = new TestableStepsDocument(text);
		
		StepsEditorModel model = new StepsEditorModel(doc);
		model.handleTextEdit();
		
		HighlightedItem[] items = doc.getHighlightedItems();
		
		assertThat(items[0].text()).isEqualTo("!-- this is a comment");
		assertThat(items[0].style()).isEqualTo(EditorStyle.LIGHT_GRAY_ITALIC);
		
		assertThat(items[1].text()).isEqualTo("<test>");
		assertThat(items[1].style()).isEqualTo(EditorStyle.BLUE_ITALIC);
		
		assertThat(items[2].text()).isEqualTo("And");
		assertThat(items[2].style()).isEqualTo(EditorStyle.BOLD);
		
		assertThat(items[3].text()).isEqualTo("Given");
		assertThat(items[3].style()).isEqualTo(EditorStyle.BOLD);
		
		assertThat(items[4].text()).isEqualTo("Then");
		assertThat(items[4].style()).isEqualTo(EditorStyle.BOLD);
		
		assertThat(items[5].text()).isEqualTo("When");
		assertThat(items[5].style()).isEqualTo(EditorStyle.BOLD);
		
		assertThat(items[6].text()).isEqualTo("this is jibberish");
		assertThat(items[6].style()).isEqualTo(EditorStyle.RED);
	}
}

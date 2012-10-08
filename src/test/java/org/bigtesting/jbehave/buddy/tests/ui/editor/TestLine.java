package org.bigtesting.jbehave.buddy.tests.ui.editor;

import static org.fest.assertions.Assertions.assertThat;

import org.bigtesting.jbehave.buddy.ui.editor.Line;
import org.junit.Test;

public class TestLine {

	@Test
	public void testGetContentReturnsContent() {
		Line line = new Line("test", 1);
		assertThat(line.content()).isEqualTo("test");
	}
	
	@Test
	public void testGetOffsetReturnsOffset() {
		Line line = new Line("test", 1);
		assertThat(line.offset()).isEqualTo(1);
	}
}

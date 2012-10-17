package org.bigtesting.jbehave.buddy.tests.integration;

import org.bigtesting.jbehave.buddy.ui.Screen;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ScreenIT {

	private FrameFixture window;
	
	@Test
	public void testIt() throws Exception {
		window.show();
	}

	@Before
	public void initFixture() {
		Screen screen = GuiActionRunner.execute(new GuiQuery<Screen>() {
			protected Screen executeInEDT() {
				return new Screen();
			}
		});
		window = new FrameFixture(screen.getFrame());
	}
	
	@After
	public void tearDown() {
		window.cleanUp();
	}
}

package org.bigtesting.jbehave.buddy.tests;

import org.bigtesting.jbehave.buddy.tests.swing.TestScreen;
import org.bigtesting.jbehave.buddy.tests.ui.TestScenarioParameters;
import org.bigtesting.jbehave.buddy.tests.ui.editor.TestStepsEditorModel;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	TestScreen.class,
	TestStepsEditorModel.class,
	TestScenarioParameters.class
})
public class AllTests {
}

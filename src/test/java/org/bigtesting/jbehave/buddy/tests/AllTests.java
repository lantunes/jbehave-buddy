package org.bigtesting.jbehave.buddy.tests;

import org.bigtesting.jbehave.buddy.tests.integration.TestScreen;
import org.bigtesting.jbehave.buddy.tests.ui.TestExamplesGenerator;
import org.bigtesting.jbehave.buddy.tests.ui.TestScenarioParameters;
import org.bigtesting.jbehave.buddy.tests.ui.editor.TestStepsEditorModel;
import org.bigtesting.jbehave.buddy.tests.util.TestExamplesFormatter;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
    TestScreen.class, 
    TestStepsEditorModel.class,
    TestScenarioParameters.class, 
    TestExamplesGenerator.class,
    TestExamplesFormatter.class })
public class AllTests {
}

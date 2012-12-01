package org.bigtesting.jbehave.storywriter.tests;

import org.bigtesting.jbehave.storywriter.tests.integration.ScreenIT;
import org.bigtesting.jbehave.storywriter.tests.ui.TestExamplesGenerator;
import org.bigtesting.jbehave.storywriter.tests.ui.TestScenarioParameters;
import org.bigtesting.jbehave.storywriter.tests.ui.editor.TestStepsEditorModel;
import org.bigtesting.jbehave.storywriter.tests.util.TestExamplesFormatter;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ 
    ScreenIT.class, 
    TestStepsEditorModel.class,
    TestScenarioParameters.class, 
    TestExamplesGenerator.class,
    TestExamplesFormatter.class })
public class AllTests {
}

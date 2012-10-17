package org.bigtesting.jbehave.buddy.tests.integration;

import static org.bigtesting.jbehave.buddy.tests.integration.dsl.ScreenDSL.*;

import org.junit.After;
import org.junit.Test;

public class ScreenIT {

    @Test
    public void testIt() throws Exception {
        
        createScreen();
        typeSteps("Given a <test>");
        assertThatStepsEditorIsEnabled();
    }

    /*-----------------------------------------*/

    @After
    public void tearDown() {
        closeScreen();
    }
}

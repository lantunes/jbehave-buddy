package org.bigtesting.jbehave.storywriter.tests.ui.editor;

import java.util.HashSet;
import java.util.Set;

import org.bigtesting.jbehave.storywriter.ui.editor.ParametersListener;

public class TestableParametersListener implements ParametersListener {

    private Set<String> parameters = new HashSet<String>();

    public void handleParameters(Set<String> parameters) {
        this.parameters = parameters;
    }

    public Set<String> getParameters() {
        return parameters;
    }
}

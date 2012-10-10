package org.bigtesting.jbehave.buddy.tests.ui.editor;

import java.util.HashSet;
import java.util.Set;

import org.bigtesting.jbehave.buddy.ui.editor.ParametersListener;

public class TestableParametersListener implements ParametersListener {

	private Set<String> parameters = new HashSet<String>();
	
	@Override
	public void handleParameters(Set<String> parameters) {
		this.parameters = parameters;
	}
	
	public Set<String> getParameters() {
		return parameters;
	}
}

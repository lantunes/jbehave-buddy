package org.bigtesting.jbehave.buddy.core.ui;

public interface ParametersProvider {

    String[] getActiveParameters();

    String[] getValues(String parameter);
}

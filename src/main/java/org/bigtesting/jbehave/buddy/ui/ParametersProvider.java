package org.bigtesting.jbehave.buddy.ui;

public interface ParametersProvider {

    String[] getActiveParameters();

    String[] getValues(String parameter);
}

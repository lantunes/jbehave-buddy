package org.bigtesting.jbehave.storywriter.ui;

public interface ParametersProvider {

    String[] getActiveParameters();

    String[] getValues(String parameter);
}

package org.bigtesting.jbehave.buddy.core.ui;

public interface ParameterValues {

    String[] getValues(String parameter);

    void addValue(String param, String value);

    void removeValue(String param, String value);
}

package org.bigtesting.jbehave.buddy.ui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bigtesting.jbehave.buddy.ui.editor.ParametersListener;
import org.bigtesting.jbehave.buddy.util.StringUtil;

public class ScenarioParameters implements ParametersListener, ParametersProvider, ParameterValues {

    private final Set<String> activeParameters = new HashSet<String>();
    private final Map<String, Set<String>> parameterValues = new HashMap<String, Set<String>>();

    public void handleParameters(Set<String> parameters) {
        activeParameters.clear();
        activeParameters.addAll(parameters);

        for (String param : parameters) {
            Set<String> existing = parameterValues.get(param);
            if (existing == null) {
                parameterValues.put(param, new HashSet<String>());
            }
        }
    }

    public String[] getActiveParameters() {
        return StringUtil.getSortedStrings(activeParameters);
    }

    public String[] getValues(String parameter) {
        return StringUtil.getSortedStrings(parameterValues.get(parameter));
    }

    public void addValue(String param, String value) {
        checkParamExists(param);
        parameterValues.get(param).add(value);
    }

    public void removeValue(String param, String value) {
        checkParamExists(param);
        parameterValues.get(param).remove(value);
    }

    private void checkParamExists(String param) {
        if (!parameterValues.containsKey(param)) {
            /*
             * NOTE: normally we would throw an Exception,
             * but we're trying to accommodate the import
             * process, which may not parse the parameters
             * from the steps, such as in inline tables
             */
            //throw new IllegalArgumentException("unknown parameter");
            parameterValues.put(param, new HashSet<String>());
        }
    }
}

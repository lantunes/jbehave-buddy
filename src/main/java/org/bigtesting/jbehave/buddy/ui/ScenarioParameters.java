package org.bigtesting.jbehave.buddy.ui;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.bigtesting.jbehave.buddy.ui.editor.ParametersListener;

public class ScenarioParameters implements ParametersListener, ParametersProvider, ParameterValues {

	private final Set<String> activeParameters = new HashSet<String>();
	private final Comparator<String> comparator = new StringComparator();
	private final Map<String, Set<String>> parameterValues = new HashMap<String, Set<String>>();
	
	@Override
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
		return getSortedStrings(activeParameters);
	}
	
	public String[] getValues(String parameter) {
		return getSortedStrings(parameterValues.get(parameter));
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
			throw new IllegalArgumentException("unknown parameter");
		}
	}
	
	private String[] getSortedStrings(Set<String> strings) {
		String[] items = strings.toArray(new String[strings.size()]);
		Arrays.sort(items, comparator);
		return items;
	}
	
	private static final class StringComparator implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
			return o1.compareTo(o2);
		}
	}
}

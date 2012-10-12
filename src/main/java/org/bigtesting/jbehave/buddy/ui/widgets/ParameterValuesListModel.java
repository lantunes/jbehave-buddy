package org.bigtesting.jbehave.buddy.ui.widgets;

import javax.swing.DefaultListModel;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import org.bigtesting.jbehave.buddy.ui.ParameterValues;

public class ParameterValuesListModel implements ListModel {

	private final ParameterValues paramValues;
	private final DefaultListModel delegate = new DefaultListModel();
	
	public ParameterValuesListModel(ParameterValues paramValues) {
		this.paramValues = paramValues;
	}
	
	public void set(int index, String val) {
		delegate.set(index, val);
	}

	public void addValue(String param, String val) {
		paramValues.addValue(param, val);
		setValuesFor(param);
	}
	
	public void removeValue(String param, String val) {
		paramValues.removeValue(param, val);
		setValuesFor(param);
	}
	
	public void replaceValue(String param, String oldValue, String newValue) {
		paramValues.removeValue(param, oldValue);
		paramValues.addValue(param, newValue);
		setValuesFor(param);
	}

	public void setValuesFor(String param) {
		setValues(paramValues.getValues(param));
	}
	
	private void setValues(String[] newVals) {
		delegate.clear();
		for (String val : newVals) {
			delegate.addElement(val);
		}
	}

	public void clear() {
		delegate.clear();
	}
	
	@Override
	public void addListDataListener(ListDataListener l) {
		delegate.addListDataListener(l);
	}

	@Override
	public Object getElementAt(int index) {
		return delegate.get(index);
	}

	@Override
	public int getSize() {
		return delegate.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		delegate.removeListDataListener(l);
	}
}

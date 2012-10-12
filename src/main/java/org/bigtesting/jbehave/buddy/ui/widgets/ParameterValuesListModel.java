package org.bigtesting.jbehave.buddy.ui.widgets;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.bigtesting.jbehave.buddy.ui.ParameterValues;

public class ParameterValuesListModel implements ListModel {

	private final ParameterValues paramValues;
	private final List<String> values = new ArrayList<String>();
	
	private final Set<ListDataListener> listeners = new HashSet<ListDataListener>();
	
	public ParameterValuesListModel(ParameterValues paramValues) {
		this.paramValues = paramValues;
	}
	
	public void set(int index, String val) {
		values.set(index, val);
		notifyListeners();
	}

	public void addValue(String param, String val) {
		paramValues.addValue(param, val);
		setValuesFor(param);
	}
	
	public void removeValue(String param, String val) {
		paramValues.removeValue(param, val);
		setValuesFor(param);
	}

	public void setValuesFor(String param) {
		setValues(paramValues.getValues(param));
	}
	
	public void clear() {
		values.clear();
		notifyListeners();
	}
	
	private void setValues(String[] newVals) {
		values.clear();
		for (String val : newVals) {
			values.add(val);
		}
		notifyListeners();
	}
	
	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public Object getElementAt(int index) {
		return values.get(index);
	}

	@Override
	public int getSize() {
		return values.size();
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}
	
	private void notifyListeners() {
		ListDataEvent le = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, getSize());
		for (ListDataListener l : listeners) {
			l.contentsChanged(le);
		}
	}
}

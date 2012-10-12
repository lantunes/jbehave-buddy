package org.bigtesting.jbehave.buddy.ui.widgets;

import javax.swing.ListModel;

@SuppressWarnings("serial")
public class ParamValuesEditListAction extends EditListAction {

	public ParamValuesEditListAction() {
		setModelClass(ParameterValuesListModel.class);
	}
	
	protected void applyValueToModel(String value, ListModel model, int row) {
		ParameterValuesListModel lm = (ParameterValuesListModel) model;
		lm.set(row, value);
	}
}

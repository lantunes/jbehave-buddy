package org.bigtesting.jbehave.storywriter.ui.widgets;

import javax.swing.ListModel;

@SuppressWarnings("serial")
public class ParamValuesEditListAction extends EditListAction {

    private String currentParam;

    public ParamValuesEditListAction() {
        setModelClass(ParameterValuesListModel.class);
    }

    public void setCurrentParam(String param) {
        this.currentParam = param;
    }

    protected void applyValueToModel(String oldValue, String newValue, ListModel model, int row) {
        ParameterValuesListModel lm = (ParameterValuesListModel) model;
        lm.replaceValue(currentParam, oldValue, newValue);
    }
}

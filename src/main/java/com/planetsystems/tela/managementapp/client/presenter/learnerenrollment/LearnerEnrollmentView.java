package com.planetsystems.tela.managementapp.client.presenter.learnerenrollment;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;

class LearnerEnrollmentView extends ViewImpl implements LearnerEnrollmentPresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private LearnerEnrollementPane learnerEnrollementPane;
	private FilterLearnerHeadCountPane filterLearnerHeadCountPane;
	
	private ControlsPane controlsPane;

    @Inject
    LearnerEnrollmentView() {
    	panel = new VLayout();
    	learnerEnrollementPane = new LearnerEnrollementPane();
    	filterLearnerHeadCountPane = new FilterLearnerHeadCountPane();
    	
    	controlsPane = new ControlsPane();
    
    	
    	panel.addMember(controlsPane);
    	panel.addMember(filterLearnerHeadCountPane);
    	panel.addMember(learnerEnrollementPane);
    	panel.setWidth100();
		panel.setHeight("90%");
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);
      
    }
    
    public Widget asWidget() {
		return panel;
	}

	public ControlsPane getControlsPane() {
		return controlsPane;
	}

	public LearnerEnrollementPane getLearnerEnrollementPane() {
		return learnerEnrollementPane;
	}

	public FilterLearnerHeadCountPane getFilterLearnerHeadCountPane() {
		return filterLearnerHeadCountPane;
	}

	
	
	
}
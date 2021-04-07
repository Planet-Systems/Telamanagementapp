package com.planetsystems.tela.managementapp.client.presenter.learnerattendance;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;

class LearnerAttendanceView extends ViewImpl implements LearnerAttendancePresenter.MyView {
 
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private LearnerAttendancePane attendancePane;
	
	private ControlsPane controlsPane;

    @Inject
    LearnerAttendanceView() {
    	panel = new VLayout();
    	attendancePane = new LearnerAttendancePane();
    	controlsPane = new ControlsPane();
    
    	
    	panel.addMember(controlsPane);
    	panel.addMember(attendancePane);
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

	public LearnerAttendancePane getAttendancePane() {
		return attendancePane;
	}


}
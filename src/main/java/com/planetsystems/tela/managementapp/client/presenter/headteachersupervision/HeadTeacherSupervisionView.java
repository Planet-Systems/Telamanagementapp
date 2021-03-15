package com.planetsystems.tela.managementapp.client.presenter.headteachersupervision;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;

class HeadTeacherSupervisionView extends ViewImpl implements HeadTeacherSupervisionPresenter.MyView {
   
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private ControlsPane controlsPane;
	private HeadTeacherSupervisionPane headTeacherSupervisionPane;


    @Inject
    HeadTeacherSupervisionView() {
    	headTeacherSupervisionPane = new HeadTeacherSupervisionPane();
    	controlsPane = new ControlsPane();
    	panel = new VLayout();		
    	
    	
    	panel.addMember(controlsPane);
    	panel.addMember(headTeacherSupervisionPane);
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

}
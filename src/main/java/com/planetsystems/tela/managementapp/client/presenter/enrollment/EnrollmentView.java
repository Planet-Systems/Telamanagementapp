package com.planetsystems.tela.managementapp.client.presenter.enrollment;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

class EnrollmentView extends ViewImpl implements EnrollmentPresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private StaffEnrollmentPane staffEnrollmentPane;
	private LearnerEnrollmentPane learnerEnrollmentPane;
	private TabSet tabSet;
	private ControlsPane controlsPane;
	public static final String STAFF_ENROLLMENT="Staff Enrollment";
	public static final String LEARNERS_ENROLLMENT="Learner Enrollment";

    @Inject
    EnrollmentView() {
    	panel = new VLayout();
    	learnerEnrollmentPane = new  LearnerEnrollmentPane();
    	staffEnrollmentPane = new StaffEnrollmentPane();
    	controlsPane = new ControlsPane();
    	tabSet = new TabSet();
    	
    	Tab staffTab = new Tab();
    	staffTab.setTitle(STAFF_ENROLLMENT);
    	staffTab.setPane(staffEnrollmentPane);
    	
    	Tab learnerTab = new Tab();
    	learnerTab.setTitle(LEARNERS_ENROLLMENT);
    	learnerTab.setPane(learnerEnrollmentPane);
    
    	tabSet.addTab(learnerTab);
    	tabSet.addTab(staffTab);
    
    	controlsPane.addMember(new Label("Menus"));
    	
    	panel.addMember(controlsPane);
    	panel.addMember(tabSet);
    	panel.setWidth100();
		panel.setHeight("90%");
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);
      
    }
    
    public Widget asWidget() {
		return panel;
	}


	public TabSet getTabSet() {
		return tabSet;
	}

	public StaffEnrollmentPane getStaffEnrollmentPane() {
		return staffEnrollmentPane;
	}

	public LearnerEnrollmentPane getLearnerEnrollmentPane() {
		return learnerEnrollmentPane;
	}

	@Override
	public ControlsPane getControlsPane() {
		return controlsPane;
	}


	
    
}
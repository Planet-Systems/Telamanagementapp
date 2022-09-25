package com.planetsystems.tela.managementapp.client.presenter.staffenrollment;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.presenter.staffenrollment.staff.SchoolStaffPane;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

class StaffEnrollmentView extends ViewImpl implements StaffEnrollmentPresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private StaffEnrollmentPane staffEnrollmentPane;
	private SchoolStaffPane schoolStaffPane;
	private TabSet tabSet;
	private ControlsPane controlsPane;
	
	public static final String STAFF_ENROLLMENT="Teacher Head Count";
	public static final String TEACHER_LIST="Teacher Enrolment";

    @Inject
    StaffEnrollmentView() {
    	panel = new VLayout();
    	schoolStaffPane = new  SchoolStaffPane();
    	
    	staffEnrollmentPane = new StaffEnrollmentPane();
    	
    	controlsPane = new ControlsPane();
    	tabSet = new TabSet();
    	
    	Tab tab1 = new Tab();
    	tab1.setTitle(STAFF_ENROLLMENT);
    	tab1.setPane(staffEnrollmentPane);
    	
    	Tab tab2 = new Tab();
    	tab2.setTitle(TEACHER_LIST);
    	tab2.setPane(schoolStaffPane);
    
    	tabSet.addTab(tab2);
    	tabSet.addTab(tab1);
    	 
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



	public SchoolStaffPane getSchoolStaffPane() {
		return schoolStaffPane;
	}

	@Override
	public ControlsPane getControlsPane() {
		return controlsPane;
	}
	
    
}
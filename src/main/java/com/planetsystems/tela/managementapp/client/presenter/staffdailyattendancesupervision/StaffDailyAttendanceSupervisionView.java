package com.planetsystems.tela.managementapp.client.presenter.staffdailyattendancesupervision;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

class StaffDailyAttendanceSupervisionView extends ViewImpl implements StaffDailyAttendanceSupervisionPresenter .MyView {
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private ControlsPane controlsPane;
	private CreateStaffDailyAttendanceTaskSupervisionPane createStaffDailyAttendanceTaskSupervisionPane;
	private ViewStaffDailyAttendanceTaskSupervisionPane viewStaffDailyAttendanceTaskSupervisionPane;
	private StaffDailyAttendanceSupervisionPane staffDailyAttendanceSupervisionPane;

	public final static String STAFF_DAILY_ATTENDANCE_SUPERVISION = "Staff Supervisions";
	public final static String ADD_STAFF_DAILY_ATTENDANCE_SUPERVISION_TASK = "Create Supervision Tasks";
	public final static String VIEW_STAFF_DAILY_ATTENDANCE_SUPERVISION_TASK = "View Supervision Tasks";
	
	TabSet tabSet;
	
	

    @Inject
    StaffDailyAttendanceSupervisionView() {
    	panel = new VLayout(); 
    	controlsPane = new ControlsPane();
    	createStaffDailyAttendanceTaskSupervisionPane = new CreateStaffDailyAttendanceTaskSupervisionPane();
    	viewStaffDailyAttendanceTaskSupervisionPane = new ViewStaffDailyAttendanceTaskSupervisionPane();
    	staffDailyAttendanceSupervisionPane = new StaffDailyAttendanceSupervisionPane();
    	
    	
    	tabSet = new TabSet();
    	Tab tab1 = new Tab(STAFF_DAILY_ATTENDANCE_SUPERVISION);
    	tab1.setPane(staffDailyAttendanceSupervisionPane);
    	
    	tabSet.addTab(tab1);
    	
    	
//        panel.addMember(staffDailyTaskPane);
    	
    	VLayout layout = new VLayout();
    	layout.setHeight100();
    	layout.setWidth100();
    	
    	layout.addMember(controlsPane);
    	layout.addMember(tabSet);
    	
    	panel.setMembers(layout);
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

	public CreateStaffDailyAttendanceTaskSupervisionPane getCreateStaffDailyAttendanceTaskSupervisionPane() {
		return createStaffDailyAttendanceTaskSupervisionPane;
	}

	public ViewStaffDailyAttendanceTaskSupervisionPane getViewStaffDailyAttendanceTaskSupervisionPane() {
		return viewStaffDailyAttendanceTaskSupervisionPane;
	}

	public StaffDailyAttendanceSupervisionPane getStaffDailyAttendanceSupervisionPane() {
		return staffDailyAttendanceSupervisionPane;
	}

	public TabSet getTabSet() {
		return tabSet;
	}
    
    



}
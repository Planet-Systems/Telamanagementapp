package com.planetsystems.tela.managementapp.client.presenter.staffdailytask;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

class StaffDailyTaskView extends ViewImpl implements StaffDailyTaskPresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private ControlsPane controlsPane;
	private CreateStaffDailyTaskPane createStaffDailyTaskPane;
	private ViewStaffDailyAttendanceTaskPane viewStaffDailyAttendanceTaskPane;
	private StaffDailyAttendancePane staffDailyAttendancePane;

	public final static String STAFF_DAILY_ATTENDANCES = "School Staff Daily Task Attendance";
	public final static String ADD_STAFF_DAILY_ATTENDANCE_TASK = "Create Staff Daily Tasks";
	public final static String VIEW_STAFF_DAILY_ATTENDANCE_TASK = "View Staff Daily Tasks";
	
	TabSet tabSet;
	
	

    @Inject
    StaffDailyTaskView() {
    	panel = new VLayout(); 
    	controlsPane = new ControlsPane();
    	createStaffDailyTaskPane = new CreateStaffDailyTaskPane();
    	viewStaffDailyAttendanceTaskPane = new ViewStaffDailyAttendanceTaskPane();
    	staffDailyAttendancePane = new StaffDailyAttendancePane();
    	
    	
    	tabSet = new TabSet();
    	Tab tab1 = new Tab(STAFF_DAILY_ATTENDANCES);
    	tab1.setPane(staffDailyAttendancePane);
    	
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

	public CreateStaffDailyTaskPane getCreateStaffDailyTaskPane() {
		return createStaffDailyTaskPane;
	}

	public void setCreateStaffDailyTaskPane(CreateStaffDailyTaskPane createStaffDailyTaskPane) {
		this.createStaffDailyTaskPane = createStaffDailyTaskPane;
	}

	public ViewStaffDailyAttendanceTaskPane getViewStaffDailyAttendanceTaskPane() {
		return viewStaffDailyAttendanceTaskPane;
	}

	public StaffDailyAttendancePane getStaffDailyAttendancePane() {
		return staffDailyAttendancePane;
	}

	public ControlsPane getControlsPane() {
		return controlsPane;
	}

	public TabSet getTabSet() {
		return tabSet;
	}

}
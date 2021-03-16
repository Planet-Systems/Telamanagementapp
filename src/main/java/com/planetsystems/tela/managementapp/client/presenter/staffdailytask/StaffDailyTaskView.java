package com.planetsystems.tela.managementapp.client.presenter.staffdailytask;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.presenter.region.DistrictPane;
import com.planetsystems.tela.managementapp.client.presenter.region.RegionPane;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

class StaffDailyTaskView extends ViewImpl implements StaffDailyTaskPresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private StaffDailyTaskPane staffDailyTaskPane;
	private ViewStaffDailyAttendanceTaskPane viewStaffDailyAttendanceTaskPane;

	public final static String STAFF_DAILY_ATTENDANCE = "DAILY_ATTENDANCE Tasks";
	public final static String ADD_STAFF_DAILY_ATTENDANCE_TASK = "ADD_STAFF_DAILY_ATTENDANCE_TASK";
	
	

    @Inject
    StaffDailyTaskView() {
    	panel = new VLayout(); 	
    	staffDailyTaskPane = new StaffDailyTaskPane();
    	viewStaffDailyAttendanceTaskPane = new ViewStaffDailyAttendanceTaskPane();
    	
        panel.addMember(staffDailyTaskPane);
    	panel.setWidth100();
		panel.setHeight("90%");
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);
      
    }
    
    public Widget asWidget() {
		return panel;
	}

	public StaffDailyTaskPane getStaffDailyTaskPane() {
		return staffDailyTaskPane;
	}

    
    

}
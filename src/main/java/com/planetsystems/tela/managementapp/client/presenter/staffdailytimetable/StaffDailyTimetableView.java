package com.planetsystems.tela.managementapp.client.presenter.staffdailytimetable;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

class StaffDailyTimetableView extends ViewImpl implements StaffDailyTimtablePresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private ControlsPane controlsPane;
	private CreateStaffDailyTimetableLessonPane createStaffDailyTimetableLessonPane;
	private ViewStaffDailyTimetableLessonPane viewStaffDailyTimetableLessonPane;
	private StaffDailyTimetablePane staffDailyTimetablePane;

	public final static String STAFF_DAILY_TIMETABLE = "School Staff Daily Timetable";
	public final static String ADD_STAFF_DAILY_TIMETABLE_LESSON = "Create Staff Daily Lessons";
	public final static String VIEW_STAFF_DAILY_TIMETABLE_LESSON = "View Staff Daily Lessons";
	
	TabSet tabSet;
	
	

    @Inject
    StaffDailyTimetableView() {
    	panel = new VLayout(); 
    	controlsPane = new ControlsPane();
    	createStaffDailyTimetableLessonPane = new CreateStaffDailyTimetableLessonPane();
    	viewStaffDailyTimetableLessonPane = new ViewStaffDailyTimetableLessonPane();
    	staffDailyTimetablePane = new StaffDailyTimetablePane();
    	
    	
    	tabSet = new TabSet();
    	Tab tab1 = new Tab(STAFF_DAILY_TIMETABLE);
    	tab1.setPane(staffDailyTimetablePane);
    	
    	tabSet.addTab(tab1);
    	
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

	public VLayout getPanel() {
		return panel;
	}

	public ControlsPane getControlsPane() {
		return controlsPane;
	}

	public CreateStaffDailyTimetableLessonPane getCreateStaffDailyTimetableLessonPane() {
		return createStaffDailyTimetableLessonPane;
	}

	public ViewStaffDailyTimetableLessonPane getViewStaffDailyTimetableLessonPane() {
		return viewStaffDailyTimetableLessonPane;
	}

	public StaffDailyTimetablePane getStaffDailyTimetablePane() {
		return staffDailyTimetablePane;
	}

	public TabSet getTabSet() {
		return tabSet;
	}

    

}
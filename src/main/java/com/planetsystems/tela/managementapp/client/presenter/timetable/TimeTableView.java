package com.planetsystems.tela.managementapp.client.presenter.timetable;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.presenter.staffdailytask.CreateStaffDailyTaskPane;
import com.planetsystems.tela.managementapp.client.presenter.staffdailytask.StaffDailyAttendancePane;
import com.planetsystems.tela.managementapp.client.presenter.staffdailytask.ViewStaffDailyAttendanceTaskPane;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

class TimeTableView extends ViewImpl implements TimeTablePresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private TimetablePane timetablePane;

	private ControlsPane controlsPane;
	private TabSet tabSet;
	
	private CreateTimeTablePane createTimeTablePane;
	private ViewTimeTableLessonsPane viewTimeTableLessonsPane;
	

	public static final String TIME_TABLES_TAB = "TimeTables";
	public static final String VIEW_TIME_TABLE_LESSON_TAB = "VIEW_TIME_TABLE_LESSON_TAB";
	public static final String CRREATE_TIME_TABLE_LESSON_TAB = "CRREATE_TIME_TABLE_LESSON_TAB";
	
	

    @Inject
    TimeTableView() {
    	panel = new VLayout();
    	timetablePane = new TimetablePane();
    	controlsPane = new ControlsPane();
    	
    	Tab tab = new Tab();
    	tab.setTitle(TIME_TABLES_TAB);
    	tab.setPane(timetablePane);
    	
    	tabSet =  new TabSet();
        tabSet.addTab(tab);
        
        VLayout layout = new VLayout();
    	layout.setHeight100();
    	layout.setWidth100();
    	
    	layout.addMember(controlsPane);
    	layout.addMember(tabSet);
        
    	panel.addMember(layout);
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


	public TimetablePane getTimetablePane() {
		return timetablePane;
	}


	public TabSet getTabSet() {
		return tabSet;
	}


	public CreateTimeTablePane getCreateTimeTablePane() {
		return createTimeTablePane;
	}


	
	
	
	
	

}
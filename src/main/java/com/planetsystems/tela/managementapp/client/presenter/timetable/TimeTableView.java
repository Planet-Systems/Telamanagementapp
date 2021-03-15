package com.planetsystems.tela.managementapp.client.presenter.timetable;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

class TimeTableView extends ViewImpl implements TimeTablePresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private TimetablePane timetablePane;
	private VLayout bodyLayout;
	private VLayout controlsTabsLayout;
	private ControlsPane controlsPane;
	private TabSet timeTableTabset;

	public static final String TIME_TABLE_TAB = "TimeTable Lessons";
	public static final String TIME_TABLE_LESSON_TAB = "LESSON_TAB";

    @Inject
    TimeTableView() {
    	panel = new VLayout();
    	timetablePane = new TimetablePane();
    	controlsPane = new ControlsPane();
    	bodyLayout = new VLayout();
    	bodyLayout.setHeight100();
    	bodyLayout.setWidth100();
    	controlsTabsLayout = new VLayout();
    	
    	Tab lessonTab = new Tab();
    	lessonTab.setTitle(TIME_TABLE_TAB);
    	lessonTab.setPane(timetablePane);
    	
    	timeTableTabset =  new TabSet();
        timeTableTabset.addTab(lessonTab);
        
        controlsTabsLayout.addMembers(controlsPane , timeTableTabset);
    	
        bodyLayout.setMembers(controlsTabsLayout);
        
    	panel.addMember(bodyLayout);
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


	public TabSet getTimeTableTabset() {
		return timeTableTabset;
	}


	public VLayout getBodyLayout() {
		return bodyLayout;
	}
	
	
	
	

}
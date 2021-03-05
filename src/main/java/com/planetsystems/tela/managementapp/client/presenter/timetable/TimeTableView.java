package com.planetsystems.tela.managementapp.client.presenter.timetable;

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

class TimeTableView extends ViewImpl implements TimeTablePresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private TimetablePane timetablePane;
	private ControlsPane controlsPane;
	private TabSet timeTableTabset;

	public static final String TIME_TABLE_TAB = "TimeTable Lessons";
	public static final String TIME_TABLE_LESSON_TAB = "LESSON_TAB";

    @Inject
    TimeTableView() {
    	panel = new VLayout();
    	timetablePane = new TimetablePane();
    	controlsPane = new ControlsPane();
    	
    	Tab lessonTab = new Tab();
    	lessonTab.setTitle(TIME_TABLE_TAB);
    	lessonTab.setPane(timetablePane);
    	
    	timeTableTabset =  new TabSet();
        timeTableTabset.addTab(lessonTab);
    	
    	panel.addMember(controlsPane);
    	panel.addMember(timeTableTabset);
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
	
	
	

}
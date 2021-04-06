package com.planetsystems.tela.managementapp.client.presenter.staffattendance;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

class StaffAttendanceView extends ViewImpl implements StaffAttendancePresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private ClockOutPane clockOutPane;
	private ClockInPane clockInPane;
	
	private ControlsPane controlsPane;
	private TabSet tabSet;
	
	public static final String CLOCKIN_TAB_TITLE="Clock In";
	public static final String CLOCKOUT_TAB_TITLE="Clock Out";

    @Inject
    StaffAttendanceView() {
    	panel = new VLayout();
    	clockOutPane = new ClockOutPane();
    	clockInPane = new ClockInPane();
    	
    	controlsPane = new ControlsPane();
    	tabSet = new TabSet();
    	
    	Tab clockInTab = new Tab();
    	clockInTab.setTitle(CLOCKIN_TAB_TITLE);
    	clockInTab.setPane(clockInPane);
    	
    	Tab clockOutTab = new Tab();
    	clockOutTab.setTitle(CLOCKOUT_TAB_TITLE);
    	clockOutTab.setPane(clockOutPane);
    
    	
    	tabSet.addTab(clockInTab);
    	tabSet.addTab(clockOutTab);


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

	public ControlsPane getControlsPane() {
		return controlsPane;
	}

	public TabSet getTabSet() {
		return tabSet;
	}

	public ClockOutPane getClockOutPane() {
		return clockOutPane;
	}

	public ClockInPane getClockInPane() {
		return clockInPane;
	}

    
}
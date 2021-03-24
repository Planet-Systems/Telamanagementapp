package com.planetsystems.tela.managementapp.client.presenter.timeattendancesupervision;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

class TimeAttendanceSupervisionView extends ViewImpl implements TimeAttendanceSupervisionPresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private ControlsPane controlsPane;
	private ClockInPane clockInPane;
	private ClockOutPane clockOutPane;
	private AbsentPane absentPane;
	private TabSet tabSet;
	
	public static final String CLOCKED_IN="Clocked Ins";
	public static final String CLOCKED_OUT="Clocked Outs";
	public static final String ABSENT="Absents";
	

    @Inject
    TimeAttendanceSupervisionView() {
    	panel = new VLayout();
    	controlsPane = new ControlsPane();
    	tabSet = new TabSet();
    	
    	clockInPane = new ClockInPane();
    	clockOutPane = new ClockOutPane();
    	absentPane = new AbsentPane();
    	
    	
    	Tab tab = new Tab();
    	tab.setTitle(CLOCKED_IN);
    	tab.setPane(clockInPane);
    	
    	
    	Tab tab2 = new Tab();
    	tab2.setTitle(CLOCKED_OUT);
    	tab2.setPane(clockOutPane);
    	
    	Tab tab3 = new Tab();
    	tab3.setTitle(ABSENT);
    	tab3.setPane(absentPane);
   
    
    	tabSet.addTab(tab);
    	tabSet.addTab(tab2);
    	tabSet.addTab(tab3);
    
    	
    	///panel.addMember(controlsPane);
    	panel.addMember(tabSet);
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

	public ClockInPane getClockInPane() {
		return clockInPane;
	}

	public ClockOutPane getClockOutPane() {
		return clockOutPane;
	}

	public AbsentPane getAbsentPane() {
		return absentPane;
	}

	public TabSet getTabSet() {
		return tabSet;
	}
    
    

}
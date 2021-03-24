package com.planetsystems.tela.managementapp.client.presenter.academicyear;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

class AcademicYearView extends ViewImpl implements AcademicYearPresenter.MyView {
    
	private static final String DEFAULT_MARGIN = "0px";
	
	private AcademicYearPane academicYearPane;
	private AcademicTermPane academicTermPane;

	private VLayout panel;
	private ControlsPane controlsPane;
	private TabSet tabSet;

	
	public static final String ACADEMIC_YEAR_TAB_TITLE="Academic Year";
	public static final String ACADEMIC_TERM_TAB_TITLE="Academic Term";
	
	private AcademicYearDashboardPane academicYearDashboardPane;
	
	VLayout vlayout;
   
 @Inject	
  AcademicYearView() {
    
    	panel = new VLayout();
    	academicYearPane = new AcademicYearPane();
    	academicTermPane = new AcademicTermPane();
    	academicYearDashboardPane = new AcademicYearDashboardPane();
    	controlsPane = new ControlsPane();
    	vlayout=new VLayout();
    	
    	vlayout.setMembers(academicTermPane);

		tabSet = new TabSet();
	

		Tab academicYearTab = new Tab();
		academicYearTab.setPane(academicYearPane);
		academicYearTab.setTitle(ACADEMIC_YEAR_TAB_TITLE);

		Tab academicTermTab = new Tab();
		//tab2.setPane(assessmentPeriodPane);
		academicTermTab.setPane(vlayout);
		academicTermTab.setTitle(ACADEMIC_TERM_TAB_TITLE);

		tabSet.addTab(academicYearTab);
		tabSet.addTab(academicTermTab);

		tabSet.setMargin(0);
		tabSet.setPadding(0);
		
		panel.addMember(controlsPane);
		panel.addMembers(tabSet);//(tabSet);
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

	public ControlsPane getControlsPane() {
		return controlsPane;
	}

	public AcademicYearPane getAcademicYearPane() {
		return academicYearPane;
	}

	
	public AcademicTermPane getAcademicTermPane() {
		return academicTermPane;
	}

	public VLayout getVlayout() {
		return vlayout;
	}

	
}
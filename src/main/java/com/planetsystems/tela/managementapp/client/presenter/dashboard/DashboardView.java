package com.planetsystems.tela.managementapp.client.presenter.dashboard;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

class DashboardView extends ViewImpl implements DashboardPresenter.MyView {
  
	private VLayout panel;
	
	private static final String DEFAULT_MARGIN = "0px";
	
	private ControlsPane controlsPane;
	
	
	private TabSet tabSet;

	public static final String SUMMARY = "Summary";
	
	public static final String PRIMARY = "Primary Schools";
	public static final String SECONDARY = "Secondary Schools";
	public static final String INSTITUTIONS = "Institutions";
	
	private DashboardPane summaryDashboard; 
	private DashboardPane primaryDashboard; 
	private DashboardPane secondaryDashboard; 
	private DashboardPane institutionDashboard; 
	
    @Inject
    DashboardView() {
    	
    	panel = new VLayout(); 
    	controlsPane=new ControlsPane();
    	summaryDashboard = new DashboardPane();
    	
    	primaryDashboard = new DashboardPane();
    	secondaryDashboard = new DashboardPane();
    	institutionDashboard = new DashboardPane();
		
		tabSet = new TabSet();

		Tab tab1 = new Tab();
		tab1.setTitle(SUMMARY);
		tab1.setPane(summaryDashboard);

		Tab tab2 = new Tab();
		tab2.setTitle(PRIMARY);
		tab2.setPane(primaryDashboard);
		
		Tab tab3 = new Tab();
		tab3.setTitle(SECONDARY);
		tab3.setPane(secondaryDashboard);
		
		Tab tab4 = new Tab();
		tab4.setTitle(INSTITUTIONS);
		tab4.setPane(institutionDashboard);

		tabSet.addTab(tab1);
		tabSet.addTab(tab2);
		tabSet.addTab(tab3);
		tabSet.addTab(tab4);
		 
		panel.addMember(controlsPane); 
		panel.addMember(tabSet);
		 
		panel.setWidth100();
		panel.setHeight100();
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);
    }
    
    @Override
    public void setInSlot(Object slot, IsWidget content) {
        if (slot == DashboardPresenter.SLOT_Dashboard) {
        	panel.setMembers((VLayout) content.asWidget());
        } else {
            super.setInSlot(slot, content);
        }
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

	public DashboardPane getSummaryDashboard() {
		return summaryDashboard;
	}

	public DashboardPane getPrimaryDashboard() {
		return primaryDashboard;
	}

	public DashboardPane getSecondaryDashboard() {
		return secondaryDashboard;
	}

	public DashboardPane getInstitutionDashboard() {
		return institutionDashboard;
	}
 
	
    
    
}
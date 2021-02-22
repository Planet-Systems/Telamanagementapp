package com.planetsystems.tela.managementapp.client.presenter.dashboard;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.smartgwt.client.widgets.layout.VLayout;

class DashboardView extends ViewImpl implements DashboardPresenter.MyView {
  
	private VLayout panel;
	private DashboardPane dashboardPane;
	private static final String DEFAULT_MARGIN = "0px";
	
    @Inject
    DashboardView() {
    	panel = new VLayout();
		
		
		
		dashboardPane = new DashboardPane();
		panel.addMember(dashboardPane);
		
		
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
    
    
}
package com.planetsystems.tela.managementapp.client.presenter.dashboard;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.VLayout;

class DashboardView extends ViewImpl implements DashboardPresenter.MyView {
  
	private VLayout panel;
	private DashboardPane dashboardPane;
	private IButton messsageBtn;
	private static final String DEFAULT_MARGIN = "0px";
	
	private ControlsPane controlsPane;
	
    @Inject
    DashboardView() {
    	panel = new VLayout(); 
    	controlsPane=new ControlsPane();
		dashboardPane = new DashboardPane(); 
		
		messsageBtn = new IButton("Message");
		 
		panel.addMember(controlsPane);
		panel.addMember(messsageBtn);
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

	public DashboardPane getDashboardPane() {
		return dashboardPane;
	}

	public ControlsPane getControlsPane() {
		return controlsPane;
	}

	public IButton getMesssageBtn() {
		return messsageBtn;
	}
	
	
	
	
    
    
}
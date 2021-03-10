package com.planetsystems.tela.managementapp.client.presenter.systemuser;

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

class SystemUserView extends ViewImpl implements SystemUserPresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	private VLayout panel;
	private SystemUserPane systemUserPane;
	private ControlsPane controlsPane;
	
    @Inject
    SystemUserView() {
    	panel = new VLayout();
    	controlsPane = new ControlsPane();
    	systemUserPane = new SystemUserPane();

    	VLayout layout = new VLayout();
    	layout.setWidth100();
    	layout.setHeight100();
    	layout.addMembers(controlsPane , systemUserPane);
    	
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

	public SystemUserPane getSystemUserPane() {
		return systemUserPane;
	}

	
	
 
}
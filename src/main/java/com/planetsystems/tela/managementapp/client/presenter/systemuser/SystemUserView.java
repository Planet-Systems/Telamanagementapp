package com.planetsystems.tela.managementapp.client.presenter.systemuser;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

class SystemUserView extends ViewImpl implements SystemUserPresenter.MyView {
	private static final String DEFAULT_MARGIN = "0px";
	
	private VLayout panel;
	private ControlsPane controlsPane;
	private SystemUserPane systemUserPane;
	 
	private SystemMenuPane systemMenuPane;
	
	private UserGroupPane userGroupPane;
	
	private TabSet tabSet;
	
    @Inject
    SystemUserView() {
    	panel = new VLayout();
    	controlsPane = new ControlsPane();
    	systemUserPane = new SystemUserPane();
    	userGroupPane=new UserGroupPane();
    	systemMenuPane=new SystemMenuPane();

    	Tab userRolePaneTab = new Tab();
		userRolePaneTab.setPane(userGroupPane);
		userRolePaneTab.setTitle("System user groups");


		Tab usersPaneTab = new Tab();
		usersPaneTab.setPane(systemUserPane);
		usersPaneTab.setTitle("System user details");
		
		Tab systemMenuPaneTab = new Tab();
		systemMenuPaneTab.setPane(systemMenuPane);
		systemMenuPaneTab.setTitle("System Menu Setup");

		tabSet = new TabSet();

		tabSet.addTab(userRolePaneTab);
		tabSet.addTab(usersPaneTab); 
		tabSet.addTab(systemMenuPaneTab);

		tabSet.setMargin(0);
		tabSet.setPadding(0);

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

	public SystemUserPane getSystemUserPane() {
		return systemUserPane;
	}

	public SystemMenuPane getSystemMenuPane() {
		return systemMenuPane;
	}

	public UserGroupPane getUserGroupPane() {
		return userGroupPane;
	}

	public TabSet getTabSet() {
		return tabSet;
	}

	
	
 
}
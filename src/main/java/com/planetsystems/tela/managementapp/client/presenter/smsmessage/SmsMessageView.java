package com.planetsystems.tela.managementapp.client.presenter.smsmessage;

import javax.inject.Inject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.presenter.smsmessage.smsstaff.SchoolStaffPane;
import com.planetsystems.tela.managementapp.client.presenter.smsmessage.smssystemuser.SystemUserPane;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.tab.Tab;
import com.smartgwt.client.widgets.tab.TabSet;

class SmsMessageView extends ViewImpl implements SmsMessagePresenter.MyView {

	private VLayout panel;
	private static final String DEFAULT_MARGIN = "0px";
	
	public static final String SCHOOL_STAFF_TAB_TITLE="School Staff";
	public static final String SYSTEM_USER_TAB_TITLE="System User";
	
	private SchoolStaffPane schoolStaffPane;
	private SystemUserPane systemUserPane;
	private TabSet tabSet;

	
    @Inject
    SmsMessageView() {
    	panel = new VLayout();

    	schoolStaffPane = new SchoolStaffPane();
    	systemUserPane = new SystemUserPane();
    	tabSet = new TabSet();
    	
    	Tab schoolStaffTab = new Tab();
    	schoolStaffTab.setTitle(SCHOOL_STAFF_TAB_TITLE);
    	schoolStaffTab.setPane(schoolStaffPane);
    	
    	Tab systemUserTab = new Tab();
    	systemUserTab.setTitle(SYSTEM_USER_TAB_TITLE);
    	systemUserTab.setPane(systemUserPane);
    
    	tabSet.addTab(schoolStaffTab);
    	tabSet.addTab(systemUserTab);

    	panel.setMembers(tabSet);
    	panel.setWidth100();
		panel.setHeight("90%");
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);
    }
    
    
    public Widget asWidget() {
		return panel;
	}

	public SchoolStaffPane getSchoolStaffPane() {
		return schoolStaffPane;
	}

	public SystemUserPane getSystemUserPane() {
		return systemUserPane;
	}


	public TabSet getTabSet() {
		return tabSet;
	}
	
}
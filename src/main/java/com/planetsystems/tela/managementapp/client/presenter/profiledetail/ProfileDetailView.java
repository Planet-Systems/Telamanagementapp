package com.planetsystems.tela.managementapp.client.presenter.profiledetail;

import javax.inject.Inject;

import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.Masthead;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

class ProfileDetailView extends ViewImpl implements ProfileDetailPresenter.MyView {

	private static final int NORTH_HEIGHT = 20;
	private static final String DEFAULT_MARGIN = "0px";
	private static final int DEFAULT_MENU_WIDTH = 70;

	private VLayout panel;//Main panel that holds the entire ui
    private ProfileDetailPane profileDetailPane;  
    
	private Masthead mastHead;//header part horizontal layout
	
    @Inject
    ProfileDetailView() {
       panel = new VLayout();
       profileDetailPane = new ProfileDetailPane();
		mastHead = new Masthead();
       
       HLayout northLayout = new HLayout();
		northLayout.setHeight(NORTH_HEIGHT);
		
		VLayout vLayout = new VLayout();
		vLayout.addMember(mastHead);

		northLayout.addMember(vLayout);
       
       
    
	panel.addMember(northLayout);	
	panel.addMember(profileDetailPane); 
	panel.setWidth100();
	panel.setHeight100();
	Window.enableScrolling(false);
	Window.setMargin(DEFAULT_MARGIN);
    }
    
    
	public Widget asWidget() {
		return panel;
	}


	public ProfileDetailPane getProfileDetailPane() {
		return profileDetailPane;
	}


	public Masthead getMastHead() {
		return mastHead;
	}


    
	
	
}
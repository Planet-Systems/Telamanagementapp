package com.planetsystems.tela.managementapp.client.presenter.main;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;
import com.planetsystems.tela.managementapp.client.widget.MainStatusBar;
import com.planetsystems.tela.managementapp.client.widget.Masthead;
import com.planetsystems.tela.managementapp.client.widget.NavigationPane;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

class MainView extends ViewImpl implements MainPresenter.MyView {

	private static final int NORTH_HEIGHT = 20;
	private static final String DEFAULT_MARGIN = "0px";
	private static final int DEFAULT_MENU_WIDTH = 70;

	private VLayout panel;//Main panel that holds the entire ui
	private VLayout centerLayout;//panel holding dynamic content

	private Masthead mastHead;//header part horizontal layout
	private NavigationPane navigationPane;//holds side menus
	private MainStatusBar mainStatusBar;//footer status bar

	
	MainView() {
		super();
		  
		mastHead = new Masthead();
		mainStatusBar = new MainStatusBar();
		navigationPane=new NavigationPane();
		centerLayout = new VLayout();
		panel = new VLayout();
		
		
		panel.setWidth100();
		panel.setHeight100();

		
		centerLayout.setWidth100();
		centerLayout.setHeight100();
		centerLayout.setBackgroundColor("white");


		HLayout northLayout = new HLayout();
		northLayout.setHeight(NORTH_HEIGHT);

		VLayout vLayout = new VLayout();
		vLayout.addMember(mastHead);

		northLayout.addMember(vLayout);

		navigationPane.setCanDragResize(true);
		navigationPane.setShowResizeBar(true);
		VLayout westLayout = this.navigationPane;

		HLayout southLayout = new HLayout();
		southLayout.setMembers(westLayout, centerLayout);

		panel.addMember(northLayout);
		panel.addMember(southLayout);
		panel.addMember(mainStatusBar); 
		Window.enableScrolling(false);
		Window.setMargin(DEFAULT_MARGIN);

		 
	}

	@Override
	public void setInSlot(Object slot, IsWidget content) {
		if (slot == MainPresenter.SLOT_Main) {
			 //main.setWidget(content);
			centerLayout.setMembers((VLayout) content.asWidget());
			 
		} else {
			super.setInSlot(slot, content);
		}
	}

	public Widget asWidget() {
		return panel;
	}


	public Masthead getMastHead() {
		return mastHead;
	}


	public NavigationPane getNavigationPane() {
		return navigationPane;
	}


	public MainStatusBar getMainStatusBar() {
		return mainStatusBar;
	}
}
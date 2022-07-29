/**
 * 
 */
package com.planetsystems.tela.managementapp.client.widget;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class Masthead extends VLayout {

	private static final int MASTHEAD_HEIGHT = 10;
	private static final String WEST_WIDTH = "50%";
	private static final String EAST_WIDTH = "50%";
	private static final String CENTER_WIDTH = "20%";
 
	private Label logedInUser;
	private Label logedInUserRole;

	private Avata userProfile;
	private Img menu;
	

	private static final String LOGO = "moes_logo.png";
	
	private static final String LOGO2 = "telalogo.png";

	public Masthead() {
		super();
   
		logedInUser = new Label();
		logedInUser.setContents("");
		logedInUser.setAutoHeight();
		logedInUser.setMargin(15);
		logedInUser.setStyleName("crm-Masthead-loginedUser");
		logedInUser.setAlign(Alignment.RIGHT);

		logedInUserRole = new Label();
		logedInUserRole.setContents("--");
		logedInUserRole.setAutoHeight();
		logedInUserRole.setMargin(15);
		logedInUserRole.setStyleName("crm-Masthead-loginedUser");
		logedInUserRole.setAlign(Alignment.RIGHT);


		userProfile = new Avata();

		menu = new Img();
		menu = new Img("menu-4-48.png");
		menu.setID("menu");
		menu.setCursor(Cursor.HAND);
		menu.setWidth(30);
		menu.setHeight(35);
		menu.setStyleName("menu-switcher");
		// menu.setMargin(5);

		Img logo = new Img(LOGO, 60, 50);
		logo.setMargin(5);
		
		Img logo2 = new Img(LOGO2, 100, 50);
		logo2.setMargin(5);
	

		// initialize the West layout container
		HLayout westLayout = new HLayout();
		westLayout.setWidth(WEST_WIDTH);
		westLayout.setHeight(MASTHEAD_HEIGHT);
//		westLayout.addMember(menu);
	    //westLayout.addMember(logo);
	    westLayout.addMember(logo2);


		VLayout eastLayout = new VLayout();
		eastLayout.setAlign(Alignment.RIGHT);
		eastLayout.setAutoHeight();
		eastLayout.setWidth(100);
		eastLayout.setMembersMargin(1);
		eastLayout.setMargin(0);
		eastLayout.setMembers(userProfile);
		
		// eastLayout.addMember(logedInUser);
		// eastLayout.addMember(logout);
		// eastLayout.addMember(logedInUserRole);

		HLayout hLayout = new HLayout();
		hLayout.addMember(westLayout);
		hLayout.addMember(eastLayout);

		hLayout.setMargin(2);

		Label banner2 = new Label();
		banner2.setContents("<div style=\"background: #222222;height: 2px;\"></div>\r\n"
				+ "<div style=\"background: #FFEB3B;height: 2px;\"></div>\r\n"
				+ "<div style=\"background: red;height: 2px;\"></div>");
		banner2.setAutoHeight();
		banner2.setAlign(Alignment.LEFT);
		banner2.setWidth100();

		VLayout layout = new VLayout();
		layout.addMember(hLayout);
		layout.addMember(banner2);
		layout.setMembersMargin(0);
		layout.setAutoHeight();

		this.addMember(layout);
		this.setStyleName("crm-Masthead");
		// this.setAutoHeight();
		this.setHeight(MASTHEAD_HEIGHT);
		//this.setBackgroundColor("#00698C");
		this.setBackgroundColor("#0087c3");

	}

	public Label getLogedInUser() {
		return logedInUser;
	}

	public void setLogedInUser(Label logedInUser) {
		this.logedInUser = logedInUser;
	}

	public Label getLogedInUserRole() {
		return logedInUserRole;
	}

	public Avata getUserProfile() {
		return userProfile;
	}

	public Img getMenu() {
		return menu;
	}


	
	
	

	

	
}

/**
 * 
 */
package com.planetsystems.tela.managementapp.client.widget;

import com.planetsystems.tela.managementapp.client.gin.SessionManager;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.IMenuButton;

public class Masthead extends VLayout {

	private static final int MASTHEAD_HEIGHT = 10;
	private static final String WEST_WIDTH = "50%";
	private static final String EAST_WIDTH = "50%";
	private static final String CENTER_WIDTH = "20%";

	// GOVERNMENT OF UGANDA REFUGEE RESPONSE MONITORING SYSTEM
	// private static final String NAME_LABEL = "OPM-DoR";
	private static final String NAME_LABEL = "URRMS";

	private Label logedInUser;
	private Label logedInUserRole;

	private Label logoutLabel;
	private Avata userProfile;
	private Img menu;
	

	private static final String LOGO = "logo2.png";

	public Masthead() {
		super();

		Label name = new Label();
		name.setStyleName("crm-Masthead-Name");
		name.setContents(NAME_LABEL);
		name.setAutoHeight();
		// name.setIcon(LOGO);

		Label slogan = new Label();
		// slogan.setContents("Refugee Response Monitoring & Evaluation E-Reporting");
		// slogan.setContents("Uganda Refugee Actors Monitoring System");
		slogan.setContents("Government of Uganda Refugee Response Monitoring System");
		// slogan.setContents("Soft lock");
		slogan.setAutoHeight();
		slogan.setStyleName("crm-Masthead-slogan");

		// initialize the East layout container

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

		logoutLabel = new Label("Logout");
		logoutLabel.setAutoHeight();
		logoutLabel.setAutoWidth();
		logoutLabel.setLayoutAlign(VerticalAlignment.CENTER);
		logoutLabel.setMargin(5);
		logoutLabel.setCursor(Cursor.NOT_ALLOWED);

		userProfile = new Avata();

		menu = new Img();
		menu = new Img("menu-4-48.png");
		menu.setID("menu");
		menu.setCursor(Cursor.HAND);
		menu.setWidth(30);
		menu.setHeight(35);
		menu.setStyleName("menu-switcher");
		// menu.setMargin(5);

		Img logo = new Img();
		logo = new Img(LOGO);
		logo.setID("logoImage");
		// logo.setMargin(5);
		// logo.setAlign(Alignment.RIGHT);
		logo.setHeight(50);
		logo.setWidth("70%");

		// initialize the West layout container
		HLayout westLayout = new HLayout();
		// westLayout.setHeight(MASTHEAD_HEIGHT);
		westLayout.setWidth(WEST_WIDTH);
		westLayout.setHeight(MASTHEAD_HEIGHT);
		// westLayout.setAutoWidth();
		westLayout.addMember(menu);
//		westLayout.addMember(logo);
		// westLayout.addMember(name);
		// westLayout.addMember(slogan);

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
		hLayout.addMember(logoutLabel);
		
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
		this.setBackgroundColor("#FFF101");

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

	public Label getLogoutLabel() {
		return logoutLabel;
	}

	
	
	

	

	
}

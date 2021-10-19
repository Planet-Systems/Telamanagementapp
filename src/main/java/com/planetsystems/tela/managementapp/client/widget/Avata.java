package com.planetsystems.tela.managementapp.client.widget;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.Cursor;
import com.smartgwt.client.widgets.Button;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class Avata extends VLayout {

	private static final String LOGO = "avata.jpg";

	private Img profileImage;
	
	private Button myAccount;

	public Avata() {
		super();

		profileImage = new Img(LOGO, 50, 45);
		profileImage.setID("profileImage");
		profileImage.setMargin(5);
		profileImage.setAlign(Alignment.RIGHT);
		profileImage.setCursor(Cursor.HAND);
		
		myAccount=new Button();
		myAccount.setTitle("My Account");
		myAccount.setStyleName("myaccount-button");
		   
		HLayout hLayout=new HLayout();
		hLayout.setAutoHeight();
		hLayout.setAutoWidth();
		hLayout.addMember(myAccount);

		this.setMembers(profileImage);

	}

	public VLayout getLayout() {
		return this;
	}

	public Img getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(Img profileImage) {
		this.profileImage = profileImage;
	}

}

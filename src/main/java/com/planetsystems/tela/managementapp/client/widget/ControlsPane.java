package com.planetsystems.tela.managementapp.client.widget;

import java.util.List;
 
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class ControlsPane extends ToolStrip {

	public ControlsPane() {
		this.setWidth100();
		this.setAlign(Alignment.RIGHT);
		this.setBackgroundColor("#ffffff");
	}
	
	public void addTitle(String title) {

		Label header = new Label();
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents(title);
		header.setWidth("100%");
		header.setAutoHeight();
		header.setAlign(Alignment.LEFT);

		this.addMember(header);
	}
	
	
	public void addMenuButtons(String title, List<MenuButton> buttons) {

		HLayout buttonLayout = new HLayout();
		buttonLayout.setAutoHeight();
		buttonLayout.setWidth100();
		buttonLayout.setMembersMargin(3);
		buttonLayout.setAlign(Alignment.RIGHT);

		for (MenuButton b : buttons) {
			buttonLayout.addMember(b);
		}

		Label header = new Label();
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents(title);
		header.setWidth("100%");
		header.setAutoHeight();
		header.setAlign(Alignment.LEFT);

		HLayout Layout = new HLayout();
		Layout.setAutoHeight();
		Layout.setWidth100();
		Layout.setMembersMargin(3);
		Layout.addMember(header);
		Layout.addMember(buttonLayout);

		this.setMembers(Layout);
	}

	public void addMenuButtons(List<MenuButton> buttons) {

		HLayout buttonLayout = new HLayout();
		buttonLayout.setAutoHeight();
		buttonLayout.setWidth100();
		buttonLayout.setMembersMargin(3);
		buttonLayout.setAlign(Alignment.RIGHT);

		for (MenuButton b : buttons) {
			buttonLayout.addMember(b);
		}

		this.setMembers(buttonLayout);
	}

	public void addMenuButton(MenuButton button) {
		this.addMember(button);
	}

	public void removeMenuButton(MenuButton button) {
		this.removeMember(button);
	}

}
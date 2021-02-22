package com.planetsystems.tela.managementapp.client.widget;

import java.util.List;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.toolbar.ToolStrip;

public class ControlsPane extends ToolStrip {

	public ControlsPane() {
		this.setWidth100();
		this.setAlign(Alignment.RIGHT);
		this.setBackgroundColor("#ffffff");
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
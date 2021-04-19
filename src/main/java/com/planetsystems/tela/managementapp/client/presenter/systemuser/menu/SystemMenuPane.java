package com.planetsystems.tela.managementapp.client.presenter.systemuser.menu;

import com.smartgwt.client.widgets.layout.VLayout;

public class SystemMenuPane extends VLayout {

	private SystemMenuListgrid listgrid;

	public SystemMenuPane() {

		super();

		listgrid = new SystemMenuListgrid();

		VLayout layout = new VLayout();
		layout.addMember(listgrid);
		this.addMember(layout);

	}

	public SystemMenuListgrid getListgrid() {
		return listgrid;
	}

}

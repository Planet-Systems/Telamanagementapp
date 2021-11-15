package com.planetsystems.tela.managementapp.client.presenter.systemuser.manage;

import com.planetsystems.tela.managementapp.client.presenter.systemuser.profile.AdministrativeUnitListGrid;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionAppearance;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SystemUserSchoolPane extends VLayout {

	private IButton addButton;
	private AdministrativeUnitListGrid administrativeUnitListGrid;

	public SystemUserSchoolPane() {
		super();

		addButton = new IButton("Add");

		HLayout controlButtonLayout = new HLayout();
		controlButtonLayout.setMembers(addButton);
		controlButtonLayout.setLayoutAlign(Alignment.RIGHT);
		controlButtonLayout.setAlign(Alignment.RIGHT);
		controlButtonLayout.setLayoutRightMargin(20);
		controlButtonLayout.setMembersMargin(5);
		controlButtonLayout.setAutoHeight();
		controlButtonLayout.setAutoWidth();
		controlButtonLayout.setMargin(5);

		administrativeUnitListGrid = new AdministrativeUnitListGrid();
		administrativeUnitListGrid.setSelectionAppearance(SelectionAppearance.CHECKBOX);
		administrativeUnitListGrid.setSelectionType(SelectionStyle.SIMPLE);

		this.addMember(controlButtonLayout);
		this.addMember(administrativeUnitListGrid);
		this.setHeight100();
		this.setWidth100();
	}

	public IButton getAddButton() {
		return addButton;
	}

	public AdministrativeUnitListGrid getAdministrativeUnitListGrid() {
		return administrativeUnitListGrid;
	}

}

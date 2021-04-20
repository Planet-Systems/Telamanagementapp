package com.planetsystems.tela.managementapp.client.presenter.filterpaneutils;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

public class FilterRegionDistrict extends VLayout {
	private ComboBox regionCombo;
	private ComboBox districtCombo;
	
	public FilterRegionDistrict() {
		super();

		Label schoolLabel = new Label("School: ");
		schoolLabel.setAutoHeight();
		schoolLabel.setAutoWidth();
		schoolLabel.setPadding(2);

		Label dayLabel = new Label("Day: ");
		dayLabel.setAutoHeight();
		dayLabel.setAutoWidth();
		dayLabel.setPadding(2);

		DynamicForm form = new DynamicForm();
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setColWidths("150", "250");
		form.setCellPadding(5);

		districtCombo = new ComboBox();
		districtCombo.setTitle("District");
		districtCombo.setHint("District");
		districtCombo.setShowHintInField(true);

		regionCombo = new ComboBox();
		regionCombo.setTitle("Region");
		regionCombo.setHint("Region");
		regionCombo.setShowHintInField(true);

		form.setFields(regionCombo, districtCombo);

		this.addMember(form);
		this.setWidth100();
		this.setAutoHeight();
	}

	public ComboBox getRegionCombo() {
		return regionCombo;
	}

	public ComboBox getDistrictCombo() {
		return districtCombo;
	}


	
	
}

package com.planetsystems.tela.managementapp.client.presenter.filterpaneutils;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.VLayout;

public class FilterRegionDistrictSchoolCategory extends VLayout {
	private ComboBox schoolCategoryCombo;
	private ComboBox districtCombo;
	private ComboBox regionCombo;
	 


	public FilterRegionDistrictSchoolCategory() {
		super();
		

		DynamicForm form = new DynamicForm();
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setColWidths("150", "250");
		form.setCellPadding(5);

		regionCombo = new ComboBox();
		regionCombo.setTitle("Sub-Region");
		regionCombo.setHint("Select");
		regionCombo.setShowHintInField(true);

		districtCombo = new ComboBox();
		districtCombo.setTitle("Local Government");
		districtCombo.setHint("District/City/Municipality");
		districtCombo.setShowHintInField(true);

		schoolCategoryCombo = new ComboBox();
		schoolCategoryCombo.setTitle("Foundation Body");
		schoolCategoryCombo.setHint("Select");
		schoolCategoryCombo.setShowHintInField(true);

		form.setFields(regionCombo, districtCombo, schoolCategoryCombo);

		this.addMember(form);
		this.setWidth100();
		this.setAutoHeight();
	}


	public ComboBox getSchoolCategoryCombo() {
		return schoolCategoryCombo;
	}


	public ComboBox getDistrictCombo() {
		return districtCombo;
	}


	public ComboBox getRegionCombo() {
		return regionCombo;
	}

	
	
	
}

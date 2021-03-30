package com.planetsystems.tela.managementapp.client.presenter.filterpaneutils;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.shared.DatePattern;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
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
		regionCombo.setTitle("Region");
		regionCombo.setHint("region");
		regionCombo.setShowHintInField(true);

		districtCombo = new ComboBox();
		districtCombo.setTitle("District");
		districtCombo.setHint("District");
		districtCombo.setShowHintInField(true);

		schoolCategoryCombo = new ComboBox();
		schoolCategoryCombo.setTitle("SchoolCategory");
		schoolCategoryCombo.setHint("school category");
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

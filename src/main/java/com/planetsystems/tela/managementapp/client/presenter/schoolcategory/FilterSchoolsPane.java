package com.planetsystems.tela.managementapp.client.presenter.schoolcategory;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;

public class FilterSchoolsPane extends HLayout {

	private ComboBox districtCombo;
	private ComboBox categoryCombo;
	
	
	public static final String DISTRICT_ID = "DISTRICT_ID";
	public static final String SCHOOL_CATEGORY_ID = "SCHOOL_CATEGORY_ID";
	
	
	public FilterSchoolsPane() {
		super();

		categoryCombo = new ComboBox();
		categoryCombo.setTitle("Category");
		categoryCombo.setHint("Select School Category");
		categoryCombo.setShowHintInField(true);
		
		districtCombo = new ComboBox();
		districtCombo.setTitle("District");
		districtCombo.setHint("Select A District");
		districtCombo.setShowHintInField(true);
		
		DynamicForm form = new DynamicForm();
		form.setFields(categoryCombo , districtCombo);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setCellPadding(10);
		form.setNumCols(2);
		form.setColWidths("80", "250");
		
		this.addMember(form);
		this.setAutoHeight();
		this.setWidth100();
		//this.setBorder("1px solid green");
	}

	public ComboBox getDistrictCombo() {
		return districtCombo;
	}

	public void setDistrictCombo(ComboBox districtCombo) {
		this.districtCombo = districtCombo;
	}

	public ComboBox getCategoryCombo() {
		return categoryCombo;
	}

	public void setCategoryCombo(ComboBox categoryCombo) {
		this.categoryCombo = categoryCombo;
	}
}

package com.planetsystems.tela.managementapp.client.presenter.academicyear;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;

public class FilterAcademicTermsPane extends HLayout {

	private ComboBox academicYearCombo;
	
	public FilterAcademicTermsPane() {
		super();

		academicYearCombo = new ComboBox();
		academicYearCombo.setTitle("Academic Year");
		academicYearCombo.setHint("Select Academic Year");
		academicYearCombo.setShowHintInField(true);
		
		DynamicForm form = new DynamicForm();
		form.setFields(academicYearCombo);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setCellPadding(10);
		form.setColWidths("80" , "250");
		
		this.addMember(form);
		this.setAutoHeight();
		this.setWidth100();
	}


	public ComboBox getAcademicYearCombo() {
		return academicYearCombo;
	}

	
	

}

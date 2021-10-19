package com.planetsystems.tela.managementapp.client.presenter.subjectcategory.subject;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;

public class FilterSubjectsPane extends HLayout {

	private ComboBox categoryCombo;
	
	public FilterSubjectsPane() {
		super();

		categoryCombo = new ComboBox();
		categoryCombo.setTitle("Category");
		categoryCombo.setHint("Select Subject Category");
		categoryCombo.setShowHintInField(true);
		
		
		DynamicForm form = new DynamicForm();
		form.setFields(categoryCombo);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setCellPadding(10);
		form.setNumCols(2);
		form.setColWidths("80" , "250");
		
		
		this.addMember(form);
		this.setAutoHeight();
		this.setWidth100();
	}
	
	

	public ComboBox getCategoryCombo() {
		return categoryCombo;
	}


}

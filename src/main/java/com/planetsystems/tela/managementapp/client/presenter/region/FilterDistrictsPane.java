package com.planetsystems.tela.managementapp.client.presenter.region;

import org.apache.bcel.generic.NEW;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;

public class FilterDistrictsPane extends HLayout {

	private ComboBox regionCombo;
	
	public FilterDistrictsPane() {
		super();

		regionCombo = new ComboBox();
		regionCombo.setTitle("Region");
		regionCombo.setHint("Select Region");
		regionCombo.setShowHintInField(true);
		
		DynamicForm form = new DynamicForm();
		form.setFields(regionCombo);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setCellPadding(10);
		form.setColWidths("80" , "250");
		
		this.addMember(form);
		this.setAutoHeight();
		this.setWidth100();
		//this.setBorder("1px solid green");
	}

	public ComboBox getRegionCombo() {
		return regionCombo;
	}
	

}

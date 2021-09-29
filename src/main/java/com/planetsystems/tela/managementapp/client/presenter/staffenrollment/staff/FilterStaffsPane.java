package com.planetsystems.tela.managementapp.client.presenter.staffenrollment.staff;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class FilterStaffsPane extends HLayout {

	private ComboBox districtCombo;
	private ComboBox schoolCombo;
	
	public static final String DISTRICT_ID = "DISTRICT_ID";
	public static final String SCHOOL_ID = "SCHOOL_ID";
	
	public FilterStaffsPane() {
		super();

		districtCombo = new ComboBox();
		districtCombo.setTitle("District");
		districtCombo.setHint("Select");
		districtCombo.setShowHintInField(true);
		
		
		schoolCombo = new ComboBox();
		schoolCombo.setTitle("School");
		schoolCombo.setHint("Select");
		schoolCombo.setShowHintInField(true);
		
		
		DynamicForm form = new DynamicForm();
		form.setFields(districtCombo , schoolCombo);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setCellPadding(10);
		form.setNumCols(2);
		form.setColWidths("100" , "300");
		 
		this.addMember(form);
		this.setAutoHeight();
		this.setWidth100();
	
	}
	

	public ComboBox getDistrictCombo() {
		return districtCombo;
	}

	public ComboBox getSchoolCombo() {
		return schoolCombo;
	}


}

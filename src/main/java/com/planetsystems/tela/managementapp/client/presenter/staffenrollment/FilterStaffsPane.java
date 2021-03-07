package com.planetsystems.tela.managementapp.client.presenter.staffenrollment;

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
	private IButton filterButton;
	
	public static final String DISTRICT_ID = "DISTRICT_ID";
	public static final String SCHOOL_ID = "SCHOOL_ID";
	
	public FilterStaffsPane() {
		super();

		districtCombo = new ComboBox();
		districtCombo.setTitle("District");
		districtCombo.setHint("Select A District");
		districtCombo.setShowHintInField(true);
		
		
		schoolCombo = new ComboBox();
		schoolCombo.setTitle("School");
		schoolCombo.setHint("Select A School");
		schoolCombo.setShowHintInField(true);
		
		
		DynamicForm form = new DynamicForm();
		form.setFields(districtCombo , schoolCombo);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setCellPadding(10);
		form.setNumCols(4);
		form.setColWidths("50" , "150" , "50" , "150");
		filterButton = new IButton("Filter");
		filterButton.setLayoutAlign(VerticalAlignment.CENTER);
		filterButton.disable();
		disableEnableFilterButton(districtCombo , schoolCombo , filterButton);
	
		HLayout layout = new HLayout();
		layout.setAutoHeight();
		layout.setWidth100();
		layout.addMembers(form , filterButton);
		
		
		this.addMember(layout);
		this.setAutoHeight();
		this.setWidth100();
	
	}
	
	
	private void disableEnableFilterButton(final ComboBox districtCombo, final ComboBox schoolCombo,final IButton filterButton) {;
	districtCombo.addChangedHandler(new ChangedHandler() {

		@Override
		public void onChanged(ChangedEvent event) {

			if (districtCombo.getValueAsString() != null && schoolCombo.getValueAsString() != null) {
                filterButton.setDisabled(false);
			}else {
				 filterButton.setDisabled(true);	
			}
		}
	});

	schoolCombo.addChangedHandler(new ChangedHandler() {

		@Override
		public void onChanged(ChangedEvent event) {
			if (districtCombo.getValueAsString() != null && schoolCombo.getValueAsString() != null) {
                filterButton.setDisabled(false);
			}else {
				filterButton.setDisabled(true);
			}
		}
	});

}
	

	public ComboBox getDistrictCombo() {
		return districtCombo;
	}

	public ComboBox getSchoolCombo() {
		return schoolCombo;
	}
	
	
	

}

package com.planetsystems.tela.managementapp.client.presenter.schoolcategory;

import org.apache.bcel.generic.NEW;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class FilterSchoolsPane extends HLayout {

	private ComboBox districtCombo;
	private ComboBox categoryCombo;
	private IButton filterButton;
	
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
		form.setNumCols(4);
		form.setColWidths("50" , "150" , "50" , "150");
		
		filterButton = new IButton("Filter");
		filterButton.setLayoutAlign(VerticalAlignment.CENTER);
		filterButton.disable();
		disableEnableFilterButton(categoryCombo , districtCombo , filterButton);
	
		HLayout layout = new HLayout();
		layout.setAutoHeight();
		layout.setWidth100();
		layout.addMembers(form , filterButton);
		
		
		this.addMember(layout);
		this.setAutoHeight();
		this.setWidth100();
		//this.setBorder("1px solid green");
	}
	
	private void disableEnableFilterButton(final ComboBox categoryCombo, final ComboBox districtCombo,final IButton filterButton) {;
	categoryCombo.addChangedHandler(new ChangedHandler() {

		@Override
		public void onChanged(ChangedEvent event) {

			if (categoryCombo.getValueAsString() != null && districtCombo.getValueAsString() != null) {
                filterButton.setDisabled(false);
			}else {
				 filterButton.setDisabled(true);	
			}
		}
	});

	districtCombo.addChangedHandler(new ChangedHandler() {

		@Override
		public void onChanged(ChangedEvent event) {
			if (categoryCombo.getValueAsString() != null && districtCombo.getValueAsString() != null) {
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

	public void setDistrictCombo(ComboBox districtCombo) {
		this.districtCombo = districtCombo;
	}

	public ComboBox getCategoryCombo() {
		return categoryCombo;
	}

	public void setCategoryCombo(ComboBox categoryCombo) {
		this.categoryCombo = categoryCombo;
	}

	public IButton getFilterButton() {
		return filterButton;
	}

	


}

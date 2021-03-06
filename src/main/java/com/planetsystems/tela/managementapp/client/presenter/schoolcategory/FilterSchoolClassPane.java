package com.planetsystems.tela.managementapp.client.presenter.schoolcategory;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;
import com.smartgwt.client.widgets.layout.HLayout;

public class FilterSchoolClassPane extends HLayout {

	private ComboBox academicTermCombo;
	private ComboBox schoolCombo;
	private IButton filterButton;
	public static final String ACADEMIC_TERM_ID = "ACADEMIC_TERM_ID";
	public static final String SCHOOL_ID = "SCHOOL_ID";

	public FilterSchoolClassPane() {
		super();

		academicTermCombo = new ComboBox();
		academicTermCombo.setTitle("Academic Term");
		academicTermCombo.setHint("Select A Term");
		academicTermCombo.setShowHintInField(true);

		schoolCombo = new ComboBox();
		schoolCombo.setTitle("School");
		schoolCombo.setHint("Select A School");
		schoolCombo.setShowHintInField(true);

		DynamicForm form = new DynamicForm();
		form.setFields(academicTermCombo, schoolCombo);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setCellPadding(10);
		form.setNumCols(4);
		form.setColWidths("50", "150", "50", "150");

		filterButton = new IButton("Filter");
		filterButton.setLayoutAlign(VerticalAlignment.CENTER);
		filterButton.disable();
		disableEnableFilterButton(academicTermCombo, schoolCombo, filterButton);

		HLayout layout = new HLayout();
		layout.setAutoHeight();
		layout.setWidth100();
		layout.addMembers(form, filterButton);

		this.addMember(layout);
		this.setAutoHeight();
		this.setWidth100();
		// this.setBorder("1px solid green");
	}

	private void disableEnableFilterButton(final ComboBox academicTermCombo, final ComboBox schoolCombo,final IButton filterButton) {;
		academicTermCombo.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {

				if (academicTermCombo.getValueAsString() != null && schoolCombo.getValueAsString() != null) {
                    filterButton.setDisabled(false);
				}else {
					 filterButton.setDisabled(true);	
				}
			}
		});

		schoolCombo.addChangedHandler(new ChangedHandler() {

			@Override
			public void onChanged(ChangedEvent event) {
				if (academicTermCombo.getValueAsString() != null && schoolCombo.getValueAsString() != null) {
                    filterButton.setDisabled(false);
				}else {
					filterButton.setDisabled(true);
				}
			}
		});

	}

	public ComboBox getAcademicTermCombo() {
		return academicTermCombo;
	}

	public ComboBox getSchoolCombo() {
		return schoolCombo;
	}

	public IButton getFilterButton() {
		return filterButton;
	}

}

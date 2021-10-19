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

public class FilterYearTermDistrictSchoolDate extends VLayout {
	private ComboBox schoolCombo;
	private ComboBox districtCombo;
	private ComboBox academicYearCombo;
	private TextItem dayField;
	private ComboBox academicTermCombo;
	
	DateTimeFormat dayFormat = DateTimeFormat.getFormat(DatePattern.DAY_DATE.getPattern());

	public FilterYearTermDistrictSchoolDate() {
		super();
		
		HLayout formLayout = new HLayout();
		formLayout.setPadding(5);
		formLayout.setAutoHeight();
		formLayout.setWidth("50%");
		formLayout.setLayoutAlign(Alignment.CENTER);

		HLayout schoolDistrictLayout = new HLayout();
		schoolDistrictLayout.setPadding(5);
		schoolDistrictLayout.setAutoHeight();
		schoolDistrictLayout.setWidth("50%");
		schoolDistrictLayout.setLayoutAlign(Alignment.CENTER);

		Label schoolLabel = new Label("School: ");
		schoolLabel.setAutoHeight();
		schoolLabel.setAutoWidth();
		schoolLabel.setPadding(2);

		Label dayLabel = new Label("Day: ");
		dayLabel.setAutoHeight();
		dayLabel.setAutoWidth();
		dayLabel.setPadding(2);

		DynamicForm form = new DynamicForm();
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setColWidths("150", "250");
		form.setCellPadding(5);
		form.setNumCols(4);

		academicYearCombo = new ComboBox();
		academicYearCombo.setTitle("AcademicYear");
		academicYearCombo.setHint("Year");
		academicYearCombo.setShowHintInField(true);

		academicTermCombo = new ComboBox();
		academicTermCombo.setTitle("AcademicTerm");
		academicTermCombo.setHint("AcademicTerm");
		academicTermCombo.setShowHintInField(true);

		districtCombo = new ComboBox();
		districtCombo.setTitle("District");
		districtCombo.setHint("District");
		districtCombo.setShowHintInField(true);

		schoolCombo = new ComboBox();
		schoolCombo.setTitle("School");
		schoolCombo.setHint("School");
		schoolCombo.setShowHintInField(true);

		dayField = new TextItem("Day");
		dayField.setValue(dayFormat.format(new Date()));
		dayField.disable();

		form.setFields(academicYearCombo, districtCombo, academicTermCombo, schoolCombo, dayField);

		this.addMember(form);
		this.setWidth100();
		this.setAutoHeight();
	}

	public ComboBox getSchoolCombo() {
		return schoolCombo;
	}

	public ComboBox getDistrictCombo() {
		return districtCombo;
	}

	public ComboBox getAcademicYearCombo() {
		return academicYearCombo;
	}

	public TextItem getDayField() {
		return dayField;
	}

	public ComboBox getAcademicTermCombo() {
		return academicTermCombo;
	}

	public DateTimeFormat getDayFormat() {
		return dayFormat;
	}

	
	
}

package com.planetsystems.tela.managementapp.client.presenter.timetable;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class CreateTimeTablePane extends VLayout {

	private ComboBox academicYearCombo;
	private ComboBox academicTermCombo;
	private ComboBox districtCombo;
	private ComboBox schoolCombo;
	private IButton addLessonButton;
	private LessonListGrid lessonListGrid;
	
	private IButton saveButton;

	private IButton cancelButton;
	public CreateTimeTablePane() {
		super();
		
		academicYearCombo = new ComboBox();
		academicYearCombo.setTitle("AcademicYear");
		academicYearCombo.setHint("Year");
		academicYearCombo.setShowHint(true);
		academicYearCombo.setShowHintInField(true);
		academicYearCombo.setTitleOrientation(TitleOrientation.TOP);
		
		academicTermCombo = new ComboBox();
		academicTermCombo.setTitle("AcademicTerm");
		academicTermCombo.setHint("Term");
		academicTermCombo.setShowHint(true);
		academicTermCombo.setShowHintInField(true);
		academicTermCombo.setTitleOrientation(TitleOrientation.TOP);
		
		districtCombo = new ComboBox();
		districtCombo.setTitle("District");
		districtCombo.setHint("District");
		districtCombo.setShowHint(true);
		districtCombo.setShowHintInField(true);
		districtCombo.setTitleOrientation(TitleOrientation.TOP);
		
		schoolCombo = new ComboBox();
		schoolCombo.setTitle("School");
		schoolCombo.setHint("School");
		schoolCombo.setShowHint(true);
		schoolCombo.setShowHintInField(true);
		schoolCombo.setTitleOrientation(TitleOrientation.TOP);
		
		addLessonButton = new IButton("Add");
		addLessonButton.setLayoutAlign(Alignment.RIGHT);
		addLessonButton.setPadding(10);
		addLessonButton.disable();

		
		DynamicForm filterForm = new DynamicForm();
		filterForm.setNumCols(4);
		filterForm.setColWidths(20 , 150 ,20 , 150, 20 , 150, 20 , 150);
		filterForm.setCellPadding(10);
		filterForm.setFields(academicYearCombo , academicTermCombo , districtCombo , schoolCombo);
		
		lessonListGrid = new LessonListGrid();
		lessonListGrid.setSelectionType(SelectionStyle.MULTIPLE);
	
		
		saveButton = new IButton("Save");
        saveButton.disable();
		
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton, saveButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		
		buttonLayout.setLayoutAlign(Alignment.CENTER);
		
		
		VLayout layout = new VLayout();
		layout.setMembersMargin(10);
		layout.addMembers(filterForm , addLessonButton , lessonListGrid , buttonLayout);
		
		
	 
		this.addMember(layout);
	
	}
	public ComboBox getAcademicYearCombo() {
		return academicYearCombo;
	}
	public ComboBox getAcademicTermCombo() {
		return academicTermCombo;
	}
	public ComboBox getDistrictCombo() {
		return districtCombo;
	}
	public ComboBox getSchoolCombo() {
		return schoolCombo;
	}
	public IButton getAddLessonButton() {
		return addLessonButton;
	}
	public LessonListGrid getLessonListGrid() {
		return lessonListGrid;
	}
	public IButton getSaveButton() {
		return saveButton;
	}
	public IButton getCancelButton() {
		return cancelButton;
	}

	

}

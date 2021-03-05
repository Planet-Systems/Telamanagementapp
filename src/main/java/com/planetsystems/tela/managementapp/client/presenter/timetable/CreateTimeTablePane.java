package com.planetsystems.tela.managementapp.client.presenter.timetable;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class CreateTimeTablePane extends VLayout {

	private ComboBox academicYearComboBox;
	private ComboBox academicTermComboBox;
	private ComboBox districtComboBox;
	private ComboBox schoolComboBox;
	private IButton addLessonButton ;
	private LessonListGrid lessonListGrid;
	
	private IButton saveButton;

	private IButton cancelButton;
	public CreateTimeTablePane() {
		super();
		
		academicYearComboBox = new ComboBox();
		academicYearComboBox.setTitle("AcademicYear");
		academicYearComboBox.setHint("Year");
		academicYearComboBox.setShowHint(true);
		academicYearComboBox.setShowHintInField(true);
		academicYearComboBox.setTitleOrientation(TitleOrientation.TOP);
		
		academicTermComboBox = new ComboBox();
		academicTermComboBox.setTitle("AcademicTerm");
		academicTermComboBox.setHint("Term");
		academicTermComboBox.setShowHint(true);
		academicTermComboBox.setShowHintInField(true);
		academicTermComboBox.setTitleOrientation(TitleOrientation.TOP);
		
		districtComboBox = new ComboBox();
		districtComboBox.setTitle("District");
		districtComboBox.setHint("District");
		districtComboBox.setShowHint(true);
		districtComboBox.setShowHintInField(true);
		districtComboBox.setTitleOrientation(TitleOrientation.TOP);
		
		schoolComboBox = new ComboBox();
		schoolComboBox.setTitle("School");
		schoolComboBox.setHint("School");
		schoolComboBox.setShowHint(true);
		schoolComboBox.setShowHintInField(true);
		schoolComboBox.setTitleOrientation(TitleOrientation.TOP);
		
		addLessonButton = new IButton("Add");
		addLessonButton.setLayoutAlign(Alignment.RIGHT);
		addLessonButton.setPadding(10);
		addLessonButton.disable();

		
		DynamicForm filterForm = new DynamicForm();
		filterForm.setNumCols(4);
		filterForm.setColWidths(20 , 150 ,20 , 150, 20 , 150, 20 , 150);
		filterForm.setCellPadding(10);
		filterForm.setFields(academicYearComboBox , academicTermComboBox , districtComboBox , schoolComboBox);
		
		lessonListGrid = new LessonListGrid();

		
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
		layout.addMembers(filterForm , addLessonButton ,lessonListGrid , buttonLayout);
		
		
	 
		this.addMember(layout);
	
	}

	public ComboBox getAcademicYearComboBox() {
		return academicYearComboBox;
	}

	public ComboBox getAcademicTermComboBox() {
		return academicTermComboBox;
	}

	public ComboBox getDistrictComboBox() {
		return districtComboBox;
	}

	public ComboBox getSchoolComboBox() {
		return schoolComboBox;
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

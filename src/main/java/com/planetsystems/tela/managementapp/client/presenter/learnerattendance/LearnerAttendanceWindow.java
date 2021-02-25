package com.planetsystems.tela.managementapp.client.presenter.learnerattendance;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class LearnerAttendanceWindow extends Window {
	
	/*
	 *     private SchoolClassDTO schoolClassDTO;

    private AcademicTermDTO academicTermDTO;

    private SchoolStaffDTO schoolStaffDTO;

    private LocalDate attendanceDate;
    private long girlsPresent;
    private long boysPresent;
    private long boysAbsent;
    private long girlsAbsent;
    private String comment;
	 */
	private ComboBox academicTermComboBox;

	private ComboBox schoolClassComboBox;
	
	private ComboBox schoolStaffComboBox;

	private TextAreaItem commentField;
	private TextItem girlsPresentField;
	private TextItem boysPresentField;
	private TextItem boysAbsentField;
	private TextItem girlsAbsentField;
	private TextItem totalAbsentField;
	private TextItem totalPresentField;
	

	private IButton saveButton;
	private IButton cancelButton;

	public LearnerAttendanceWindow() {
		
		commentField = new TextAreaItem();
		commentField.setTitle("Comment");
		commentField.setRowSpan(2);
		
		girlsAbsentField = new TextItem();
		girlsAbsentField.setTitle("GirlsAbsent");
		
		boysAbsentField = new TextItem();
		boysAbsentField.setTitle("BoysAbsent");
		
		girlsPresentField = new TextItem();
		girlsPresentField.setTitle("GirlsPresent");
		
		boysPresentField = new TextItem();
		boysPresentField.setTitle("BoysPresent");
		
		totalAbsentField = new TextItem();
		totalAbsentField.setTitle("TotalAbsent");
		totalAbsentField.disable();
		
		totalPresentField = new TextItem();
		totalPresentField.setTitle("TotalPresent");
		totalPresentField.disable();
		
		academicTermComboBox = new ComboBox();
		academicTermComboBox.setTitle("AcademicTerm");
		
		schoolStaffComboBox = new ComboBox();
		schoolStaffComboBox.setTitle("Staff");
		
		schoolClassComboBox = new ComboBox();
		schoolClassComboBox.setTitle("Class");
       
		
		DynamicForm dynamicForm = new DynamicForm();
		dynamicForm.setFields(academicTermComboBox ,schoolClassComboBox ,schoolStaffComboBox , 
				boysPresentField , boysAbsentField , girlsPresentField , girlsAbsentField , commentField , totalAbsentField , totalPresentField);
		dynamicForm.setWrapItemTitles(false);
		dynamicForm.setMargin(10);
		dynamicForm.setCellPadding(7);
		dynamicForm.setColWidths("80", "220" , "80", "220");
		dynamicForm.setNumCols(4);
		
		
		saveButton = new IButton("Save");
		cancelButton = new IButton("Cancel");
	
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton , saveButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		
		
		buttonLayout.setLayoutAlign(Alignment.CENTER);
		
		VLayout layout = new VLayout();
		layout.addMember(dynamicForm);
		layout.addMember(buttonLayout);
		layout.setMembersMargin(10);
		layout.setMargin(10);
		
		
		this.addMember(layout);
		
	
		this.setWidth("60%");
		this.setHeight("70%");
		this.setAutoCenter(true);
		this.setTitle("Learner Attendance");
		this.setIsModal(true);
		this.setShowModalMask(true);
		closeWindow(this);
	}
	
	private void closeWindow(final Window window) {
		cancelButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				window.close();
			}
		});
	}

	public ComboBox getAcademicTermComboBox() {
		return academicTermComboBox;
	}

	public ComboBox getSchoolClassComboBox() {
		return schoolClassComboBox;
	}

	public ComboBox getSchoolStaffComboBox() {
		return schoolStaffComboBox;
	}

	public TextAreaItem getCommentField() {
		return commentField;
	}

	public TextItem getGirlsPresentField() {
		return girlsPresentField;
	}

	public TextItem getBoysPresentField() {
		return boysPresentField;
	}

	public TextItem getBoysAbsentField() {
		return boysAbsentField;
	}

	public TextItem getGirlsAbsentField() {
		return girlsAbsentField;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

	public TextItem getTotalAbsentField() {
		return totalAbsentField;
	}

	public TextItem getTotalPresentField() {
		return totalPresentField;
	}

	
	
	
	
	
}

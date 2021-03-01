package com.planetsystems.tela.managementapp.client.presenter.schoolstaff;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.DateItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SchoolStaffWindow extends Window {
    
	    private TextItem firstNameField;
	    private TextItem lastNameField;
	    private TextItem phoneNumberField;
	    private TextItem emailField;
	    private DateItem dobItem;
	    private TextItem nationalIdField;
	    private ComboBox genderComboBox;
	    private TextItem nameAbrevField;
	    private TextItem staffCode;
	    private ComboBox registeredComboBox;
	    private ComboBox schoolComboBox;
	   
	    
	    private IButton saveButton;
		private IButton cancelButton;
	
	public SchoolStaffWindow() {
		
		firstNameField = new TextItem();
		firstNameField.setTitle("FirstName");
		
		lastNameField = new TextItem();
		lastNameField.setTitle("LastName");
		
		phoneNumberField = new TextItem();
		phoneNumberField.setTitle("PhoneNumber");
		phoneNumberField.setMask("#### ###-###");  
        phoneNumberField.setHint("#### ###-###");  
        phoneNumberField.setShowHintInField(true);  
		
		
		emailField = new TextItem();
		emailField.setTitle("Email");

		dobItem = new DateItem();
		dobItem.setTitle("DOB");
		
		nationalIdField = new TextItem();
		nationalIdField.setTitle("NationalId");

		nameAbrevField = new TextItem();
		nameAbrevField.setTitle("NameAbbrev");
		
		staffCode = new TextItem();
		staffCode.setTitle("StaffCode");
		
		genderComboBox = new ComboBox();
		genderComboBox.setTitle("Gender");
		
		registeredComboBox = new ComboBox();
		registeredComboBox.setTitle("Registered");
		
		
		schoolComboBox = new ComboBox();
		schoolComboBox.setTitle("School");
		
		DynamicForm dynamicForm = new DynamicForm();
		dynamicForm.setFields(firstNameField , lastNameField , nameAbrevField , emailField , nationalIdField , phoneNumberField , staffCode , registeredComboBox , genderComboBox , schoolComboBox , dobItem);
		dynamicForm.setNumCols(4);
		dynamicForm.setWrapItemTitles(false);
		dynamicForm.setMargin(10);
		dynamicForm.setCellPadding(8);
		dynamicForm.setColWidths("80", "220" , "80", "220");
	
		
        
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
		
		
		
		this.setWidth("60%");
		this.setHeight("60%");
		this.setAutoCenter(true);
		this.setTitle("SchoolStaff");
		this.setIsModal(true);
		//this.setShowModalMask(true);
		
		this.addMember(layout);
		
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

	public TextItem getFirstNameField() {
		return firstNameField;
	}

	public void setFirstNameField(TextItem firstNameField) {
		this.firstNameField = firstNameField;
	}

	public TextItem getLastNameField() {
		return lastNameField;
	}

	public void setLastNameField(TextItem lastNameField) {
		this.lastNameField = lastNameField;
	}

	public TextItem getPhoneNumberField() {
		return phoneNumberField;
	}

	public void setPhoneNumberField(TextItem phoneNumberField) {
		this.phoneNumberField = phoneNumberField;
	}

	public TextItem getEmailField() {
		return emailField;
	}

	public void setEmailField(TextItem emailField) {
		this.emailField = emailField;
	}

	public DateItem getDobItem() {
		return dobItem;
	}

	public void setDobItem(DateItem dobItem) {
		this.dobItem = dobItem;
	}

	public TextItem getNationalIdField() {
		return nationalIdField;
	}

	public void setNationalIdField(TextItem nationalIdField) {
		this.nationalIdField = nationalIdField;
	}

	public ComboBox getGenderComboBox() {
		return genderComboBox;
	}

	public void setGenderComboBox(ComboBox genderComboBox) {
		this.genderComboBox = genderComboBox;
	}

	public TextItem getNameAbrevField() {
		return nameAbrevField;
	}

	public void setNameAbrevField(TextItem nameAbrevField) {
		this.nameAbrevField = nameAbrevField;
	}

	public TextItem getStaffCode() {
		return staffCode;
	}

	public void setStaffCode(TextItem staffCode) {
		this.staffCode = staffCode;
	}

	public ComboBox getRegisteredComboBox() {
		return registeredComboBox;
	}

	public void setRegisteredComboBox(ComboBox registeredComboBox) {
		this.registeredComboBox = registeredComboBox;
	}

	public ComboBox getSchoolComboBox() {
		return schoolComboBox;
	}

	public void setSchoolComboBox(ComboBox schoolComboBox) {
		this.schoolComboBox = schoolComboBox;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(IButton saveButton) {
		this.saveButton = saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

	public void setCancelButton(IButton cancelButton) {
		this.cancelButton = cancelButton;
	}

	
	
	
	

}

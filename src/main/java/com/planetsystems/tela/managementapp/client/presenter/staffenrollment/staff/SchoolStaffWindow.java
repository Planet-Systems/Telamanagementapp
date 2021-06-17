package com.planetsystems.tela.managementapp.client.presenter.staffenrollment.staff;

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
	    private ComboBox genderCombo;
	    private TextItem nameAbrevField;
	    private TextItem codeField;
	    private ComboBox registeredCombo;
	    private ComboBox districtCombo;
	    private ComboBox schoolCombo;
	   
	    
	    private IButton saveButton;
		private IButton cancelButton;
	
	public SchoolStaffWindow() {
		super();
		firstNameField = new TextItem();
		firstNameField.setTitle("FirstName");
		firstNameField.setHint("FirstName");
		firstNameField.setShowHintInField(true);
		
		lastNameField = new TextItem();
		lastNameField.setTitle("LastName");
		lastNameField.setHint("LastName");
		lastNameField.setShowHintInField(true);
		
		phoneNumberField = new TextItem();
		phoneNumberField.setTitle("PhoneNumber");
		phoneNumberField.setMask("#### ###-###");  
        phoneNumberField.setHint("#### ###-###");  
        phoneNumberField.setShowHintInField(true);  
		
		
		emailField = new TextItem();
		emailField.setTitle("Email");
		emailField.setHint("email");
		emailField.setShowHintInField(true);

		dobItem = new DateItem();
		dobItem.setTitle("DOB");
		
		nationalIdField = new TextItem();
		nationalIdField.setTitle("NationalId");
		nationalIdField.setHint("NIN");
		nationalIdField.setShowHintInField(true);

		nameAbrevField = new TextItem();
		nameAbrevField.setTitle("NameAbbrev");
		nameAbrevField.setHint("Name Abbreviation");
		nameAbrevField.setShowHintInField(true);
		
		codeField = new TextItem();
		codeField.setTitle("CodeField");
		codeField.setHint("codeField");
		codeField.setShowHintInField(true);
		
		genderCombo = new ComboBox();
		genderCombo.setTitle("Gender");
		genderCombo.setHint("Gender");
		genderCombo.setShowHintInField(true);
		
		registeredCombo = new ComboBox();
		registeredCombo.setTitle("Registered");
		registeredCombo.setHint("Registered");
		registeredCombo.setShowHintInField(true);
		
		
		
		schoolCombo = new ComboBox();
		schoolCombo.setTitle("School");
		schoolCombo.setHint("school");
		schoolCombo.setShowHintInField(true);
		
		districtCombo = new ComboBox();
		districtCombo.setTitle("District");
		districtCombo.setHint("District");
		districtCombo.setShowHintInField(true);
		
		DynamicForm dynamicForm = new DynamicForm();
		dynamicForm.setFields(firstNameField , lastNameField , nameAbrevField , emailField , nationalIdField , phoneNumberField , codeField , districtCombo , genderCombo , schoolCombo , dobItem , registeredCombo);
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

		
		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("60%");
		this.setHeight("70%");
		this.setAutoCenter(true);
		this.setTitle("School Staff");
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

	public TextItem getFirstNameField() {
		return firstNameField;
	}

	public TextItem getLastNameField() {
		return lastNameField;
	}

	public TextItem getPhoneNumberField() {
		return phoneNumberField;
	}

	public TextItem getEmailField() {
		return emailField;
	}

	public DateItem getDobItem() {
		return dobItem;
	}

	public TextItem getNationalIdField() {
		return nationalIdField;
	}

	public ComboBox getGenderCombo() {
		return genderCombo;
	}

	public TextItem getNameAbrevField() {
		return nameAbrevField;
	}

	public TextItem getCodeField() {
		return codeField;
	}

	public ComboBox getRegisteredCombo() {
		return registeredCombo;
	}

	public ComboBox getDistrictCombo() {
		return districtCombo;
	}

	public ComboBox getSchoolCombo() {
		return schoolCombo;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

	
	

}

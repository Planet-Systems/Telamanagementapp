package com.planetsystems.tela.managementapp.client.presenter.smsmessage.smsstaff;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.MultiComboBoxItem;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SchoolStaffPane extends VLayout{

	private ComboBox regionCombo;
	private ComboBox districtCombo;
	private ComboBox schoolCombo;
	private MultiComboBoxItem schoolStaffCombo;
	private TextAreaItem messageField;
	
	private IButton sendButton;
	
	public SchoolStaffPane() {
		super();
        
		regionCombo = new ComboBox();
		regionCombo.setTitle("Region");
		regionCombo.setHint("region");
		regionCombo.setShowHint(true);
		regionCombo.setShowHintInField(true);
		
		districtCombo = new ComboBox();
		districtCombo.setTitle("District");
		districtCombo.setHint("district");
		districtCombo.setShowHint(true);
		districtCombo.setShowHintInField(true);
	
		
		
		schoolCombo = new ComboBox();
		schoolCombo.setTitle("School");
		schoolCombo.setHint("school");
		schoolCombo.setShowHint(true);
		schoolCombo.setShowHintInField(true);

		
		
		schoolStaffCombo = new MultiComboBoxItem();
		schoolStaffCombo.setTitle("School Staff");

		
		
		
		messageField = new TextAreaItem();
		messageField.setTitle("Message");
		messageField.setHint("messag...");
		messageField.setShowHint(true);
		messageField.setShowHintInField(true);
		messageField.setColSpan(3);

		
		
		
		DynamicForm form = new DynamicForm();
		form.setFields(regionCombo , districtCombo , schoolCombo , schoolStaffCombo , messageField);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setColWidths("150","270" , "150","270" , "150","270" , "150","270" , "150","270");
		form.setCellPadding(10);
		
		sendButton = new IButton("Send");
		
		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(sendButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setLayoutAlign(Alignment.CENTER);
		
		
		VLayout layout = new VLayout();
//		layout.setBorder("1px solid blue");
		layout.setWidth("60%");
		layout.setHeight("100%");
		layout.setAlign(Alignment.CENTER);
		layout.setLayoutAlign(Alignment.CENTER);
		layout.addMembers(form , buttonLayout);
		
		
	
		this.addMember(layout);

	}

	public ComboBox getRegionCombo() {
		return regionCombo;
	}

	public ComboBox getDistrictCombo() {
		return districtCombo;
	}

	public ComboBox getSchoolCombo() {
		return schoolCombo;
	}

	
	

	public MultiComboBoxItem getSchoolStaffCombo() {
		return schoolStaffCombo;
	}

	public TextAreaItem getMessageField() {
		return messageField;
	}

	public IButton getSendButton() {
		return sendButton;
	}
	
	
	
	

}

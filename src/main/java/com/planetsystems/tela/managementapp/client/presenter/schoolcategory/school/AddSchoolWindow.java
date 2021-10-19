package com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class AddSchoolWindow extends Window {

	private TextField schoolCode;
	private TextField schoolName;
	private ComboBox schoolCategoryCombo;
	private ComboBox districtCombo;
	private ComboBox regionCombo;

	private TextField latitude;
	private TextField longtitude;
	
	private TextField deviceNumber;
	

	private IButton saveButton;
	private IButton cancelButton;



	public AddSchoolWindow() {
		super();
		schoolCode = new TextField();
		schoolCode.setTitle("Code");
		schoolCode.setHint("code");
		schoolCode.setShowHintInField(true);

		schoolName = new TextField();
		schoolName.setTitle("Name");
		schoolName.setHint("name");
		schoolName.setShowHintInField(true);

		schoolCategoryCombo = new ComboBox();
		schoolCategoryCombo.setTitle("Category");
		schoolCategoryCombo.setHint("category");
		schoolCategoryCombo.setShowHintInField(true);

		latitude = new TextField();
		latitude.setTitle("Latitude");
		latitude.setHint("latitude");
		latitude.setShowHintInField(true);

		longtitude = new TextField();
		longtitude.setTitle("Longtitude");
		longtitude.setHint("longtitude");
		longtitude.setShowHintInField(true);

		districtCombo = new ComboBox();
		districtCombo.setTitle("District");
		districtCombo.setHint("district");
		districtCombo.setShowHintInField(true);
		
		regionCombo = new ComboBox();
		regionCombo.setTitle("Region");
		regionCombo.setHint("region");
		regionCombo.setShowHintInField(true);
		
		deviceNumber= new TextField();
		deviceNumber.setTitle("Device Number");
		deviceNumber.setHint("device number");
		deviceNumber.setShowHintInField(true);
	

		saveButton = new IButton("Save");
		
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		DynamicForm form = new DynamicForm();
		form.setFields(schoolCategoryCombo , regionCombo , districtCombo , schoolCode, schoolName , latitude, longtitude,deviceNumber);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setColWidths("150","250");
		form.setCellPadding(10);

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton , saveButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setAutoWidth();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);
		buttonLayout.setLayoutAlign(Alignment.CENTER);

		VLayout layout = new VLayout();
		layout.addMember(form);
		layout.addMember(buttonLayout);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("50%");
		this.setHeight("90%");
		this.setAutoCenter(true);
		this.setTitle("Add School");
		this.setIsModal(true);
		this.setShowModalMask(true);
		cancel(this);
	}

	private void cancel(final Window window) {
		cancelButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				window.close();
			}
		});
	}
	
	public TextField getSchoolCode() {
		return schoolCode;
	}

	public TextField getSchoolName() {
		return schoolName;
	}

	public TextField getLatitude() {
		return latitude;
	}

	public TextField getLongtitude() {
		return longtitude;
	}

	public TextField getDeviceNumber() {
		return deviceNumber;
	}



	public IButton getSaveButton() {
		return saveButton;
	}

	public ComboBox getSchoolCategoryCombo() {
		return schoolCategoryCombo;
	}

	public ComboBox getDistrictCombo() {
		return districtCombo;
	}

	public ComboBox getRegionCombo() {
		return regionCombo;
	}

	

}

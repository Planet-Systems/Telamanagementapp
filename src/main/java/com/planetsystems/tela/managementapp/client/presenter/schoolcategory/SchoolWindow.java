package com.planetsystems.tela.managementapp.client.presenter.schoolcategory;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SchoolWindow extends Window {

	private TextField schoolCode;
	private TextField schoolName;
	private ComboBox schoolCategory;
	private ComboBox district;

	private TextField latitude;
	private TextField longtitude;
	
	private TextField deviceNumber;
	

	private IButton saveButton;
	private IButton cancelButton;



	public SchoolWindow() {
		super();
		schoolCode = new TextField();
		schoolCode.setTitle("Code");

		schoolName = new TextField();
		schoolName.setTitle("Name");

		schoolCategory = new ComboBox();
		schoolCategory.setTitle("Category");

		latitude = new TextField();
		latitude.setTitle("Latitude");

		longtitude = new TextField();
		longtitude.setTitle("Longtitude");

		district = new ComboBox();
		district.setTitle("District");
		
		deviceNumber= new TextField();
		deviceNumber.setTitle("Device Number");
	

		saveButton = new IButton("Save");
		
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		DynamicForm form = new DynamicForm();
		form.setFields(schoolCategory,district, schoolCode, schoolName , latitude, longtitude,deviceNumber);
		form.setWrapItemTitles(false);
		form.setMargin(10);

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton , saveButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setWidth100();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);

		VLayout layout = new VLayout();
		layout.addMember(form);
		layout.addMember(buttonLayout);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("40%");
		this.setHeight("60%");
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

	public ComboBox getSchoolCategory() {
		return schoolCategory;
	}

	public ComboBox getDistrict() {
		return district;
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

	
	
	
	

}

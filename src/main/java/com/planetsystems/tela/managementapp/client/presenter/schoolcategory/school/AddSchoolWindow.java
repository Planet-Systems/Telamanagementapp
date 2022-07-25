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

	private ComboBox schoolLevel;
	private ComboBox schoolOwnership;
	private ComboBox schoolType;
	private ComboBox schoolGenderCategory;
	private ComboBox licensed;

	private IButton saveButton;
	private IButton cancelButton;

	public AddSchoolWindow() {
		super();
		schoolCode = new TextField();
		schoolCode.setTitle("School Code");
		schoolCode.setHint("School Code");
		schoolCode.setShowHintInField(true);

		schoolName = new TextField();
		schoolName.setTitle("School Name");
		schoolName.setHint("School Name");
		schoolName.setShowHintInField(true);

		schoolCategoryCombo = new ComboBox();
		schoolCategoryCombo.setTitle("Foundation Body");
		schoolCategoryCombo.setHint("Select");
		schoolCategoryCombo.setShowHintInField(true);

		latitude = new TextField();
		latitude.setTitle("Latitude");
		latitude.setHint("Latitude");
		latitude.setShowHintInField(true);

		longtitude = new TextField();
		longtitude.setTitle("Longtitude");
		longtitude.setHint("Longtitude");
		longtitude.setShowHintInField(true);

		districtCombo = new ComboBox();
		districtCombo.setTitle("Local Government");
		districtCombo.setHint("Select");
		districtCombo.setShowHintInField(true);

		regionCombo = new ComboBox();
		regionCombo.setTitle("Sub-Region");
		regionCombo.setHint("Select");
		regionCombo.setShowHintInField(true);

		deviceNumber = new TextField();
		deviceNumber.setTitle("Tela Phone Number");
		deviceNumber.setHint("Tela Phone Number");
		deviceNumber.setShowHintInField(true);

		schoolLevel = new ComboBox();
		schoolLevel.setTitle("School Level");
		schoolLevel.setHint("Select");
		schoolLevel.setShowHintInField(true);

		schoolOwnership = new ComboBox();
		schoolOwnership.setTitle("School Ownership");
		schoolOwnership.setHint("Select");
		schoolOwnership.setShowHintInField(true);

		schoolType = new ComboBox();
		schoolType.setTitle("School Type");
		schoolType.setHint("Select");
		schoolType.setShowHintInField(true);

		schoolGenderCategory = new ComboBox();
		schoolGenderCategory.setTitle("Gender Category");
		schoolGenderCategory.setHint("Select");
		schoolGenderCategory.setShowHintInField(true);

		licensed = new ComboBox();
		licensed.setTitle("Is Licensed/Registered");
		licensed.setHint("Select");
		licensed.setShowHintInField(true);

		saveButton = new IButton("Save");

		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		// schoolLevel,schoolOwnership,schoolType,schoolGenderCategory,licensed

		DynamicForm form = new DynamicForm();
		form.setFields(schoolLevel, schoolOwnership, schoolType, schoolGenderCategory, schoolCategoryCombo, regionCombo,
				districtCombo, deviceNumber, schoolCode, schoolName, licensed, latitude, longtitude);

		form.setWrapItemTitles(true);
		form.setMargin(10);
		form.setColWidths("150", "350");
		form.setCellPadding(8);
		form.setTitleAlign(Alignment.LEFT);

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton, saveButton);
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
		this.setTitle("School Detail");
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

	public ComboBox getSchoolLevel() {
		return schoolLevel;
	}

	public ComboBox getSchoolOwnership() {
		return schoolOwnership;
	}

	public ComboBox getSchoolType() {
		return schoolType;
	}

	public ComboBox getSchoolGenderCategory() {
		return schoolGenderCategory;
	}

	public ComboBox getLicensed() {
		return licensed;
	}

}

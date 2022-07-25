package com.planetsystems.tela.managementapp.client.presenter.staffenrollment.staff;

import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class StaffImportWindow extends Window {

	private FormPanel uploadForm = new FormPanel();
	private FileUpload upload = new FileUpload();
	private IButton uploadButton = new IButton();
	private Label serverResponseLabel = new Label();

	private ComboBox region;
	private ComboBox district;
	private ComboBox school;

	public StaffImportWindow() {
		super();
		this.setAutoCenter(true);
		this.setWidth("35%");
		this.setHeight("40%");
		this.setTitle("Upload File");

		uploadButton.setTitle("Upload");

		region = new ComboBox();
		region.setTitle("Sub-Region");

		district = new ComboBox();
		district.setTitle("Local Government");
		
		school=new ComboBox();
		school.setTitle("School");

		// upload.setName("uploadFormElement");
		upload.setTitle("File Upload");
		upload.setName("upload");

		uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		uploadForm.setMethod(FormPanel.METHOD_POST);
		uploadForm.add(upload);
		//uploadForm.getElement().setAttribute("accept", ".xlsx");
		//uploadForm.

		DynamicForm form = new DynamicForm();
		form.setFields(region, district,school);
		form.setColWidths("150", "300" , "150", "300");
		form.setPadding(8); 

		HLayout hlayout = new HLayout();
		hlayout.setMargin(10);
		hlayout.addMember(uploadForm);
		hlayout.addMember(uploadButton);
		hlayout.setAutoHeight();

		VLayout vlayout = new VLayout();
		vlayout.addMember(form);
		vlayout.addMember(hlayout);
		vlayout.setMargin(10);
		vlayout.setAutoHeight();

		serverResponseLabel.setStyleName("crm-Masthead-SignedInUser");

		this.addItem(vlayout);
		this.addItem(serverResponseLabel);
		this.setIsModal(true);
		this.setShowModalMask(true);
	}

	public FormPanel getUploadForm() {
		return uploadForm;
	}

	public FileUpload getUpload() {
		return upload;
	}

	public IButton getUploadButton() {
		return uploadButton;
	}

	public Label getServerResponseLabel() {
		return serverResponseLabel;
	}

	public ComboBox getRegion() {
		return region;
	}

	public ComboBox getDistrict() {
		return district;
	}

	public ComboBox getSchool() {
		return school;
	}
	
	

}

package com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school;

import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class GeneralSchoolImportWindow extends Window {

	private FormPanel uploadForm = new FormPanel();
	private FileUpload upload = new FileUpload();
	private IButton uploadButton = new IButton();
	private Label serverResponseLabel = new Label();

	public GeneralSchoolImportWindow() {
		super();
		this.setAutoCenter(true);
		this.setWidth("30%");
		this.setHeight("30%");
		this.setTitle("Upload File"); 

		uploadButton.setTitle("Upload");

		// upload.setName("uploadFormElement");
		upload.setTitle("File Upload");
		upload.setName("upload");

		uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		uploadForm.setMethod(FormPanel.METHOD_POST);
		uploadForm.add(upload); 
		// uploadForm.getElement().setAttribute("accept", ".xlsx");
		// uploadForm.

		VLayout hlayout = new VLayout();
		hlayout.setMargin(10);
		hlayout.addMember(uploadForm);
		hlayout.addMember(uploadButton);
		hlayout.setAutoHeight();
  
		serverResponseLabel.setStyleName("crm-Masthead-SignedInUser");

		this.addItem(hlayout);
		//this.addItem(serverResponseLabel);
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

}

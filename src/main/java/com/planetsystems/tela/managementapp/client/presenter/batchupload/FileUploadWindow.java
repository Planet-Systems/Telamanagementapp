package com.planetsystems.tela.managementapp.client.presenter.batchupload;

import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class FileUploadWindow extends Window {

	private FormPanel uploadForm = new FormPanel();
	private FileUpload upload = new FileUpload();
	private IButton uploadButton = new IButton();

	public FileUploadWindow() {
		super();
		this.setAutoCenter(true);
		this.setWidth("35%");
		this.setHeight("40%");
		this.setTitle("Upload File");

		uploadButton.setTitle("Upload"); 
		upload.setTitle("File Upload");
		upload.setName("upload");

		uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		uploadForm.setMethod(FormPanel.METHOD_POST);
		uploadForm.add(upload);

		HLayout hlayout = new HLayout();
		hlayout.setMargin(10);
		hlayout.addMember(uploadForm);
		hlayout.addMember(uploadButton);
		hlayout.setAutoHeight();

		VLayout vlayout = new VLayout();
		vlayout.addMember(hlayout);
		vlayout.setMargin(10);
		vlayout.setAutoHeight();
		this.addItem(vlayout);
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
	
	

}

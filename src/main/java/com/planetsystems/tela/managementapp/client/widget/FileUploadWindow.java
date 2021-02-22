package com.planetsystems.tela.managementapp.client.widget;

import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormPanel;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextAreaItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class FileUploadWindow extends Window {
	
	private FormPanel uploadForm = new FormPanel();
	private FileUpload upload = new FileUpload();
	private IButton uploadButton = new IButton();
	private Label serverResponseLabel = new Label();

	private TextAreaItem comment;
	private ComboBox documentType;

	public FileUploadWindow() {
		super();
		this.setAutoCenter(true);
		this.setWidth("40%");
		this.setHeight("40%");
		this.setTitle("Upload File");

		uploadButton.setTitle("Upload");
		documentType=new ComboBox();
		documentType.setTitle("Document Type");

		upload.setName("uploadFormElement");
		upload.setTitle("File Upload");

		comment = new TextAreaItem();
		comment.setTitle("Description");
		comment.setShowTitle(true);
		comment.setWidth(500);
		comment.setHeight(100);

		uploadForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		uploadForm.setMethod(FormPanel.METHOD_POST);
		uploadForm.add(upload);

		DynamicForm form = new DynamicForm();
		form.setFields(documentType,comment);

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

	 
	public void setServerResponseLabel(Label serverResponseLabel) {
		this.serverResponseLabel = serverResponseLabel;
	}

	public TextAreaItem getComment() {
		return comment;
	}

	public ComboBox getDocumentType() {
		return documentType;
	}

}

package com.planetsystems.tela.managementapp.client.presenter.emailattachmentdownload;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.VLayout;

public class EmailAttachmentDownloadPane extends VLayout {

	private EmailAttachmentDownloadListgrid listGrid;

	public EmailAttachmentDownloadPane() {
		super();
		Label header = new Label();

		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("Email Attachment Downloads");
		header.setWidth("100%");
		header.setAutoHeight();
		header.setMargin(10);
		header.setAlign(Alignment.LEFT);

		listGrid = new EmailAttachmentDownloadListgrid();

		VLayout layout = new VLayout();
		//layout.addMember(header);
		layout.addMember(listGrid);
		this.addMember(layout);

	}

	public EmailAttachmentDownloadListgrid getListGrid() {
		return listGrid;
	}
	
	

}

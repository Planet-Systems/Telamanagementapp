package com.planetsystems.tela.managementapp.client.presenter.emailattachmentdownload;

import java.util.List;

import com.planetsystems.tela.dto.EmailAttachmentDownloadDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.types.SelectionStyle;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class EmailAttachmentDownloadListgrid extends SuperListGrid {
	public static String ID = "id";

	public static String Sender = "Sender";
	public static String Subject = "Subject";
	public static String SentDate = "SentDate";
	public static String HasAttachment = "HasAttachment";
	public static String Attachments = "Attachments";
	public static String ProcessingStatus = "ProcessingStatus";
	public static String SenderEmail = "SenderEmail";
	public static String AttachmentType = "AttachmentType";
	public static String Submissions = "Submissions";

	private EmailAttachmentDownloadSource dataSource;

	public EmailAttachmentDownloadListgrid() {
		super();

		dataSource = EmailAttachmentDownloadSource.getInstance();

		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField sender = new ListGridField(Sender, "Sender Details");
		ListGridField subject = new ListGridField(Subject, "Subject");
		ListGridField sentDate = new ListGridField(SentDate, "Date");
		ListGridField hasAttachment = new ListGridField(HasAttachment, "Has Attachment");
		ListGridField attachments = new ListGridField(Attachments, "Attachments");
		attachments.setHidden(true);
		ListGridField processingStatus = new ListGridField(ProcessingStatus, "Status");
		ListGridField senderEmail = new ListGridField(SenderEmail, "Email");
		ListGridField attachmentType = new ListGridField(AttachmentType, "Attachment Type");
		ListGridField submissions = new ListGridField(Submissions, "No. Submissions");

		this.setDataSource(dataSource);
		this.setFields(idField, senderEmail, sender, subject, sentDate, hasAttachment, attachmentType, submissions,
				attachments, processingStatus);
		this.setSelectionType(SelectionStyle.SIMPLE);
		this.setWrapHeaderTitles(true);
	}

	public ListGridRecord addRowData(EmailAttachmentDownloadDTO dto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, dto.getId());
		record.setAttribute(Sender, dto.getSender());
		record.setAttribute(Subject, dto.getSubject());
		record.setAttribute(SentDate, dto.getSentDate());
		
		if(dto.isHasAttachment()) {
			record.setAttribute(HasAttachment, "Yes");
		}else {
			record.setAttribute(HasAttachment, "No");
		}
		
		record.setAttribute(Attachments, dto.getAttachments());
		record.setAttribute(ProcessingStatus, dto.getProcessingStatus());
		record.setAttribute(SenderEmail, dto.getSenderEmail());
		record.setAttribute(AttachmentType, dto.getAttachmentType());
		record.setAttribute(Submissions, dto.getSubmissions());

		return record;
	}

	public void addRecordsToGrid(List<EmailAttachmentDownloadDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (EmailAttachmentDownloadDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		dataSource.setTestData(records);
	}

	public static class EmailAttachmentDownloadSource extends DataSource {

		private static EmailAttachmentDownloadSource instance = null;

		public static EmailAttachmentDownloadSource getInstance() {
			if (instance == null) {
				instance = new EmailAttachmentDownloadSource("EmailAttachmentDownloadSource");
			}
			return instance;
		}

		public EmailAttachmentDownloadSource(String id) {
			setID(id);

			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);

			DataSourceTextField sender = new DataSourceTextField(Sender, "Sender Details");
			DataSourceTextField subject = new DataSourceTextField(Subject, "Subject");
			DataSourceTextField sentDate = new DataSourceTextField(SentDate, "Date");
			DataSourceTextField hasAttachment = new DataSourceTextField(HasAttachment, "Has Attachment");
			DataSourceTextField attachments = new DataSourceTextField(Attachments, "Attachments");
			DataSourceTextField processingStatus = new DataSourceTextField(ProcessingStatus, "Status");
			DataSourceTextField senderEmail = new DataSourceTextField(SenderEmail, "Email");
			DataSourceTextField attachmentType = new DataSourceTextField(AttachmentType, "Attachment Type");
			DataSourceTextField submissions = new DataSourceTextField(Submissions, "No. Submissions");

			this.setFields(idField, senderEmail, sender, subject, sentDate, hasAttachment, attachmentType, submissions,
					attachments, processingStatus);
			setClientOnly(true);

		}
	}

}

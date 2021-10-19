package com.planetsystems.tela.managementapp.client.presenter.login.forgotpassword;

import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ForgotPasswordWindow extends Window {

	private TextField emailField;

	private IButton saveButton;
	private IButton cancelButton;

	public ForgotPasswordWindow() {
		super();

		Label forgotPassword = new Label();
		forgotPassword.setContents("Get help Signing in TELA");
		forgotPassword.setAutoHeight();
		forgotPassword.setStyleName("forgot-password");
		forgotPassword.setMargin(20);

		Label lable2 = new Label();
		lable2.setContents(
				"<p style='margin-left:30px; font-size: 14px;'>An email notification with a temporary password will be sent to this email.</p>");
		lable2.setAutoHeight();

		emailField = new TextField();
		emailField.setWrapTitle(false);
		emailField.setTitle(
				"<span style=' color: #000000; font-weight: bold; font-size: 14px;'>Email </span><span style=' color: red; font-weight: bold; font-size: 14px;'>*</span>");

		emailField.setUsePlaceholderForHint(true);
		emailField.setShowHintInField(true);
		emailField.setHint("Enter your email address");

		saveButton = new IButton("Submit");
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button"); 
		

		DynamicForm form = new DynamicForm();
		form.setFields(emailField);
		form.setWrapItemTitles(true);
		form.setMargin(15);
		form.setNumCols(2);
		form.setColWidths("150", "400");
		form.setCellPadding(5);

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton,saveButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setWidth100();
		buttonLayout.setMargin(20);
		buttonLayout.setMembersMargin(10);
		buttonLayout.setAlign(Alignment.CENTER);

		VLayout layout = new VLayout();
		layout.addMember(forgotPassword);
		layout.addMember(form);
		layout.addMember(lable2);
		layout.addMember(buttonLayout);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("45%");
		this.setHeight("50%");
		this.setAutoCenter(true);
		this.setTitle("Forgot password");
		this.setIsModal(true);
		this.setShowModalMask(true);
		this.setShowCloseButton(false);
		this.setShowHeader(false);
		this.setShowFooter(false);
		this.setCanDrag(false);
		this.setCanDrop(false);
		onCancelButtonClicked(this);
	}

	private void onCancelButtonClicked(final Window window) {
		cancelButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				window.close();

			}
		});
	}



	public TextField getEmailField() {
		return emailField;
	}

	public void setEmailField(TextField emailField) {
		this.emailField = emailField;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

}
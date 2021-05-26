package com.planetsystems.tela.managementapp.client.presenter.login.changepassword;

import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.PasswordItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class ChangePasswordWindow extends Window {
	private PasswordItem oldPasswordField;
	private PasswordItem newPasswordField;
	private PasswordItem comfirmPasswoField;

	private IButton changeButton;
	private IButton cancelButton;
	public ChangePasswordWindow() {
		super();
		oldPasswordField = new PasswordItem();
		oldPasswordField.setTitle("Old Password");
		oldPasswordField.setHint("old password");
		oldPasswordField.setShowHintInField(true);

		newPasswordField = new PasswordItem();
		newPasswordField.setTitle("New password");
		newPasswordField.setHint("new password");
		newPasswordField.setShowHintInField(true);
		
		comfirmPasswoField = new PasswordItem();
		comfirmPasswoField.setTitle("Comfirm password");
		comfirmPasswoField.setHint("confirm password");
		comfirmPasswoField.setShowHintInField(true);
		

		changeButton = new IButton("Change Password");
		
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");
		
		DynamicForm form = new DynamicForm();
		form.setFields(oldPasswordField, newPasswordField  , comfirmPasswoField);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setColWidths("150","250");
		form.setCellPadding(10);

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton , changeButton);
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
		this.setWidth("40%");
		this.setHeight("50%");
		this.setAutoCenter(true);
		this.setTitle("Region");
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


	public PasswordItem getOldPasswordField() {
		return oldPasswordField;
	}


	public void setOldPasswordField(PasswordItem oldPasswordField) {
		this.oldPasswordField = oldPasswordField;
	}


	public PasswordItem getNewPasswordField() {
		return newPasswordField;
	}


	public void setNewPasswordField(PasswordItem newPasswordField) {
		this.newPasswordField = newPasswordField;
	}


	public PasswordItem getComfirmPasswoField() {
		return comfirmPasswoField;
	}


	public void setComfirmPasswoField(PasswordItem comfirmPasswoField) {
		this.comfirmPasswoField = comfirmPasswoField;
	}


	public IButton getChangeButton() {
		return changeButton;
	}


	public void setChangeButton(IButton changeButton) {
		this.changeButton = changeButton;
	}
	
	
	
	

}

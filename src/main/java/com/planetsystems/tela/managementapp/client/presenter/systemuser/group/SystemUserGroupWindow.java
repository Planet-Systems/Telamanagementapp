package com.planetsystems.tela.managementapp.client.presenter.systemuser.group;
  
import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.CheckboxItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class UserGroupWindow extends Window {

	private TextField roleCode;
	private TextField roleName;
	private TextField description;
	private CheckboxItem defaultRole;
	private CheckboxItem receiveAlerts;
	
	private CheckboxItem administrativeRole;

	private IButton saveButton;
	private IButton cancelButton;

	public UserGroupWindow() {
		super();
		roleCode = new TextField();
		roleCode.setTitle("code");

		roleName = new TextField();
		roleName.setTitle("Name");

		description = new TextField();
		description.setTitle("Description");

		defaultRole = new CheckboxItem();
		defaultRole.setTitle("Is Default?");

		receiveAlerts = new CheckboxItem();
		receiveAlerts.setTitle("Receive Project activation Alerts?");
		
		administrativeRole=new CheckboxItem();
		administrativeRole.setTitle("Manage Data");
		

		saveButton = new IButton("Save");
		cancelButton = new IButton("Cancel");

		DynamicForm form = new DynamicForm();
		form.setFields(roleCode, roleName, description, defaultRole, receiveAlerts,administrativeRole);
		form.setWrapItemTitles(false);
		form.setMargin(10);

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(saveButton, cancelButton);
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
		this.setHeight("45%");
		this.setAutoCenter(true);
		this.setTitle("User Groups");
		this.setIsModal(true);
		this.setShowModalMask(true);

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

	public TextField getRoleCode() {
		return roleCode;
	}

	public TextField getRoleName() {
		return roleName;
	}

	public TextField getDescription() {
		return description;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public CheckboxItem getDefaultRole() {
		return defaultRole;
	}

	public CheckboxItem getReceiveAlerts() {
		return receiveAlerts;
	}

	public CheckboxItem getAdministrativeRole() {
		return administrativeRole;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

}

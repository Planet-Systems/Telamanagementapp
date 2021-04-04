package com.planetsystems.tela.managementapp.client.presenter.systemuser;
 
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.MultiComboBoxItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SystemMenuWindow extends Window {

	private ComboBox navigationMenu;
	private MultiComboBoxItem navigationMenuItem;

	private IButton saveButton;
	private IButton cancelButton;

	public SystemMenuWindow() {
		super();

		navigationMenu = new ComboBox();
		navigationMenu.setTitle("Navigation Menu");

		navigationMenuItem = new MultiComboBoxItem();
		navigationMenuItem.setTitle("Menu Item");

		saveButton = new IButton("Save");
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		DynamicForm form = new DynamicForm();
		form.setFields(navigationMenu, navigationMenuItem);
		form.setWrapItemTitles(false);
		form.setMargin(10);

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton, saveButton);
		buttonLayout.setAutoHeight();
		buttonLayout.setWidth100();
		buttonLayout.setMargin(5);
		buttonLayout.setMembersMargin(4);

		VLayout layout = new VLayout();
		layout.addMember(form);
		layout.addMember(buttonLayout);

		layout.setMargin(10);
		this.addItem(layout);
		this.setWidth("30%");
		this.setHeight("40%");
		this.setAutoCenter(true);
		this.setTitle("System Menu Setup");
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

	public ComboBox getNavigationMenu() {
		return navigationMenu;
	}

	public MultiComboBoxItem getNavigationMenuItem() {
		return navigationMenuItem;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

}

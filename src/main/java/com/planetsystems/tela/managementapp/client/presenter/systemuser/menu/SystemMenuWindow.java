package com.planetsystems.tela.managementapp.client.presenter.systemuser.menu;
 
import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.MultiComboBoxItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class SystemMenuWindow extends Window {

	private ComboBox navigationMenuCombo;
	private MultiComboBoxItem subMenuItemCombo;

	private IButton saveButton;
	private IButton cancelButton;

	public SystemMenuWindow() {
		super();

		navigationMenuCombo = new ComboBox();
		navigationMenuCombo.setTitle("Navigation Menu");

		subMenuItemCombo = new MultiComboBoxItem();
		subMenuItemCombo.setTitle("Menu Item");

		saveButton = new IButton("Save");
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		DynamicForm form = new DynamicForm();
		form.setFields(navigationMenuCombo, subMenuItemCombo);
		form.setWrapItemTitles(false);
		form.setCellPadding(8);
		form.setColWidths("150", "250" , "150", "250" );


		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton, saveButton);
		buttonLayout.setAlign(Alignment.CENTER);
		buttonLayout.setLayoutAlign(Alignment.CENTER);
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


	
	
	public ComboBox getNavigationMenuCombo() {
		return navigationMenuCombo;
	}

	public MultiComboBoxItem getSubMenuItemCombo() {
		return subMenuItemCombo;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public IButton getCancelButton() {
		return cancelButton;
	}

}

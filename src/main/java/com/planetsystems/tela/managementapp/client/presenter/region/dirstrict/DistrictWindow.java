package com.planetsystems.tela.managementapp.client.presenter.region.dirstrict;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class DistrictWindow extends Window {

	private TextField districtCode;
	private TextField districtName;
	private ComboBox region;

	private ComboBox rolledOut;

	private IButton saveButton;
	private IButton cancelButton;

	public DistrictWindow() {
		super();
		districtCode = new TextField();
		districtCode.setTitle("Code");
		districtCode.setHint("Code");
		districtCode.setShowHintInField(true);

		districtName = new TextField();
		districtName.setTitle("Local Government");
		districtName.setHint("District/City/Municipality");
		districtName.setShowHintInField(true);

		region = new ComboBox();
		region.setTitle("Sub-Region");
		region.setHint("Sub-Region");
		region.setShowHintInField(true);

		rolledOut = new ComboBox();
		rolledOut.setTitle("Rolled Out");
		rolledOut.setHint("Rolled Out");
		rolledOut.setShowHintInField(true);

		saveButton = new IButton("Save");

		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		DynamicForm form = new DynamicForm();
		form.setFields(region, rolledOut, districtCode, districtName);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setColWidths("150","250");
		form.setCellPadding(10);

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton, saveButton);
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
		this.setHeight("60%");
		this.setAutoCenter(true);
		this.setTitle("Local Government (District/City/Municipality) Setup");
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

	public TextField getDistrictCode() {
		return districtCode;
	}

	public TextField getDistrictName() {
		return districtName;
	}

	public ComboBox getRegion() {
		return region;
	}

	public IButton getSaveButton() {
		return saveButton;
	}

	public ComboBox getRolledOut() {
		return rolledOut;
	}

}

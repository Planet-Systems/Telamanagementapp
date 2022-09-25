package com.planetsystems.tela.managementapp.client.presenter.schoolcategory.school;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class AssignRolloutPhaseWindow extends Window {

	private IButton saveButton;
	private IButton cancelButton;
	private ComboBox rolloutPhaseCombo;

	public AssignRolloutPhaseWindow() {
		super();

		DynamicForm form = new DynamicForm();
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setColWidths("150", "250");
		form.setCellPadding(5);

		rolloutPhaseCombo = new ComboBox();
		rolloutPhaseCombo.setTitle("Rollout Phase");
		rolloutPhaseCombo.setHint("Select");
		rolloutPhaseCombo.setShowHintInField(true);

		form.setFields(rolloutPhaseCombo);

		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		saveButton = new IButton("Assign");

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
		this.setHeight("30%");
		this.setAutoCenter(true);
		this.setTitle("Assign School Level");
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

	public IButton getSaveButton() {
		return saveButton;
	}

	public ComboBox getRolloutPhaseCombo() {
		return rolloutPhaseCombo;
	}

}

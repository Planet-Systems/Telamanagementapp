package com.planetsystems.tela.managementapp.client.presenter.staffdailytask;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.planetsystems.tela.managementapp.client.widget.TextField;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TimeItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class AddStaffDailyTaskWindow extends Window {

	private ComboBox schoolClassCombo;
	private ComboBox subjectCombo;
	private TimeItem startTime;
	private TimeItem endTime;
    
	

	private IButton saveButton;
	private IButton cancelButton;

	public AddStaffDailyTaskWindow() {
		super();
		
		subjectCombo = new ComboBox();
		subjectCombo.setTitle("Subject");
		subjectCombo.setHint("Subject");
		subjectCombo.setShowHintInField(true);
		
		schoolClassCombo = new ComboBox();
		schoolClassCombo.setTitle("School Class");
		schoolClassCombo.setHint("Class");
		schoolClassCombo.setShowHintInField(true);
		
		startTime = new TimeItem();
		startTime.setTitle("StartTime");
		startTime.setHint("H:M");
		startTime.setShowHintInField(true);
		startTime.setUseMask(true);
		
		endTime = new TimeItem();
		endTime.setTitle("StartTime");
		endTime.setHint("H:M");
		endTime.setShowHintInField(true);
		endTime.setUseMask(true);
	

		saveButton = new IButton("Save");
		
		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		DynamicForm form = new DynamicForm();
		form.setFields(subjectCombo, schoolClassCombo , startTime , endTime);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setColWidths("150","250");
		form.setCellPadding(10);

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton , saveButton);
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
		this.setHeight("40%");
		this.setAutoCenter(true);
		this.setTitle("Daily Tasks");
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

	public IButton getCancelButton() {
		return cancelButton;
	}

	public ComboBox getSchoolClassCombo() {
		return schoolClassCombo;
	}

	public ComboBox getSubjectCombo() {
		return subjectCombo;
	}

	public TimeItem getStartTime() {
		return startTime;
	}

	public TimeItem getEndTime() {
		return endTime;
	}
	
	

	
}

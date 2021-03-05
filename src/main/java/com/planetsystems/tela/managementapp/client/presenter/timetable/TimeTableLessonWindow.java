package com.planetsystems.tela.managementapp.client.presenter.timetable;

import com.planetsystems.tela.managementapp.client.widget.ComboBox;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.TimeItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;

public class TimeTableLessonWindow extends Window {

	private ComboBox schoolClassCombo;
	private ComboBox subjectCombo;
	private TimeItem startTime;
	private TimeItem endTime;

	private ComboBox staffCombo;
	private ComboBox dayCombo;

	private IButton addRecordButton;

	private IButton cancelButton;

	public TimeTableLessonWindow() {
		super();
		
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
		
		schoolClassCombo = new ComboBox();
		schoolClassCombo.setTitle("SchoolClass");
		schoolClassCombo.setHint("SchoolClass");
		schoolClassCombo.setShowHintInField(true);
		
		subjectCombo = new ComboBox();
		subjectCombo.setTitle("Subject");
		subjectCombo.setHint("Subject");
		subjectCombo.setShowHintInField(true);

		staffCombo = new ComboBox();
		staffCombo.setTitle("Staff");
		staffCombo.setHint("Teacher");
		staffCombo.setShowHintInField(true);
		
		dayCombo = new ComboBox();
		dayCombo.setTitle("Day");
		dayCombo.setHint("Day");
		dayCombo.setShowHintInField(true);

		addRecordButton = new IButton("Add");

		cancelButton = new IButton("Cancel");
		cancelButton.setBaseStyle("cancel-button");

		DynamicForm form = new DynamicForm();
		form.setFields(schoolClassCombo , subjectCombo , staffCombo , dayCombo , startTime , endTime);
		form.setWrapItemTitles(false);
		form.setMargin(10);
		form.setColWidths("150","250");
		form.setCellPadding(10);

		HLayout buttonLayout = new HLayout();
		buttonLayout.setMembers(cancelButton, addRecordButton);
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
		this.setWidth("50%");
		this.setHeight("70%");
		this.setAutoCenter(true);
		this.setTitle("Timetable Lesson");
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

	public ComboBox getDayCombo() {
		return dayCombo;
	}


	
	
	public IButton getAddRecordButton() {
		return addRecordButton;
	}

	public ComboBox getStaffCombo() {
		return staffCombo;
	}

	

}

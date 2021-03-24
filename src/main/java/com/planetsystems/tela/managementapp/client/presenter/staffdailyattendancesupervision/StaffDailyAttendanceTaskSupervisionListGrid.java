package com.planetsystems.tela.managementapp.client.presenter.staffdailyattendancesupervision;

import java.util.List;

import com.planetsystems.tela.dto.StaffDailyAttendanceSupervisionDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceTaskSupervisionDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class StaffDailyAttendanceTaskSupervisionListGrid extends SuperListGrid {
	public static final String ID = "id";

	public static final String SUPERVISIOR = "SUPERVISIOR_USERNAME";
	public static final String SUPERVISIOR_ID = "SUPERVISIOR_ID";

	public static final String SUPERVISION_DATE = "SUPERVISION_DATE";
	public static final String SUPERVISION_TIME = "SUPERVISION_TIME";
	
	public static final String TEACHING_STATUS = "teachingStatus";
	public static final String TEACHING_TIME_STATUS = "teachingTimeStatus";

	

	
	
	/*
	 *    private SystemUserDTO supervisorDTO;
    private SchoolStaffDTO schoolStaffDTO;

    private String comment;

    private String attendanceDate;

    private String attendanceTime

	 */
	
	public StaffDailyAttendanceTaskSupervisionListGrid() {
		super();
		ListGridField idField = new ListGridField(ID , "ID");
		idField.setHidden(true);


		ListGridField supervisorField = new ListGridField(SUPERVISIOR, "Supervisor");
		ListGridField supervisorIdField = new ListGridField(SUPERVISIOR_ID, "Supervisor Id");
		supervisorIdField.setHidden(true);
		
		ListGridField supervisionDateField = new ListGridField(SUPERVISION_DATE, "Supervision Date");
		ListGridField supervisionTimeField = new ListGridField(SUPERVISION_TIME, "Supervision Time");
		

		ListGridField teachingStatusField = new ListGridField(TEACHING_STATUS, "TeachingStatus");
		ListGridField teachingTimeStatusField = new ListGridField(TEACHING_TIME_STATUS, "TeachingTimeStatus");
	

		this.setFields(idField  , supervisorIdField , supervisorField ,  supervisionDateField , supervisionTimeField , teachingStatusField ,teachingTimeStatusField);
	}

	public ListGridRecord addRowData(StaffDailyAttendanceTaskSupervisionDTO supervisionTaskSupervisionDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, supervisionTaskSupervisionDTO.getId());
		record.setAttribute(TEACHING_STATUS, supervisionTaskSupervisionDTO.getTeachingStatus());
		record.setAttribute(TEACHING_TIME_STATUS, supervisionTaskSupervisionDTO.getTeachingTimeStatus());
	     
		
		
		if (supervisionTaskSupervisionDTO.getStaffDailyAttendanceSupervisionDTO() != null) {
			record.setAttribute(SUPERVISION_DATE, supervisionTaskSupervisionDTO.getStaffDailyAttendanceSupervisionDTO() .getSupervisionDate());
			record.setAttribute(SUPERVISION_TIME, supervisionTaskSupervisionDTO.getStaffDailyAttendanceSupervisionDTO() .getSupervisionTime());
			
			if(supervisionTaskSupervisionDTO.getStaffDailyAttendanceSupervisionDTO().getSupervisorDTO() != null)
			record.setAttribute(SUPERVISIOR , supervisionTaskSupervisionDTO.getStaffDailyAttendanceSupervisionDTO().getSupervisorDTO().getUserName());;
		}
	
		
			

		return record;
	}

	public void addRecordsToGrid(List<StaffDailyAttendanceTaskSupervisionDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (StaffDailyAttendanceTaskSupervisionDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

	public void addRecordToGrid(StaffDailyAttendanceTaskSupervisionDTO dto) {
		this.addData(addRowData(dto));
	}
	
}

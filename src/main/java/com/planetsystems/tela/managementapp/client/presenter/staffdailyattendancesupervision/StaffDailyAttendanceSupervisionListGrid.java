package com.planetsystems.tela.managementapp.client.presenter.staffdailyattendancesupervision;

import java.util.List;

import com.planetsystems.tela.dto.GeneralUserDetailDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.dto.StaffDailyAttendanceSupervisionDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.data.DataSource;
import com.smartgwt.client.data.fields.DataSourceTextField;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

//TIME ATTENDANCE
public class StaffDailyAttendanceSupervisionListGrid extends SuperListGrid {
	public static final String ID = "id";

	public static final String SUPERVISION_DATE = "supervisionDate";
	public static final String SUPERVISION_TIME = "supervisionTime";
	public static final String CLOCK_IN_TIME = "clockinTime";
	public static final String SUPERVISIOR = "supervisior";
	public static final String SUPERVISIOR_ID = "supervisiorId";
	public static final String SUPERVISIOR_STATUS = "supervisiorStatus";

	public static final String SCHOOL_STAFF = "SCHOOL_STAFF";
	public static final String SCHOOL_STAFF_ID = "staffId";
	
	public static final String COMMENT = "COMMENT";
	
	StaffDailyAttendanceSupervisionDataSource dataSource;


	public StaffDailyAttendanceSupervisionListGrid() {

		dataSource = StaffDailyAttendanceSupervisionDataSource.getInstance();
		
		ListGridField idField = new ListGridField();
		idField.setHidden(true);

		ListGridField supervisionDateField = new ListGridField(SUPERVISION_DATE, "Date");
		ListGridField supervisionTimeField = new ListGridField(SUPERVISION_TIME, "Time");
		ListGridField clockInTimeField = new ListGridField(CLOCK_IN_TIME, "Clockin Time");
		ListGridField commentField = new ListGridField(COMMENT , "Comment");


		ListGridField staffField = new ListGridField(SCHOOL_STAFF, "Staff");
		ListGridField staffIdField = new ListGridField(SCHOOL_STAFF_ID, "Staff Id");
		staffIdField.setHidden(true);
		
		ListGridField supervisiorField = new ListGridField(SUPERVISIOR, " Supervised by");
		ListGridField supervisiordIdField = new ListGridField(SUPERVISIOR_ID, "Supervisior Id");
		supervisiordIdField.setHidden(true);
		
		ListGridField supervisiorStatusField = new ListGridField(SUPERVISIOR_STATUS, " Supervision Status");


		this.setDataSource(dataSource);
//		 Staff , Clockin  time , Supervision Status , Comment , Supervised by  
		this.setFields(idField , staffIdField , supervisiordIdField , staffField , clockInTimeField , supervisionTimeField , commentField ,supervisiorStatusField , supervisiorField);
	
	}

	public ListGridRecord addRowData(StaffDailyAttendanceSupervisionDTO supervisionDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, supervisionDTO.getId());
		record.setAttribute(SUPERVISION_DATE , supervisionDTO.getSupervisionDate());
		record.setAttribute(SUPERVISION_TIME , supervisionDTO.getSupervisionTime());
		record.setAttribute(COMMENT , supervisionDTO.getComment());



		if (supervisionDTO.getSchoolStaffDTO() != null) {
			record.setAttribute(SCHOOL_STAFF_ID, supervisionDTO.getSchoolStaffDTO().getId());
			String fullName = supervisionDTO.getSchoolStaffDTO().getGeneralUserDetailDTO().getFirstName() + " "
					+ supervisionDTO.getSchoolStaffDTO().getGeneralUserDetailDTO().getLastName();
			record.setAttribute(SCHOOL_STAFF, fullName);
		}
		
		if (supervisionDTO.getSupervisorDTO() != null) {
			SchoolStaffDTO supervisorDto = supervisionDTO.getSupervisorDTO();
			GeneralUserDetailDTO supervisorDetailDTO = supervisorDto.getGeneralUserDetailDTO();
			String fullName = supervisorDetailDTO.getFirstName().concat(" ").concat(supervisorDetailDTO.getLastName());
	
			record.setAttribute(SUPERVISIOR_ID ,supervisorDto.getId());
			record.setAttribute(SUPERVISIOR, fullName);
		}


		return record;
	}

	public void addRecordsToGrid(List<StaffDailyAttendanceSupervisionDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (StaffDailyAttendanceSupervisionDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
		dataSource.setTestData(records);
	}

	public void addRecordToGrid(StaffDailyAttendanceSupervisionDTO dto) {
		this.addData(addRowData(dto));
	}
	
	
	public static class StaffDailyAttendanceSupervisionDataSource extends DataSource {

		private static StaffDailyAttendanceSupervisionDataSource instance = null;

		public static StaffDailyAttendanceSupervisionDataSource getInstance() {
			if (instance == null) {
				instance = new StaffDailyAttendanceSupervisionDataSource("StaffDailyAttendanceSupervisionDataSource");
			}
			return instance;
		}

		public StaffDailyAttendanceSupervisionDataSource(String id) {
			setID(id);
			
			DataSourceTextField idField = new DataSourceTextField(ID, "Id");
			idField.setHidden(true);
			idField.setPrimaryKey(true);
			
			DataSourceTextField supervisionDateField = new DataSourceTextField(SUPERVISION_DATE, "Supervision Date");
			DataSourceTextField supervisionTimeField = new DataSourceTextField(SUPERVISION_TIME, "Supervision Time");
			DataSourceTextField commentField = new DataSourceTextField(COMMENT , "Comment");


			DataSourceTextField staffField = new DataSourceTextField(SCHOOL_STAFF, "Staff");
			DataSourceTextField staffIdField = new DataSourceTextField(SCHOOL_STAFF_ID, "Staff Id");
			staffIdField.setHidden(true);
			
			DataSourceTextField supervisiorField = new DataSourceTextField(SUPERVISIOR, "Supervisior");
			DataSourceTextField supervisiordIdField = new DataSourceTextField(SUPERVISIOR_ID, "Supervisior Id");
			supervisiordIdField.setHidden(true);

			this.setFields(idField, staffIdField ,supervisiordIdField , supervisiorField,  staffField, supervisionDateField, supervisionTimeField, commentField);
		
			setClientOnly(true);

		}
	}
	
	
	
}

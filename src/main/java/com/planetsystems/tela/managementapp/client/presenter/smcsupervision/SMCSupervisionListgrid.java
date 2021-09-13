package com.planetsystems.tela.managementapp.client.presenter.smcsupervision;

import java.util.List;

import com.planetsystems.tela.dto.SMCSupervisionDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SMCSupervisionListgrid extends SuperListGrid {

	public static String ID = "id";

	public static String AcademicTerm = "AcademicTerm";
	public static String School = "school";
	public static String Supervisor = "supervisor";

	public static String StaffPresent = "staffPresent";
	public static String StaffAtWork = "staffAtWork";
	public static String Comment = "comment";

	public static String SubmissionDate = "SubmissionDate";

	public static String StaffAtWorkNotWorking = "staffAtWorkNotWorking";
	public static String StaffAtWorkWorking = "staffAtWorkWorking";
	public static String SupervisorStatus = "supervisorStatus";

	public static String Status_p1 = "status_p1";
	public static String Status_p2 = "status_p2";
	public static String Status_p3 = "status_p3";
	public static String Status_p4 = "status_p4";
	public static String Status_p5 = "status_p5";
	public static String Status_p6 = "status_p6";
	public static String Status_p7 = "status_p7";

	public SMCSupervisionListgrid() {
		super();

		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField cademicTerm = new ListGridField(AcademicTerm, "Term");
		cademicTerm.setHidden(true);
		
		ListGridField school = new ListGridField(School, "School");
		school.setHidden(true);
		
		ListGridField supervisor = new ListGridField(Supervisor, "SMC Name");

		ListGridField staffPresent = new ListGridField(StaffPresent, "No. Teachers Present");
		ListGridField staffAtWork = new ListGridField(StaffAtWork, "No. Teachers in Class");
		ListGridField comment = new ListGridField(Comment, "Remarks");

		ListGridField submissionDate = new ListGridField(SubmissionDate, "Date of Visit");

		ListGridField staffAtWorkNotWorking = new ListGridField(StaffAtWorkNotWorking, "No. Teachers Not in Class");
		ListGridField staffAtWorkWorking = new ListGridField(StaffAtWorkWorking, "No. Teachers Teaching");
		ListGridField supervisorStatus = new ListGridField(SupervisorStatus, "Head Teacher");

		ListGridField status_p1 = new ListGridField(Status_p1, "Teacher in P1");
		ListGridField status_p2 = new ListGridField(Status_p2, "Teacher in P2");
		ListGridField status_p3 = new ListGridField(Status_p3, "Teacher in P3");
		ListGridField status_p4 = new ListGridField(Status_p4, "Teacher in P4");
		ListGridField status_p5 = new ListGridField(Status_p5, "Teacher in P5");
		ListGridField status_p6 = new ListGridField(Status_p6, "Teacher in P6");
		ListGridField status_p7 = new ListGridField(Status_p7, "Teacher in P7");

		this.setFields(idField, cademicTerm, school, submissionDate,supervisorStatus, staffPresent, staffAtWork, staffAtWorkWorking,
				staffAtWorkNotWorking, status_p1, status_p2, status_p3, status_p4, status_p5, status_p6, status_p7,
				 supervisor, comment);
		
		this.setWrapHeaderTitles(true);
		this.setHeaderHeight(50); 

	}

	public ListGridRecord addRowData(SMCSupervisionDTO dto) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, dto.getId());

		if (dto.getAcademicTerm() != null) {
			record.setAttribute(AcademicTerm, dto.getAcademicTerm().getTerm());
		}

		if (dto.getSchool() != null) {
			record.setAttribute(School, dto.getSchool().getName());
		}

		if (dto.getSupervisor() != null) {
			if (dto.getSupervisor().getGeneralUserDetailDTO() != null) {
				record.setAttribute(Supervisor, dto.getSupervisor().getGeneralUserDetailDTO().getFirstName() + " "
						+ dto.getSupervisor().getGeneralUserDetailDTO().getLastName());
			}
		}

		record.setAttribute(StaffPresent, dto.getStaffPresent());
		record.setAttribute(StaffAtWork, dto.getStaffAtWork());
		record.setAttribute(Comment, dto.getComment());

		record.setAttribute(SubmissionDate, dto.getSubmissionDate());

		record.setAttribute(StaffAtWorkNotWorking, dto.getStaffAtWorkNotWorking());
		record.setAttribute(StaffAtWorkWorking, dto.getStaffAtWorkWorking());
		record.setAttribute(SupervisorStatus, dto.getSupervisorStatus());

		record.setAttribute(Status_p1, dto.getStatus_p1());
		record.setAttribute(Status_p2, dto.getStatus_p2());
		record.setAttribute(Status_p3, dto.getStatus_p3());
		record.setAttribute(Status_p4, dto.getStatus_p4());
		record.setAttribute(Status_p5, dto.getStatus_p5());
		record.setAttribute(Status_p6, dto.getStatus_p6());
		record.setAttribute(Status_p7, dto.getStatus_p7());

		return record;
	}

	public void addRecordsToGrid(List<SMCSupervisionDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SMCSupervisionDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}

}

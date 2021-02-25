package com.planetsystems.tela.managementapp.client.presenter.schoolstaff;

import java.util.List;

import com.google.gwt.user.client.ui.Tree;
import com.planetsystems.tela.dto.DistrictDTO;
import com.planetsystems.tela.dto.SchoolStaffDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SchoolStaffListGrid extends SuperListGrid {
	public static String ID = "id";
    public static String STAFF_CODE = "staffCode";
    public static String REGISTERED = "registered";
    public static String SCHOOL = "school";
    public static String SCHOOL_ID = "schoolId";
    
    public static String FIRSTNAME = "firstName";
    public static String LASTNAME = "lastName";
    public static String PHONE_NUMBER = "phoneNumber";
    public static String EMAIL = "email";
    public static String DOB = "dob";
    public static String NATIONAL_ID = "nationalId";
    public static String GENDER = "gender";
    public static String NAME_ABREV = "nameAbrev";
    
    
    /*
     *  private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String dob;
    private String nationalId;
    private String gender;
    private String nameAbrev;
     */
    
/*
 *   private String staffCode;
    private String staffType;
    private boolean registered;


    private GeneralUserDetailDTO generalUserDetailDTO;
    private SchoolDTO schoolDTO;
 */
    
   

		

	public SchoolStaffListGrid() { 
		super();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);

		ListGridField staffCodeField = new ListGridField(STAFF_CODE, "Code");
		ListGridField registeredField = new ListGridField(REGISTERED, "Registerd");
		ListGridField schoolField = new ListGridField(SCHOOL, "School");
		
		ListGridField schoolIdField = new ListGridField(SCHOOL_ID, "SchoolId");
		schoolIdField.setHidden(true);
		
		ListGridField firstNameField = new ListGridField(FIRSTNAME, "FirstName");
		ListGridField lastNameField = new ListGridField(LASTNAME, "LastName");
		ListGridField phoneNumberField = new ListGridField(PHONE_NUMBER, "PhoneNumber");
		ListGridField emailField = new ListGridField(EMAIL, "Email");
		ListGridField dobField = new ListGridField(DOB, "DOB");
		ListGridField nationalIdField = new ListGridField(NATIONAL_ID, "NationalId");
		ListGridField genderField = new ListGridField(GENDER, "Gender");
		ListGridField nameAbrevField = new ListGridField(NAME_ABREV, "NameAbbrev");
		   
		     	
		

		this.setFields(idField , schoolIdField , firstNameField , lastNameField , emailField , nameAbrevField, phoneNumberField , nationalIdField , genderField , dobField,schoolField,staffCodeField,registeredField);

	}

	public ListGridRecord addRowData(SchoolStaffDTO schoolStaffDTO) {
		ListGridRecord record = new ListGridRecord();
//		record.setAttribute(ID, districtDTO.getId());
//		record.setAttribute(CODE, districtDTO.getCode());
//		record.setAttribute(NAME, districtDTO.getName());
//		if(districtDTO.getRegion() != null) {
//			record.setAttribute(REGION, districtDTO.getRegion().getName());
//			record.setAttribute(REGION_ID, districtDTO.getRegion().getId());
//		}
//		String roll = districtDTO.isRolledOut() ? "Yes" : "No" ;
//		record.setAttribute(ROLLEDOUT, roll);
//		record.setAttribute(ROLLEDOUT_STATUS, districtDTO.isRolledOut());
//	
		
		return record;
	}

	public void addRecordsToGrid(List<SchoolStaffDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SchoolStaffDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}
}

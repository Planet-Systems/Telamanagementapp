package com.planetsystems.tela.managementapp.client.presenter.systemuser;

import java.util.List;

import com.planetsystems.tela.dto.SystemUserDTO;
import com.planetsystems.tela.managementapp.client.widget.SuperListGrid;
import com.smartgwt.client.widgets.grid.ListGridField;
import com.smartgwt.client.widgets.grid.ListGridRecord;

public class SystemUserListGrid extends SuperListGrid {
	public static final String ID = "ID";
	public static final String USERNAME = "USERNAME";
    public static final String ENABLED = "ENABLED";
    public static final String CONFIG_ROLE = "CONFIGROLE"; 
    public static final String FIRST_NAME = "FIRST_NAME";
    public static final String LAST_NAME = "LAST_NAME";
    public static final String PHONE_NUMBER = "PHONE_NUMBER";
    public static final String EMAIL = "EMAIL";
    public static final String DOB = "DOB";
    public static final String NATIONAL_ID = "NATIONAL_ID";
    public static final String GENDER = "GENDER";
    public static final String NAME_ABBREV = "NAME_ABBREV";

/*
 *private String userName;
    private String password;
    private boolean enabled = true;
    private String configRole;
      private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String dob;
    private String nationalId;
    private String gender;
    private String nameAbbrev;
 * 
 */
		

	public SystemUserListGrid() { 
		super();
		
		ListGridField idField = new ListGridField(ID, "Id");
		idField.setHidden(true);
		
		ListGridField usernameField = new ListGridField(USERNAME, "Username");
		ListGridField firstNameField = new ListGridField(FIRST_NAME, "First Name");
		ListGridField lastNameField = new ListGridField(LAST_NAME, "Last Name");
		
		ListGridField enabledField = new ListGridField(ENABLED, "Enabled");
		
		ListGridField configRoleField = new ListGridField(CONFIG_ROLE, "Role");
		
		ListGridField phoneNumberField = new ListGridField(PHONE_NUMBER, "Phone Number");
		
		ListGridField emailField = new ListGridField(EMAIL, "Email");
		
		ListGridField dobField = new ListGridField(DOB, "D.O.B");
		
		ListGridField nationalIdField = new ListGridField(NATIONAL_ID, "NationalId");

		ListGridField genderField = new ListGridField(GENDER, "Gender");
		
		ListGridField nameAbbrevField = new ListGridField(NAME_ABBREV, "Name Abbreviation");
		

		this.setFields(idField , usernameField , firstNameField , lastNameField , emailField , phoneNumberField , nationalIdField , dobField , genderField , enabledField , nameAbbrevField , configRoleField);

	}

	public ListGridRecord addRowData(SystemUserDTO systemUserDTO) {
		ListGridRecord record = new ListGridRecord();
		record.setAttribute(ID, systemUserDTO.getId());
		record.setAttribute(USERNAME, systemUserDTO.getUserName());
		record.setAttribute(CONFIG_ROLE, systemUserDTO.getConfigRole());
	
        String enabled = systemUserDTO.isEnabled() ? "Yes" : "NO";
        record.setAttribute(ENABLED, enabled);
    
        if(systemUserDTO.getGeneralUserDetailDTO() != null) {
           record.setAttribute(FIRST_NAME, systemUserDTO.getGeneralUserDetailDTO().getFirstName());
            record.setAttribute(LAST_NAME, systemUserDTO.getGeneralUserDetailDTO().getLastName());
            record.setAttribute(EMAIL, systemUserDTO.getGeneralUserDetailDTO().getEmail());
            record.setAttribute(NAME_ABBREV, systemUserDTO.getGeneralUserDetailDTO().getNameAbbrev());
            record.setAttribute(DOB, systemUserDTO.getGeneralUserDetailDTO().getDob());
            record.setAttribute(NATIONAL_ID, systemUserDTO.getGeneralUserDetailDTO().getNationalId());
            record.setAttribute(GENDER, systemUserDTO.getGeneralUserDetailDTO().getGender());
            record.setAttribute(PHONE_NUMBER, systemUserDTO.getGeneralUserDetailDTO().getPhoneNumber());
   	  }
		
		return record;
	}

	public void addRecordsToGrid(List<SystemUserDTO> list) {
		ListGridRecord[] records = new ListGridRecord[list.size()];
		int row = 0;
		for (SystemUserDTO item : list) {
			records[row] = addRowData(item);
			row++;
		}
		this.setData(records);
	}
}

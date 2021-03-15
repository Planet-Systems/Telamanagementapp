package com.planetsystems.tela.managementapp.client.presenter.timetable;

import java.util.Arrays;

import com.planetsystems.tela.managementapp.client.widget.ControlsPane;
import com.planetsystems.tela.managementapp.client.widget.MenuButton;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.VerticalAlignment;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.LayoutSpacer;
import com.smartgwt.client.widgets.layout.VLayout;
import com.smartgwt.client.widgets.menu.MenuItem;

public class ViewTimeTableLessonsPane extends VLayout {

	private LessonListGrid lessonListGrid;
	private Label school;
	private Label district;
	private  Label academicYear;
    private Label academicTerm;
    private ControlsPane controlsPane;
	
	public ViewTimeTableLessonsPane() {
		super();
		Label header = new Label();
		
		header.setStyleName("crm-ContextArea-Header-Label");
		header.setContents("TimeTable");
		header.setPadding(10);
		header.setAutoHeight();
		header.setAutoWidth();
		header.setAlign(Alignment.CENTER);
		header.setLayoutAlign(Alignment.CENTER);
	    header.setMargin(3);
	    
	    HLayout schoolDistrictLayout = new HLayout();
	    schoolDistrictLayout.setPadding(5);
	    schoolDistrictLayout.setAutoHeight();
	    schoolDistrictLayout.setWidth("50%");
//	    schoolDistrictLayout.setBorder("1px solid red");
	    schoolDistrictLayout.setLayoutAlign(Alignment.CENTER);
	    
	    Label schoolLabel = new Label("School: ");
	    schoolLabel.setAutoHeight();
	    schoolLabel.setAutoWidth();
	    schoolLabel.setPadding(2);
	    
	    school = new Label("Kabowa Parents Primary School ");
	    school.setAutoHeight();
	    school.setWrap(false);
	    school.setLayoutAlign(VerticalAlignment.CENTER);
	    schoolLabel.setAutoWidth();
	    schoolLabel.setPadding(2);
	    
	    
	    Label districtLabel = new Label("District: ");
	    districtLabel.setAutoHeight();
	    districtLabel.setAutoWidth();
	    districtLabel.setPadding(2);
	    
	    district = new Label("Kampala");
	    district.setAutoHeight();
	    district.setWrap(false);
	    district.setLayoutAlign(VerticalAlignment.CENTER);
	    district.setAutoWidth();
	    district.setPadding(2);
	    
	    
	    schoolDistrictLayout.setMembers(schoolLabel , school , new LayoutSpacer() , districtLabel , district);
	    
	  
	    HLayout yearTermLayout = new HLayout();
	    yearTermLayout.setPadding(2);
	    yearTermLayout.setWidth("50%");
	    yearTermLayout.setAutoHeight();
//	    yearTermLayout.setBorder("1px solid green");
	    yearTermLayout.setLayoutAlign(Alignment.CENTER);
	    
	    Label academicYearLabel = new Label("AcademicYear: ");
	    academicYearLabel.setAutoHeight();
	    academicYearLabel.setAutoWidth();
	    academicYearLabel.setPadding(2);
	    
	    academicYear = new Label("2021");
	    academicYearLabel.setAutoHeight();
	    academicYearLabel.setAutoWidth();
	    academicYearLabel.setPadding(2);
	    academicYear.setLayoutAlign(VerticalAlignment.CENTER);
	    academicYear.setWrap(false);
	    academicYear.setLayoutAlign(VerticalAlignment.CENTER);
	    
	    
	    Label academicTermLabel = new Label("AcademicTerm: ");
	    academicTermLabel.setAutoHeight();
	    academicTermLabel.setAutoWidth();
	    academicTermLabel.setPadding(2);
	    
	    academicTerm = new Label("III");
	    academicTerm.setAutoHeight();
	    academicTerm.setAutoWidth();
	    academicTerm.setPadding(2);
	    academicTerm.setWrap(false);
	    academicTerm.setLayoutAlign(VerticalAlignment.CENTER);
	    
	    yearTermLayout.setMembers(academicYearLabel , academicYear ,new LayoutSpacer(), academicTermLabel , academicTerm);
	    
	    lessonListGrid = new LessonListGrid();
	    
	    controlsPane = new ControlsPane();
	    MenuButton edit = new MenuButton("edit");
	    controlsPane.addMenuButtons(Arrays.asList(edit));
	    
		this.addMember(header);
		this.addMember(schoolDistrictLayout);
		this.addMember(yearTermLayout);
		this.addMember(controlsPane);
		this.addMember(lessonListGrid);
		
	}

	public LessonListGrid getLessonListGrid() {
		return lessonListGrid;
	}

	public Label getSchool() {
		return school;
	}

	public Label getDistrict() {
		return district;
	}

	public Label getAcademicYear() {
		return academicYear;
	}

	public Label getAcademicTerm() {
		return academicTerm;
	}

	
	
	

	
}

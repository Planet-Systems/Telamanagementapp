package com.planetsystems.tela.managementapp.server;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.ProcessingException;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.server.handlers.AcademicYearTermHandler;
import com.planetsystems.tela.managementapp.server.handlers.ClockInOutHandler;
import com.planetsystems.tela.managementapp.server.handlers.LearnerAttendanceHandler;
import com.planetsystems.tela.managementapp.server.handlers.LearnerEnrollmentHandler;
import com.planetsystems.tela.managementapp.server.handlers.LoginPresenterHandler;
import com.planetsystems.tela.managementapp.server.handlers.MainPresenterHandler;
import com.planetsystems.tela.managementapp.server.handlers.RegionDistrictHandler;
import com.planetsystems.tela.managementapp.server.handlers.SchoolCatergoryClassHandler;
import com.planetsystems.tela.managementapp.server.handlers.SchoolStaffEnrollmentHandler;
import com.planetsystems.tela.managementapp.server.handlers.StaffDailyTimetableHandler;
import com.planetsystems.tela.managementapp.server.handlers.StaffDailyTimetableLessonHandler;
import com.planetsystems.tela.managementapp.server.handlers.StaffDailyTimetableSupervisionHandler;
import com.planetsystems.tela.managementapp.server.handlers.StaffDailyTimetableSupervisionTaskHandler;
import com.planetsystems.tela.managementapp.server.handlers.SubjectCategoryHandler;
import com.planetsystems.tela.managementapp.server.handlers.TimeTableLessonHandler;
import com.planetsystems.tela.managementapp.server.handlers.TimetableHandler;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.MyRequestResult;
import com.planetsystems.tela.managementapp.shared.requestcommands.AcademicYearTermCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.AuthRequestCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.ClockInOutCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.LearnerAttendanceCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.LearnerEnrollmentCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.RegionDistrictCommands;
import com.planetsystems.tela.managementapp.shared.requestcommands.SchoolCategoryClassCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.SchoolStaffEnrollmentCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.StaffDailySupervisionCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.StaffDailySupervisionTaskCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.StaffDailyTimetableCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.StaffDailyTimetableLessonCommands;
import com.planetsystems.tela.managementapp.shared.requestcommands.SubjectCategoryCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemUserGroupRequestCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemUserGroupSystemMenuCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.TimetableCommands;
import com.planetsystems.tela.managementapp.shared.requestcommands.TimetableLessonCommands;

public class MyRequestActionHandler implements ActionHandler<MyRequestAction, MyRequestResult> {

	@Override
	public MyRequestResult execute(MyRequestAction action, ExecutionContext context) throws ActionException {
		try {
			String command = (String) action.getRequestBody().get(MyRequestAction.COMMAND);
			
			switch (command) {
			case AuthRequestCommand.LOGIN:
				MyRequestResult result = new MyRequestResult();
				result.setResponseText(LoginPresenterHandler.login(action));
				return result;

			case SystemUserGroupRequestCommand.LOGGEDIN_SYSTEM_USER_GROUP:
				MyRequestResult result2 = new MyRequestResult();
				result2.setSystemUserGroupResponse(LoginPresenterHandler.loggedUserGroup(action));
				return result2;
				
			case AuthRequestCommand.RESET_PASSWORD:
				MyRequestResult result3 = new MyRequestResult();
				result3.setResponseText(LoginPresenterHandler.resetPassword(action));
				return result3;
				
			case AuthRequestCommand.CHANGE_PASSWORD:
				MyRequestResult result4 = new MyRequestResult();
				result4.setResponseText(MainPresenterHandler.changePassword(action));
				return result4;
				
			case SystemUserGroupSystemMenuCommand.LOGGED_USER_SYSTEM_MENU:
				MyRequestResult result5 = new MyRequestResult();
				result5.setSystemMenuResponseList(MainPresenterHandler.loggedUserSystemMenus(action));
				return result5;
			
			case AcademicYearTermCommand.SAVE_YEAR:
				MyRequestResult result6 = new MyRequestResult();
				result6.setAcademicYearResponse(AcademicYearTermHandler.saveAcademicYear(action));
				return result6;
				
			case AcademicYearTermCommand.GET_ALL_YEARS:
				MyRequestResult result7 = new MyRequestResult();
				result7.setAcademicYearResponseList(AcademicYearTermHandler.getAcademicYears(action));
				return result7;
				
			case AcademicYearTermCommand.GET_YEAR_BY_ID:
				MyRequestResult result17 = new MyRequestResult();
				result17.setAcademicYearResponseList(AcademicYearTermHandler.getAcademicYears(action));
				return result17;
				
			case AcademicYearTermCommand.DELETE_YEAR:
				MyRequestResult result8 = new MyRequestResult();
				result8.setResponseText(AcademicYearTermHandler.deleteAcademicYear(action));
				return result8;
				
			case AcademicYearTermCommand.UPDATE_YEAR:
				MyRequestResult result9 = new MyRequestResult();
				result9.setAcademicYearResponse(AcademicYearTermHandler.updateAcademicYear(action));
				return result9;
				
			case AcademicYearTermCommand.SAVE_TERM:
				MyRequestResult result10 = new MyRequestResult();
				result10.setAcademicTermResponse(AcademicYearTermHandler.saveAcademicTerm(action));
				return result10;
				
			case AcademicYearTermCommand.GET_ALL_TERMS:
				MyRequestResult result11 = new MyRequestResult();
				result11.setAcademicTermResponseList(AcademicYearTermHandler.getAcademicTerms(action));
				return result11;
				
			case AcademicYearTermCommand.GET_TERM_BY_ID:
				MyRequestResult result18 = new MyRequestResult();
				result18.setAcademicTermResponseList(AcademicYearTermHandler.getAcademicTerms(action));
				return result18;
				
				
			case AcademicYearTermCommand.DELETE_TERM:
				MyRequestResult result12 = new MyRequestResult();
				result12.setResponseText(AcademicYearTermHandler.deleteAcademicTerm(action));
				return result12;
				
			case AcademicYearTermCommand.UPDATE_TERM:
				MyRequestResult result13 = new MyRequestResult();
				result13.setAcademicTermResponse(AcademicYearTermHandler.updateAcademicTerm(action));
				return result13;
				
			case AcademicYearTermCommand.GET_ALL_TERMS_BY_YEAR:
				MyRequestResult result14 = new MyRequestResult();
				result14.setAcademicTermResponseList(AcademicYearTermHandler.getAcademicTermByYear(action));
				return result14;
				
			case AcademicYearTermCommand.ACTIVATE_TERM:
				MyRequestResult result15 = new MyRequestResult();
				result15.setAcademicTermResponse(AcademicYearTermHandler.activateAcademicTerm(action));
				return result15;
				
			case AcademicYearTermCommand.DEACTIVATE_TERM:
				MyRequestResult result16 = new MyRequestResult();
				result16.setAcademicTermResponse(AcademicYearTermHandler.deactivateAcademicTerm(action));
				return result16;
				
			case RegionDistrictCommands.SAVE_REGION:
				MyRequestResult result19 = new MyRequestResult();
				result19.setRegionResponse(RegionDistrictHandler.saveRegion(action));
				return result19;
				
			case RegionDistrictCommands.GET_ALL_REGIONS:
				MyRequestResult result20 = new MyRequestResult();
				result20.setRegionResponseList(RegionDistrictHandler.getRegions(action));
				return result20;
				
			case RegionDistrictCommands.GET_REGION_BY_ID:
				MyRequestResult result21 = new MyRequestResult();
				result21.setRegionResponse(RegionDistrictHandler.getRegionById(action));
				return result21;
				
			case RegionDistrictCommands.DELETE_REGION:
				MyRequestResult result22 = new MyRequestResult();
				result22.setResponseText(RegionDistrictHandler.deleteRegion(action));;
				return result22;
				
			case RegionDistrictCommands.UPDATE_REGION:
				MyRequestResult result23 = new MyRequestResult();
				result23.setRegionResponse(RegionDistrictHandler.updateRegion(action));
				return result23;
				
			case RegionDistrictCommands.SAVE_DISTRICT:
				MyRequestResult result24 = new MyRequestResult();
				result24.setDistrictResponse(RegionDistrictHandler.saveDistrict(action));
				return result24;
				
			case RegionDistrictCommands.GET_ALL_DISTRICTS:
				MyRequestResult result25 = new MyRequestResult();
				result25.setDistrictResponseList(RegionDistrictHandler.getDistricts(action));
				return result25;
				
			case RegionDistrictCommands.GET_ALL_DISTRICTS_BY_REGION:
				MyRequestResult result108 = new MyRequestResult();
				result108.setDistrictResponseList(RegionDistrictHandler.getDistrictByRegion(action));
				return result108;
				
			case RegionDistrictCommands.GET_DISTRICT_BY_ID:
				MyRequestResult result26 = new MyRequestResult();
				result26.setDistrictResponse(RegionDistrictHandler.getDistrictById(action));
				return result26;
				
			case RegionDistrictCommands.DELETE_DISTRICT:
				MyRequestResult result27 = new MyRequestResult();
				result27.setResponseText(RegionDistrictHandler.deleteDistrict(action));;
				return result27;
				
			case RegionDistrictCommands.UPDATE_DISTRICT:
				MyRequestResult result28 = new MyRequestResult();
				result28.setDistrictResponse(RegionDistrictHandler.updateDistrict(action));
				return result28;
				
			case RegionDistrictCommands.GET_REGIONS_BY_SYSTEM_USER_PROFILE_SCHOOLS:
				MyRequestResult result29 = new MyRequestResult();
				result29.setRegionResponseList(RegionDistrictHandler.getAllRegionsBySystemUserProfileSchools(action));
				return result29;
				
			case RegionDistrictCommands.GET_DISTRICTS_BY_SYSTEM_USER_PROFILE_SCHOOLS:
				MyRequestResult result30 = new MyRequestResult();
				result30.setDistrictResponseList(RegionDistrictHandler.getAllDistrictsBySystemUserProfileSchools(action));
				return result30;
				
			case SchoolCategoryClassCommand.SAVE_SCHOOL_CATEGORY:
				MyRequestResult result31 = new MyRequestResult();
				result31.setSchoolCategoryResponse(SchoolCatergoryClassHandler.saveSchoolCategory(action));
				return result31;
				
			case SchoolCategoryClassCommand.GET_ALL_SCHOOL_CATEGORYS:
				MyRequestResult result32 = new MyRequestResult();
				result32.setSchoolCategoryResponseList(SchoolCatergoryClassHandler.getSchoolCategorys(action));
				return result32;
				
			case SchoolCategoryClassCommand.GET_SCHOOL_CATEGORY_BY_ID:
				MyRequestResult result33 = new MyRequestResult();
				result33.setSchoolCategoryResponse(SchoolCatergoryClassHandler.getSchoolCategoryById(action));;
				return result33;
				
			case SchoolCategoryClassCommand.DELETE_SCHOOL_CATEGORY:
				MyRequestResult result34 = new MyRequestResult();
				result34.setResponseText(SchoolCatergoryClassHandler.deleteSchoolCategory(action));
				return result34;
				
			case SchoolCategoryClassCommand.UPDATE_SCHOOL_CATEGORY:
				MyRequestResult result35 = new MyRequestResult();
				result35.setSchoolCategoryResponse(SchoolCatergoryClassHandler.updateSchoolCategory(action));;
				return result35;
				
			case SchoolCategoryClassCommand.GET_SCHOOL_CATEGORIES_BY_SYSTEM_USER_PROFILE_SCHOOLS:
				MyRequestResult result36 = new MyRequestResult();
				result36.setSchoolCategoryResponseList(SchoolCatergoryClassHandler.getAllSchoolCategoriesByLoggedSystemUserProfileSchools(action));;
				return result36;
				
			case SchoolCategoryClassCommand.SAVE_SCHOOL:
				MyRequestResult result37 = new MyRequestResult();
				result37.setSchoolResponse(SchoolCatergoryClassHandler.saveSchool(action));
				return result37;
				
			case SchoolCategoryClassCommand.GET_ALL_SCHOOLS:
				MyRequestResult result38 = new MyRequestResult();
				result38.setSchoolResponseList(SchoolCatergoryClassHandler.getSchools(action));
				return result38;
				
			case SchoolCategoryClassCommand.GET_SCHOOL_BY_ID:
				MyRequestResult result39 = new MyRequestResult();
				result39.setSchoolResponse(SchoolCatergoryClassHandler.getSchoolById(action));
				return result39;
				
			case SchoolCategoryClassCommand.GET_ALL_SCHOOLS_BY_SCHOOL_CATEGORY:
				MyRequestResult result40 = new MyRequestResult();
				result40.setSchoolResponseList(SchoolCatergoryClassHandler.getSchoolsByCategory(action));
				return result40;
				
			case SchoolCategoryClassCommand.GET_ALL_SCHOOLS_BY_SCHOOL_DISTRICT:
				MyRequestResult result41 = new MyRequestResult();
				result41.setSchoolResponseList(SchoolCatergoryClassHandler.getSchoolsByDistrict(action));
				return result41;
				
			case SchoolCategoryClassCommand.DELETE_SCHOOL:
				MyRequestResult result42 = new MyRequestResult();
				result42.setResponseText(SchoolCatergoryClassHandler.deleteSchool(action));
				return result42;
				
			case SchoolCategoryClassCommand.UPDATE_SCHOOL:
				MyRequestResult result43 = new MyRequestResult();
				result43.setSchoolResponse(SchoolCatergoryClassHandler.updateSchool(action));
				return result43;
				
			case SchoolCategoryClassCommand.GET_SCHOOLS_BY_SYSTEM_USER_PROFILE_SCHOOLS:
				MyRequestResult result44 = new MyRequestResult();
				result44.setSchoolResponseList(SchoolCatergoryClassHandler.getAllSchoolsByLoggedSystemUserProfileSchools(action));;
				return result44;
				
			case SchoolCategoryClassCommand.SAVE_SCHOOL_CLASS:
				MyRequestResult result45 = new MyRequestResult();
				result45.setSchoolClassResponse(SchoolCatergoryClassHandler.saveSchoolClass(action));
				return result45;
				
			case SchoolCategoryClassCommand.GET_ALL_SCHOOL_CLASSES:
				MyRequestResult result46 = new MyRequestResult();
				result46.setSchoolClassResponseList(SchoolCatergoryClassHandler.getSchoolClassses(action));
				return result46;
				
			case SchoolCategoryClassCommand.GET_SCHOOL_CLASS_BY_ID:
				MyRequestResult result47 = new MyRequestResult();
				result47.setSchoolClassResponse(SchoolCatergoryClassHandler.getSchoolClassById(action));
				return result47;
				
//			case SchoolCategoryClassCommand.GET_ALL_SCHOOL_CLASSES_BY_SCHOOL:
//				MyRequestResult result48 = new MyRequestResult();
//				result48.setSchoolClassResponseList(SchoolCatergoryClassHandler.getSchoolClassesBySchool(action));
//				return result48;
		
				
			case SchoolCategoryClassCommand.DELETE_SCHOOL_CLASS:
				MyRequestResult result49 = new MyRequestResult();
				result49.setResponseText(SchoolCatergoryClassHandler.deleteSchoolClass(action));
				return result49;
				
			case SchoolCategoryClassCommand.UPDATE_SCHOOL_CLASS:
				MyRequestResult result50 = new MyRequestResult();
				result50.setSchoolClassResponse(SchoolCatergoryClassHandler.updateSchoolClass(action));
				return result50;
				
			case SchoolCategoryClassCommand.GET_SCHOOL_CLASSES_BY_SYSTEM_USER_PROFILE_SCHOOLS:
				MyRequestResult result51 = new MyRequestResult();
				result51.setSchoolClassResponseList(SchoolCatergoryClassHandler.getAllClassesByLoggedSystemUserProfileSchools(action));;
				return result51;
				
			case SubjectCategoryCommand.SAVE_SUBJECT_CATEGORY:
				MyRequestResult result52 = new MyRequestResult();
				result52.setSubjectCategoryResponse(SubjectCategoryHandler.saveSubjectCategory(action));
				return result52;
				
			case SubjectCategoryCommand.GET_ALL_SUBJECT_CATEGORYS:
				MyRequestResult result53 = new MyRequestResult();
				result53.setSubjectCategoryResponseList(SubjectCategoryHandler.getSubjectCategorys(action));
				return result53;
				
			case SubjectCategoryCommand.GET_SUBJECT_CATEGORY_BY_ID:
				MyRequestResult result54 = new MyRequestResult();
				result54.setSubjectCategoryResponse(SubjectCategoryHandler.getSubjectCategoryById(action));
				return result54;
				
			case SubjectCategoryCommand.DELETE_SUBJECT_CATEGORY:
				MyRequestResult result55 = new MyRequestResult();
				result55.setResponseText(SubjectCategoryHandler.deleteSubjectCategory(action));
				return result55;
				
			case SubjectCategoryCommand.UPDATE_SUBJECT_CATEGORY:
				MyRequestResult result56 = new MyRequestResult();
				result56.setSubjectCategoryResponse(SubjectCategoryHandler.updateSubjectCategory(action));
				return result56;
				
			case SubjectCategoryCommand.SAVE_SUBJECT:
				MyRequestResult result57 = new MyRequestResult();
				result57.setSubjectResponse(SubjectCategoryHandler.saveSubject(action));
				return result57;
				
			case SubjectCategoryCommand.GET_ALL_SUBJECTS:
				MyRequestResult result58 = new MyRequestResult();
				result58.setSubjectResponseList(SubjectCategoryHandler.getSubjects(action));
				return result58;
				
			case SubjectCategoryCommand.GET_SUBJECT_BY_ID:
				MyRequestResult result59 = new MyRequestResult();
				result59.setSubjectResponse(SubjectCategoryHandler.getSubjectById(action));
				return result59;
				
				
			case SubjectCategoryCommand.DELETE_SUBJECT:
				MyRequestResult result60 = new MyRequestResult();
				result60.setResponseText(SubjectCategoryHandler.deleteSubject(action));
				return result60;
				
			case SubjectCategoryCommand.UPDATE_SUBJECT:
				MyRequestResult result61 = new MyRequestResult();
				result61.setSubjectResponse(SubjectCategoryHandler.updateSubject(action));
				return result61;
				
			case SubjectCategoryCommand.GET_ALL_SUBJECTS_BY_SUBJECT_CATEGORY:
				MyRequestResult result62 = new MyRequestResult();
				result62.setSubjectResponseList(SubjectCategoryHandler.getSubjectBySubjectCategory(action));
				return result62;
				
			case LearnerEnrollmentCommand.SAVE_LEARNER_ENROLLMENT:
				MyRequestResult result63 = new MyRequestResult();
				result63.setLearnerEnrollmentResponse(LearnerEnrollmentHandler.saveLearnerEnrollment(action));
				return result63;
				
			case LearnerEnrollmentCommand.GET_ALL_LEARNER_ENROLLMENTS:
				MyRequestResult result64 = new MyRequestResult();
				result64.setLearnerEnrollmentResponseList(LearnerEnrollmentHandler.getLearnerEnrollments(action));
				return result64;
				
			case LearnerEnrollmentCommand.GET_LEARNER_ENROLLMENT_BY_ID:
				MyRequestResult result65 = new MyRequestResult();
				result65.setLearnerEnrollmentResponse(LearnerEnrollmentHandler.getLearnerEnrollmentById(action));
				return result65;
				
			case LearnerEnrollmentCommand.DELETE_LEARNER_ENROLLMENT:
				MyRequestResult result66 = new MyRequestResult();
				result66.setResponseText(LearnerEnrollmentHandler.deleteLearnerEnrollment(action));
				return result66;
				
			case LearnerEnrollmentCommand.UPDATE_LEARNER_ENROLLMENT:
				MyRequestResult result67 = new MyRequestResult();
				result67.setLearnerEnrollmentResponse(LearnerEnrollmentHandler.updateLearnerEnrollment(action));
				return result67;
				
			case LearnerEnrollmentCommand.GET_LEARNER_ENROLLMENTS_BY_SYSTEM_USER_PROFILE_SCHOOLS:
				MyRequestResult result68 = new MyRequestResult();
				result68.setLearnerEnrollmentResponseList(LearnerEnrollmentHandler.getLearnerEnrollmentsByLoggedSystemUserProfileSchools(action));
				return result68;
				
			case LearnerEnrollmentCommand.GET_LEARNER_ENROLLMENTS_BY_ACADEMIC_TERM_SCHOOL:
				MyRequestResult result69 = new MyRequestResult();
				result69.setLearnerEnrollmentResponseList(LearnerEnrollmentHandler.getLearnerEnrollmentsByAcademicTermSchool(action));
				return result69;
				
			case SchoolStaffEnrollmentCommand.SAVE_STAFF_ENROLLMENT:
				MyRequestResult result70 = new MyRequestResult();
				result70.setStaffEnrollmentResponse(SchoolStaffEnrollmentHandler.saveStaffEnrollment(action));
				return result70;
				
			case SchoolStaffEnrollmentCommand.GET_ALL_STAFF_ENROLLMENTS:
				MyRequestResult result71 = new MyRequestResult();
				result71.setStaffEnrollmentResponseList(SchoolStaffEnrollmentHandler.getStaffEnrollments(action));
				return result71;
				
			case SchoolStaffEnrollmentCommand.GET_STAFF_ENROLLMENT_BY_ID:
				MyRequestResult result72 = new MyRequestResult();
				result72.setStaffEnrollmentResponse(SchoolStaffEnrollmentHandler.getStaffEnrollmentById(action));
				return result72;
				
			case SchoolStaffEnrollmentCommand.DELETE_STAFF_ENROLLMENT:
				MyRequestResult result73 = new MyRequestResult();
				result73.setResponseText(SchoolStaffEnrollmentHandler.deleteStaffEnrollment(action));
				return result73;
				
			case SchoolStaffEnrollmentCommand.UPDATE_STAFF_ENROLLMENT:
				MyRequestResult result74 = new MyRequestResult();
				result74.setStaffEnrollmentResponse(SchoolStaffEnrollmentHandler.updateStaffEnrollment(action));
				return result74;
				
			case SchoolStaffEnrollmentCommand.GET_STAFF_ENROLLMENTS_BY_SYSTEM_USER_PROFILE_SCHOOLS:
				MyRequestResult result75 = new MyRequestResult();
				result75.setStaffEnrollmentResponseList(SchoolStaffEnrollmentHandler.getStaffEnrollmentsByLoggedSystemUserProfileSchools(action));
				return result75;
				
			case SchoolStaffEnrollmentCommand.GET_STAFF_ENROLLMENTS_BY_ACADEMIC_TERM_SCHOOL:
				MyRequestResult result76 = new MyRequestResult();
				result76.setStaffEnrollmentResponseList(SchoolStaffEnrollmentHandler.getStaffEnrollments(action));
				return result76;
				
			case SchoolStaffEnrollmentCommand.SAVE_SCHOOL_STAFF:
				MyRequestResult result77 = new MyRequestResult();
				result77.setSchoolStaffResponse(SchoolStaffEnrollmentHandler.saveSchoolStaff(action));
				return result77;
				
			case SchoolStaffEnrollmentCommand.GET_ALL_SCHOOL_STAFFS:
				MyRequestResult result78 = new MyRequestResult();
				result78.setSchoolStaffResponseList(SchoolStaffEnrollmentHandler.getSchoolStaffs(action));
				return result78;
				
			case SchoolStaffEnrollmentCommand.GET_SCHOOL_STAFF_BY_ID:
				MyRequestResult result79 = new MyRequestResult();
				result79.setSchoolStaffResponse(SchoolStaffEnrollmentHandler.getSchoolStaffById(action));
				return result79;
				
			case SchoolStaffEnrollmentCommand.DELETE_SCHOOL_STAFF:
				MyRequestResult result80 = new MyRequestResult();
				result80.setResponseText(SchoolStaffEnrollmentHandler.deleteSchoolStaff(action));
				return result80;
				
			case SchoolStaffEnrollmentCommand.UPDATE_SCHOOL_STAFF:
				MyRequestResult result81 = new MyRequestResult();
				result81.setSchoolStaffResponse(SchoolStaffEnrollmentHandler.updateSchoolStaff(action));
				return result81;
				
			case SchoolStaffEnrollmentCommand.GET_SCHOOL_STAFFS_BY_SYSTEM_USER_PROFILE_SCHOOLS:
				MyRequestResult result82 = new MyRequestResult();
				result82.setSchoolStaffResponseList(SchoolStaffEnrollmentHandler.getSchoolStaffsByLoggedSystemUserProfileSchools(action));
				return result82;
				
			case SchoolStaffEnrollmentCommand.GET_SCHOOL_STAFFS_BY_ACADEMIC_TERM_SCHOOL:
				MyRequestResult result83 = new MyRequestResult();
				result83.setSchoolStaffResponseList(SchoolStaffEnrollmentHandler.getSchoolStaffs(action));
				return result83;
				
			case LearnerAttendanceCommand.SAVE_LEARNER_ATTENDANCE:
				MyRequestResult result84 = new MyRequestResult();
				result84.setLearnerAttendanceResponse(LearnerAttendanceHandler.saveLearnerAttendance(action));
				return result84;
				
			case LearnerAttendanceCommand.GET_ALL_LEARNER_ATTENDANCES:
				MyRequestResult result85 = new MyRequestResult();
				result85.setLearnerAtendanceResponseList(LearnerAttendanceHandler.getLearnerAttendances(action));
				return result85;
				
			case LearnerAttendanceCommand.GET_LEARNER_ATTENDANCE_BY_ID:
				MyRequestResult result86 = new MyRequestResult();
				result86.setLearnerAttendanceResponse(LearnerAttendanceHandler.getLearnerAttendanceById(action));
				return result86;
				
			case LearnerAttendanceCommand.DELETE_LEARNER_ATTENDANCE:
				MyRequestResult result87 = new MyRequestResult();
				result87.setResponseText(LearnerAttendanceHandler.deleteLearnerAttendance(action));
				return result87;
				
			case LearnerAttendanceCommand.UPDATE_LEARNER_ATTENDANCE:
				MyRequestResult result88 = new MyRequestResult();
				result88.setLearnerAttendanceResponse(LearnerAttendanceHandler.updateLearnerAttendance(action));
				return result88;
				
			case LearnerAttendanceCommand.GET_LEARNER_ATTENDANCE_BY_SYSTEM_USER_PROFILE_SCHOOLS:
				MyRequestResult result89 = new MyRequestResult();
				result89.setLearnerAtendanceResponseList(LearnerAttendanceHandler.getLearnerAttendancesByLoggedSystemUserProfileSchools(action));
				return result89;
				
			case ClockInOutCommand.SAVE_CLOCK_IN:
				MyRequestResult result90 = new MyRequestResult();
				result90.setClockInResponse(ClockInOutHandler.saveClockIn(action));
				return result90;
				
			case ClockInOutCommand.GET_ALL_CLOCK_INS:
				MyRequestResult result91 = new MyRequestResult();
				result91.setClockInResponseList(ClockInOutHandler.getClockIns(action));
				return result91;
				
			case ClockInOutCommand.GET_CLOCK_INS_BY_SYSTEM_USER_PROFILE_SCHOOLS:
				MyRequestResult result93 = new MyRequestResult();
				result93.setClockInResponseList(ClockInOutHandler.getClockInsByLoggedSystemUserProfileSchools(action));
				return result93;
				
			case ClockInOutCommand.SAVE_CLOCK_OUT:
				MyRequestResult result94 = new MyRequestResult();
				result94.setClockOutResponse(ClockInOutHandler.saveClockOut(action));
				return result94;
				
			case ClockInOutCommand.GET_ALL_CLOCK_OUTS:
				MyRequestResult result95 = new MyRequestResult();
				result95.setClockOutResponseList(ClockInOutHandler.getClockOuts(action));
				return result95;
				
			case ClockInOutCommand.GET_CLOCK_OUTS_BY_SYSTEM_USER_PROFILE_SCHOOLS:
				MyRequestResult result97 = new MyRequestResult();
				result97.setClockOutResponseList(ClockInOutHandler.getClockOutsByLoggedSystemUserProfileSchools(action));
				return result97;
	
			case TimetableCommands.SAVE_TIMETABLE:
				MyRequestResult result98 = new MyRequestResult();
				result98.setTimeTableResponse(TimetableHandler.saveTimeTable(action));
				return result98;
				
			case TimetableCommands.GET_ALL_TIMETABLES:
				MyRequestResult result99 = new MyRequestResult();
				result99.setTimeTableResponseList(TimetableHandler.getTimeTables(action));
				return result99;
				
			case TimetableCommands.GET_TIMETABLESS_BY_SYSTEM_USER_PROFILE_SCHOOLS:
				MyRequestResult result100 = new MyRequestResult();
				result100.setTimeTableResponseList(TimetableHandler.getTimeTablesByLoggedSystemUserProfileSchools(action));
				return result100;
	
				
			case TimetableLessonCommands.SAVE_TIMETABLE_LESSON:
				MyRequestResult result101 = new MyRequestResult();
				result101.setTimeTableLessonResponse(TimeTableLessonHandler.saveTimeTableLesson(action));
				return result101;
				
			case TimetableLessonCommands.GET_ALL_TIMETABLE_LESSONS:
				MyRequestResult result102 = new MyRequestResult();
				result102.setTimeTableLessonResponseList(TimeTableLessonHandler.getTimeTableLessons(action));
				return result102;
				
			case TimetableLessonCommands.DELETE_TIMETABLE_LESSON_BY_ID:
				MyRequestResult result103 = new MyRequestResult();
				result103.setResponseText(TimeTableLessonHandler.deleteTimeTableLesson(action));
				return result103;
				
			case StaffDailyTimetableCommand.SAVE_STAFF_DAILY_TIME_TABLE:
				MyRequestResult result104 = new MyRequestResult();
				result104.setStaffDailyTimeTableResponse(StaffDailyTimetableHandler.saveStaffDailyTimeTable(action));
				return result104;
				
			case StaffDailyTimetableCommand.GET_ALL_STAFF_DAILY_TIME_TABLES:
				MyRequestResult result105 = new MyRequestResult();
				result105.setStaffDailyTimeTableResponseList(StaffDailyTimetableHandler.getStaffDailyTimeTables(action));
				return result105;
				
			case StaffDailyTimetableCommand.DELETE_STAFF_DAILY_TIME_TABLE:
				MyRequestResult result106 = new MyRequestResult();
				result106.setResponseText(StaffDailyTimetableHandler.deleteStaffDailyTimeTable(action));
				return result106;
				
			case StaffDailyTimetableCommand.GET_STAFF_DAILY_TIME_TABLES_BY_SYSTEM_USER_PROFILE_SCHOOLS:
				MyRequestResult result107 = new MyRequestResult();
				result107.setStaffDailyTimeTableResponseList(StaffDailyTimetableHandler.getStaffDailyTimeTablesByLoggedSystemUserProfileSchools(action));
				return result107;
				
			case StaffDailyTimetableLessonCommands.GET_ALL_STAFF_DAILY_TIME_TABLE_LESSONS:
				MyRequestResult result109 = new MyRequestResult();
				result109.setStaffDailyTimeTableLessonResponseList(StaffDailyTimetableLessonHandler.getStaffDailyTimeTableLessons(action));
				return result109;
				
			case StaffDailySupervisionCommand.SAVE_STAFF_DAILY_SUPERVISION:
				MyRequestResult result110 = new MyRequestResult();
				result110.setStaffDailyAttendanceSupervisionResponse(StaffDailyTimetableSupervisionHandler.saveStaffDailyAttendanceSupervision(action));
				return result110;
				
			case StaffDailySupervisionCommand.GET_ALL_STAFF_DAILY_SUPERVISIONS:
				MyRequestResult result111 = new MyRequestResult();
				result111.setStaffDailyAttendanceSupervisionResponseList(StaffDailyTimetableSupervisionHandler.getStaffDailyAttendanceSupervisions(action));
				return result111;
		
			case StaffDailySupervisionCommand.GET_STAFF_DAILY_SUPERVISIONS_BY_SYSTEM_USER_PROFILE_SCHOOLS_SCHOOL_DATE:
				MyRequestResult result112 = new MyRequestResult();
				result112.setStaffDailyAttendanceSupervisionResponseList(StaffDailyTimetableSupervisionHandler.getStaffDailyTimeTablesByLoggedSystemUserProfileSchools(action));
				return result112;
				
			case StaffDailySupervisionTaskCommand.GET_ALL_STAFF_DAILY_SUPERVISION_TASKS:
				MyRequestResult result113 = new MyRequestResult();
				result113.setAttendanceTaskSupervisionResponseList(StaffDailyTimetableSupervisionTaskHandler.getStaffDailyTimetableSupervisionTasks(action));
				return result113;
		
	
				

				
				

			default:
				System.out.println("UNKNOWN COMMAND");
				break;
			}
		} catch (ForbiddenException exception) {
			exception.printStackTrace();
			String message = "Session timed out. Please login in again";
			// error.setStatus("" + exception.getResponse().getStatus());
			MyRequestResult e = new MyRequestResult();
			e.setResponseInteger(createError(message, exception.getResponse().getStatusInfo().getStatusCode()));
			return e;

		} catch (ProcessingException exception) {
			exception.printStackTrace();
			String message = exception.getMessage()
					+ "\n There is a problem accessing the processing data processing engine. Please contact Systems Admin.";
			MyRequestResult e = new MyRequestResult();
			e.setResponseInteger(createError(message, 8082));
			return e;

		} catch (Exception exception) {
			exception.printStackTrace();
			String message = "System Error,  Please contact Systems Admin.";
			MyRequestResult e = new MyRequestResult();
			e.setResponseInteger(createError(message, 500));
			return e;
		}

		return null;
	}

	@Override
	public Class<MyRequestAction> getActionType() {
		return MyRequestAction.class;
	}

	@Override
	public void undo(MyRequestAction action, MyRequestResult result, ExecutionContext context) throws ActionException {	}

	private SystemResponseDTO<Integer> createError(String message, int code) {
		SystemResponseDTO<Integer> systemErrorDTO = new SystemResponseDTO<Integer>();
		systemErrorDTO.setData(code);
		systemErrorDTO.setMessage(message);
		return systemErrorDTO;
	}

}

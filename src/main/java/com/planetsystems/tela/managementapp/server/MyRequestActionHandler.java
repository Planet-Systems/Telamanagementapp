package com.planetsystems.tela.managementapp.server;

import javax.ws.rs.ForbiddenException;
import javax.ws.rs.ProcessingException;

import com.gwtplatform.dispatch.rpc.server.ExecutionContext;
import com.gwtplatform.dispatch.rpc.server.actionhandler.ActionHandler;
import com.gwtplatform.dispatch.shared.ActionException;
import com.planetsystems.tela.dto.response.SystemResponseDTO;
import com.planetsystems.tela.managementapp.server.handlers.AcademicYearTermHandler;
import com.planetsystems.tela.managementapp.server.handlers.LoginPresenterHandler;
import com.planetsystems.tela.managementapp.server.handlers.MainPresenterHandler;
import com.planetsystems.tela.managementapp.server.handlers.RegionDistrictHandler;
import com.planetsystems.tela.managementapp.server.handlers.SchoolCatergoryClassHandler;
import com.planetsystems.tela.managementapp.server.handlers.SubjectCategoryHandler;
import com.planetsystems.tela.managementapp.shared.MyRequestAction;
import com.planetsystems.tela.managementapp.shared.MyRequestResult;
import com.planetsystems.tela.managementapp.shared.requestcommands.AcademicYearTermCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.AuthRequestCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.RegionDistrictCommands;
import com.planetsystems.tela.managementapp.shared.requestcommands.SchoolCategoryClassCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.SubjectCategoryCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemUserGroupRequestCommand;
import com.planetsystems.tela.managementapp.shared.requestcommands.SystemUserGroupSystemMenuCommand;

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
				
			case SchoolCategoryClassCommand.GET_ALL_SCHOOL_CLASSES_BY_SCHOOL:
				MyRequestResult result48 = new MyRequestResult();
				result48.setSchoolClassResponseList(SchoolCatergoryClassHandler.getSchoolClassesBySchool(action));
				return result48;
		
				
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

package com.planetsystems.tela.managementapp.client.presenter.main;

import com.gwtplatform.mvp.client.gin.AbstractPresenterModule;
import com.planetsystems.tela.managementapp.client.presenter.academicyear.AcademicYearModule;
import com.planetsystems.tela.managementapp.client.presenter.dashboard.DashboardModule;
import com.planetsystems.tela.managementapp.client.presenter.learnerattendance.LearnerAttendanceModule;
import com.planetsystems.tela.managementapp.client.presenter.learnerenrollment.LearnerEnrollmentModule;
import com.planetsystems.tela.managementapp.client.presenter.region.RegionModule;
import com.planetsystems.tela.managementapp.client.presenter.schoolcategory.SchoolCategoryModule;
import com.planetsystems.tela.managementapp.client.presenter.schoolstaff.SchoolStaffModule;
import com.planetsystems.tela.managementapp.client.presenter.staffattendance.StaffAttendanceModule;
import com.planetsystems.tela.managementapp.client.presenter.staffdailyattendancesupervision.StaffDailyAttendanceSupervisionModule;
import com.planetsystems.tela.managementapp.client.presenter.staffdailytask.StaffDailyTaskModule;
import com.planetsystems.tela.managementapp.client.presenter.staffenrollment.StaffEnrollmentModule;
import com.planetsystems.tela.managementapp.client.presenter.subjectcategory.SubjectCategoryModule;
import com.planetsystems.tela.managementapp.client.presenter.systemuser.SystemUserModule;
import com.planetsystems.tela.managementapp.client.presenter.timeattendancesupervision.TimeAttendanceSupervisionModule;
import com.planetsystems.tela.managementapp.client.presenter.timetable.TimeTableModule;

public class MainModule extends AbstractPresenterModule {
    @Override
    protected void configure() {
     
    	  install(new DashboardModule());
          install(new AcademicYearModule());
          install(new RegionModule());
          install(new SchoolCategoryModule());
          install(new SubjectCategoryModule());
          install(new SchoolStaffModule());
          install(new StaffEnrollmentModule());
          install(new StaffAttendanceModule());
          install(new LearnerAttendanceModule());
          install(new LearnerEnrollmentModule());
          install(new TimeTableModule());
          install(new SystemUserModule());
          install(new StaffDailyTaskModule());
          install(new StaffDailyAttendanceSupervisionModule());
          install(new TimeAttendanceSupervisionModule());
          
    	bindPresenter(MainPresenter.class, MainPresenter.MyView.class, MainView.class, MainPresenter.MyProxy.class);
     
    }
}